package com.bnta.codecompiler.services;

import org.springframework.stereotype.Service;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.stream.Collectors;

@Service
public class CompilerService {

    private void saveFile(File file, String code) throws IOException {
        PrintWriter pr = new PrintWriter(new FileWriter(file));
        pr.print(code);
        pr.close();
    }

    public Process startProcess(String command, String path) throws IOException {
        ProcessBuilder processBuilder = new ProcessBuilder();
        Process process = processBuilder.command(command, path).start();
        return process;
    }

    private String readOutput(InputStream stream) {
        String text = new BufferedReader(new InputStreamReader(stream, StandardCharsets.UTF_8)).lines().collect(Collectors.joining("\n"));
        System.out.println(text);
        return text;
    }

    public String compile(String code, String lang) {
        String result = "";
        try {
            String command = lang.equals("js") ? "node" : lang.equals("java") ? "java" : "python3";
            String ext = "." + lang;
            String tDir = System.getProperty("java.io.tmpdir");
            File file = File.createTempFile("temp", ext);
            String tempName = file.getName();
            String fullPath = tDir + tempName;

            saveFile(file, code);

            result = readOutput(startProcess(command, fullPath).getInputStream());
            System.out.println("captured result: " + result);
        }
        catch (Exception e) {
            result = e.getMessage();
        }

        return result;
    }

    public String where(String command) {
        String output;
        try {
            output = readOutput(startProcess("which", command).getInputStream());
            return output;
        }
        catch (Exception e) {
            output = "Error running 'where' on " + command + ": " + e.getMessage();
            System.out.println(output);
            return output;
        }

    }

}
