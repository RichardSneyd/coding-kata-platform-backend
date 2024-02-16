package com.bnta.codecompiler.services;

import com.bnta.codecompiler.models.dtos.CompileInput;
import com.bnta.codecompiler.models.dtos.CompileResult;
import com.bnta.codecompiler.services.code.CompilerService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;


public class CompilerServiceTest {
    CompilerService compilerService;

    @BeforeEach
    public void setUp() {
        compilerService = new CompilerService();
    }

//    @Test
//    public void compileJS() throws Exception {
//        var result = compilerService.compile(new CompileInput("console.log('Hello from JS compiler');", "js"));
//        assertThat(result.getOutput()).isEqualTo("Hello from JS compiler");
//    }

    @Test
    public void compileJava() throws Exception {
        var result = compilerService.compile(new CompileInput(
                "public class Main { " +
                            "public static void main(String[] args) {" +
                                "System.out.println(\"Hello from Java compiler\");" +
                            "} " +
                        "}", "java"));
        assertEquals(result.getOutput(), "Hello from Java compiler");
    }

    @Test
    public void compilePython() throws Exception {
        var result = compilerService.compile(new CompileInput(
                "print('Hello from Python compiler')", "py"));
        assertEquals(result.getOutput(), "Hello from Python compiler");
    }

    @Test
    public void getTempFileName() throws IOException {
        File file = File.createTempFile("temp", ".js");
        ProcessBuilder processBuilder = new ProcessBuilder();
        assertNotNull(file.getName());
    }

}
