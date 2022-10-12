package com.bnta.codecompiler.services;

import org.springframework.stereotype.Service;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.stream.Collectors;

@Service
public class CompilerService {

    private void saveFile(File file, String code) {
        try {
            PrintWriter pr = new PrintWriter(new FileWriter(file));
            pr.print(code);
            pr.close();
        } catch (IOException e) {
            System.out.println(e.getMessage());
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

    private String readAll(Process process) {
        String result = readOutput(process.getInputStream());
        String errorMsg = readOutput(process.getErrorStream());
        if (errorMsg != null && !errorMsg.equals("")) result = result +  "\n" + errorMsg;
        return result;
    }

    public String compile(String code, String lang) {
        String result = "";
        try {
            String command = lang.equals("js") ? "node" : lang.equals("java") ? "java" : "python3";
           // command = where(command);
            String ext = "." + lang;
            String tDir = System.getProperty("java.io.tmpdir");
            File file = File.createTempFile("temp", ext);
            String tempName = file.getName();
            String fullPath = tDir + File.separator + tempName;

            saveFile(file, code);
            result = shell(command, fullPath);
        } catch (IOException e) {
            System.out.println(e.getMessage());
            result = e.getMessage();
        }
        return result;
    }

    public String shell(String command, String args) {
        return readAll(startProcess(command, args));
    }

    public String where(String command) {
        String output;
        try {
            output = readAll(startProcess("which", command));
            return output;
        } catch (Exception e) {
            output = "Error running 'where' on " + command + ": " + e.getMessage();
            System.out.println(output);
            return output;
        }

    }

    public String echo(String message) {
        return readAll(startProcess("echo", message));
    }

}
