package com.bnta.codecompiler.services;

import com.bnta.codecompiler.models.dtos.CompileInput;
import com.bnta.codecompiler.models.dtos.CompileResult;
import com.bnta.codecompiler.models.dtos.TestCaseResult;
import com.bnta.codecompiler.models.problems.*;
import com.bnta.codecompiler.models.tests.TestCase;
import com.bnta.codecompiler.models.tests.TestSuite;
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

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@SpringBootTest
public class EvalServiceTest {
    @Autowired
    private EvalService evalService;

    private CompileInput javaInput;
    private CompileInput javaInputPolluted;
    private CompileInput jsInput;
    private CompileInput jsInputPolluted;
    private CompileInput pyInput;
    private CompileInput pyInputPolluted;

    private List<TestCase> testCases;
    private List<TestCase> privateCases;

    @BeforeEach
    public void setUp() {
        javaInput = new CompileInput("public class Main {public int add(int a, int b) {return a + b;}}", "java");
        javaInputPolluted = new CompileInput("public class Main {public int add(int a, int b) {System.out.println(\"horse\");\nreturn a + b;}}", "java");
        jsInput = new CompileInput("const add = (a, b)=> a + b;", "js");
        jsInputPolluted = new CompileInput("const add = (a, b)=> {console.log('user pollution js'); return a + b; }", "js");
        pyInput = new CompileInput("def add(a, b):\n\sreturn a + b\n\n", "py");
        pyInputPolluted = new CompileInput("def add(a, b):\n\sprint(\"user pollution\")\n\sreturn a + b\n\n", "py");

        testCases = List.of(
                new TestCase(List.of(
                        new Data("5", DataType.INT),
                        new Data("5", DataType.INT)),
                        new Data("10", DataType.INT)),
                new TestCase(List.of(
                        new Data("14", DataType.INT),
                        new Data("-5", DataType.INT)),
                        new Data("9", DataType.INT))
        );

        privateCases = List.of(
                new TestCase(List.of(
                        new Data("10", DataType.INT),
                        new Data("-9", DataType.INT)),
                        new Data("1", DataType.INT))
        );
    }

   @Test
    public void testEvaluate() {
      //  assertThat(evalService).isNotNull();
        Problem problem = new Problem("add", "blablabla", Difficulty.VERY_EASY,
                new TestSuite(testCases,
                        privateCases), new StartCode(),
                new HashSet<>(List.of("tag1", "tag2")));
        var compileInputs = new CompileInput[]{javaInputPolluted, jsInputPolluted, pyInputPolluted};
        try {
            for (var input : compileInputs) {
                var result = evalService.evaluate(input, problem);
                assertThat(result).isNotNull();
                assertThat(result.isSuccessful()).isTrue();
                assertThat(result.getTestResultsWithLogs().get(0).isCorrect()).isFalse();
                assertThat(result.getPublicTestResults().get(0).isCorrect()).isTrue();

            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    @Test
    public void testRunTestCases() {
        List<TestCaseResult> results = evalService.runTestCases("add",
                testCases, javaInput);
        //  assertThat(results.get(1).getCompileResult().getOutput()).isEqualTo("9");
        for (int i = 0; i < results.size(); i++) {
            assertThat(results.get(i).getCompileResult().getOutput()).isNotNull();
        }
    }

    @Test
    public void testExecuteTestCase() {
        var funcName = "add";

        assertThat(evalService.executeTestCase(funcName,
                testCases.get(0), javaInput)).isInstanceOf(CompileResult.class);
        assertThat(evalService.executeTestCase(funcName,
                testCases.get(0), javaInput).getOutput()).isEqualTo("10");

        assertThat(evalService.executeTestCase(funcName,
                testCases.get(0), jsInput)).isInstanceOf(CompileResult.class);
        assertThat(evalService.executeTestCase(funcName,
                testCases.get(0), jsInput).getOutput()).isEqualTo("10");

    }

    @Test
    public void testIsDataMatch() {
        assertThat(evalService.isDataMatch("[\"Horse\", \"Cat\", \"Dog\"]", "Horse,Cat,Dog")).isTrue();
        assertThat(evalService.isDataMatch("[true, false, false]", "true,false,false")).isTrue();
        assertThat(evalService.isDataMatch("[35, 22]", "35,22")).isTrue();
    }
}
