package com.bnta.codecompiler.services;

import com.bnta.codecompiler.services.code.CompilerService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;


public class CompilerServiceTest {
    CompilerService compilerService;

    @BeforeEach
    public void setUp() {
        compilerService = new CompilerService();
    }

    @Test
    public void compileJS() throws IOException {
        String result = compilerService.compile("console.log('Hello from JS compiler');", "js");
        assertEquals(result, "Hello from JS compiler");
    }

    @Test
    public void compileJava() {
        String result = compilerService.compile(
                "public class Main { " +
                            "public static void main(String[] args) {" +
                                "System.out.println(\"Hello from Java compiler\");" +
                            "} " +
                        "}", "java");
        assertEquals(result, "Hello from Java compiler");
    }

    @Test
    public void compilePython() throws IOException {
        String result = compilerService.compile(
                "print('Hello from Python compiler')", "py");
        assertEquals(result, "Hello from Python compiler");
    }

    @Test
    public void getTempFileName() throws IOException {
        File file = File.createTempFile("temp", ".js");
        ProcessBuilder processBuilder = new ProcessBuilder();
        assertNotNull(file.getName());
    }

}
