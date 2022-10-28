package com.bnta.codecompiler.services;

import com.bnta.codecompiler.models.dtos.CompileInput;
import com.bnta.codecompiler.models.dtos.CompileResult;
import com.bnta.codecompiler.models.problems.Data;
import com.bnta.codecompiler.models.problems.DataType;
import com.bnta.codecompiler.models.tests.TestCase;
import com.bnta.codecompiler.services.code.EvalService;
import com.bnta.codecompiler.services.problems.ProblemService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@SpringBootTest
public class EvalServiceTest {
    @Autowired
    private EvalService evalService;

    private CompileInput javaInput;
    private CompileInput jsInput;
    private CompileInput pyInput;

    @BeforeEach
    public void setUp() {
        javaInput = new CompileInput("public class Main {" +
                "public int add(int a, int b) {" +
                "return a + b;" +
                "}" +
                "}", "java");
        jsInput = new CompileInput("", "js");
        pyInput = new CompileInput("", "py");
    }

    @Test
    public void testEvaluate() {
        assertThat(evalService).isNotNull();
    }

    @Test
    public void testExecuteTestCase() {
        var testCase = new TestCase(List.of(
                new Data("Hello World", DataType.STRING)
        ),
               new Data("Hello World", DataType.STRING));
        assertThat(evalService.executeTestCase("solution",
                testCase, javaInput)).isInstanceOf(CompileResult.class);
    }

    @Test
    public void testIsDataMatch() {
        assertThat(evalService.isDataMatch("[\"Horse\", \"Cat\", \"Dog\"]", "Horse,Cat,Dog")).isTrue();
        assertThat(evalService.isDataMatch("[true, false, false]", "true,false,false")).isTrue();
        assertThat(evalService.isDataMatch("[35, 22]", "35,22")).isTrue();
    }
}
