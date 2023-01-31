package com.bnta.codecompiler.services.code;

import com.bnta.codecompiler.models.dtos.CompileInput;
import com.bnta.codecompiler.models.dtos.CompileResult;
import com.bnta.codecompiler.utilities.SafetyFilter;
import org.springframework.stereotype.Service;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.concurrent.*;
import java.util.stream.Collectors;

@Service
public class CompilerService {
    private final int timeout = 5;
    private final int threadCount = 4;
    private ExecutorService executor = Executors.newFixedThreadPool(threadCount);
    private Future<CompileResult> future;
    private void saveFile(File file, String code) {
        try {
            PrintWriter pr = new PrintWriter(new FileWriter(file));
            pr.print(code);
            pr.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public Process startProcess(String command, String args) {
        try {
            ProcessBuilder processBuilder = new ProcessBuilder();
            Process process = processBuilder.command(command, args).start();
            return process;
        } catch (IOException e) {
            System.out.println(e.getMessage());
            return null;
        }
    }

    private String readOutput(InputStream stream) {
        String text = new BufferedReader(new InputStreamReader(stream, StandardCharsets.UTF_8)).lines().collect(Collectors.joining("\n"));
        System.out.println("returned from in stream: " + text);
        return text;
    }

    private CompileResult readAll(Process process, CompileResult compileResult) {
        compileResult.setOutput(readOutput(process.getInputStream()));
        compileResult.setErrors(readOutput(process.getErrorStream()));
        if(!(compileResult.getErrors().equals("") || compileResult.getErrors() == null)) compileResult.setCompiled(false);
        else compileResult.setCompiled(true);
        return compileResult;
    }

    public CompileResult compile(CompileInput input) throws Exception {
        if(!SafetyFilter.isInputSafe(input)) {
            throw new Exception("Dangerous or possible malicious code detected");
        }
        CompileResult result = new CompileResult(input.getCode(), null, null, false, input.getLang());
        try {
            String command = input.getLang().equals("js") ? "node" : input.getLang().equals("java") ? "java" : "python3";
            String ext = "." + input.getLang();
            String tDir = System.getProperty("java.io.tmpdir");
            File file = File.createTempFile("temp", ext);
            String tempName = file.getName();
            String fullPath = tDir + File.separator + tempName;

            saveFile(file, input.getCode());
            CompileResult finalResult = result.clone();
            future = executor.submit(() -> shell(command, fullPath, finalResult));
            result = future.get(timeout, TimeUnit.SECONDS);
        } catch (TimeoutException e) {
            future.cancel(true);
            result.setErrors("Compilation timed out after " + timeout + " seconds");
        }
        catch (IOException e) {
            e.printStackTrace();
            result.setErrors(e.getMessage());
            result.setCompiled(false);
        }
        return result;
    }

    public CompileResult shell(String command, String args, CompileResult result) {
        return readAll(startProcess(command, args), result);
    }

//    public CompileResult where(String command) {
//        CompileResult result = new CompileResult();
//        try {
//            result = readAll(startProcess("which", command), result);
//            return result;
//        } catch (Exception e) {
//            result.setCompiled(false);
//            result.setErrors("Error running 'where' on " + command + ": " + e.getMessage());
//            e.printStackTrace();
//            return result;
//        }
//
//    }

    public CompileResult echo(String message) {
        return readAll(startProcess("echo", message), new CompileResult());
    }

}
