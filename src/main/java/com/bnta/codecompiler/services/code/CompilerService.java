package com.bnta.codecompiler.services.code;

import com.bnta.codecompiler.models.dtos.CompileInput;
import com.bnta.codecompiler.models.dtos.CompileResult;
import com.bnta.codecompiler.utilities.SafetyFilter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.annotation.PreDestroy;
import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.concurrent.*;
import java.util.stream.Collectors;

@Service
public class CompilerService {

    @Value("${node.path}")
    private String nodePath;
    private final int timeout = 8;
    private final int threadCount = 2;
    private final ExecutorService executor = Executors.newFixedThreadPool(threadCount);

    @PreDestroy
    public void shutdownExecutor() {
        executor.shutdown();
    }

    private void saveFile(File file, String code) throws IOException {
        try (PrintWriter pr = new PrintWriter(new FileWriter(file))) {
            pr.print(code);
        }
    }

    public Process startProcess(String command, String args) throws IOException {
        ProcessBuilder processBuilder = new ProcessBuilder();
        String newPath = System.getenv("PATH") + ":" + nodePath;
        processBuilder.environment().put("PATH", newPath);
        return processBuilder.command(command, args).start();
    }

    private String readOutput(InputStream stream) throws IOException {
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(stream, StandardCharsets.UTF_8))) {
            return reader.lines().collect(Collectors.joining("\n"));
        }
    }

    private CompileResult readAll(Process process, CompileResult compileResult) throws IOException {
        compileResult.setOutput(readOutput(process.getInputStream()));
        compileResult.setErrors(readOutput(process.getErrorStream()));
        compileResult.setCompiled(compileResult.getErrors() == null || compileResult.getErrors().isEmpty());
        return compileResult;
    }

    public CompileResult compile(CompileInput input) throws Exception {
        if (!SafetyFilter.isInputSafe(input)) {
            throw new Exception("Dangerous or possible malicious code detected");
        }

        CompileResult result = new CompileResult(input.getCode(), null, null, false, input.getLang());
        Future<CompileResult> future;

        String command = switch (input.getLang()) {
            case "js" -> nodePath + "/node";
            case "java" -> "java";
            default -> "python3";
        };

        String ext = "." + input.getLang();
        File file = File.createTempFile("temp", ext);

        saveFile(file, input.getCode());

        CompileResult finalResult = result.clone();
        future = executor.submit(() -> shell(command, file.getAbsolutePath(), finalResult));

        try {
            result = future.get(timeout, TimeUnit.SECONDS);
        } catch (TimeoutException e) {
            future.cancel(true);
            result.setErrors("Compilation timed out after " + timeout + " seconds");
        } finally {
            if (!file.delete()) {
                System.out.println("Failed to delete temporary file: " + file.getAbsolutePath());
            }
        }

        return result;
    }

    public CompileResult shell(String command, String args, CompileResult result) throws IOException {
        return readAll(startProcess(command, args), result);
    }
}
