package com.bnta.codecompiler.services.code;

import com.bnta.codecompiler.models.dtos.*;
import com.bnta.codecompiler.models.problems.Problem;
import com.bnta.codecompiler.models.tests.TestCase;
import com.bnta.codecompiler.utilities.SafetyFilter;
import com.bnta.codecompiler.utilities.SrcParser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;


import static com.bnta.codecompiler.utilities.SrcParser.generateSrc;

@Service
public class EvalService {

    @Autowired
    private CompilerService compiler;

    public EvalResult evaluate(CompileInput compileInputPojo, Problem problem) throws Exception {
        if (!SafetyFilter.isInputSafe(compileInputPojo)) {
            throw new Exception("Dangerous or possibly malicious code detected");
        }

        EvalResult evalResult = new EvalResult();
        evalResult.setProblem(problem);

        var logFreeInput = compileInputPojo.clone();
        logFreeInput.setCode(SrcParser.removeLogs(logFreeInput.getCode(), logFreeInput.getLang()));

        evalResult.setTestResultsWithLogs(asyncRunTestCases(problem.getTitle(), problem.getTestSuite().getPublicCases(), compileInputPojo));
        evalResult.setPublicTestResults(asyncRunTestCases(problem.getTitle(), problem.getTestSuite().getPublicCases(), logFreeInput));

        var privateResults = asyncRunTestCases(problem.getTitle(), problem.getTestSuite().getPrivateCases(), logFreeInput);

        evalResult.setPrivateTestsPassed(privateResults.stream().allMatch(TestCaseResult::isCorrect));

        var correctness = ((double)evalResult.getPublicTestResults().stream().filter(testCase -> testCase.isCorrect()).count()
                + (double)privateResults.stream().filter(testCase -> testCase.isCorrect()).count())
                / (problem.getTestSuite().getPublicCases().size() + privateResults.size()) * 100;
        evalResult.setCorrectness((int)correctness);
        evalResult.setSuccessful(correctness >= 70);

        return evalResult;
    }

    public List<TestCaseResult> asyncRunTestCases(String functionName, List<TestCase> testCases, CompileInput compileInputPojo) {
        List<CompletableFuture<TestCaseResult>> futures = testCases.stream()
                .map(testCase -> CompletableFuture.supplyAsync(() -> {
                    var compileResult = executeTestCase(functionName, testCase, compileInputPojo);
                    var correct = isDataMatch(testCase.getOutput().getValue(), compileResult.getOutput());
                    return new TestCaseResult(compileResult, correct);
                }))
                .collect(Collectors.toList());

        return futures.stream().map(CompletableFuture::join).collect(Collectors.toList());
    }

    public CompileResult executeTestCase(String functionName, TestCase testCase, CompileInput compileInputPojo) {
        var compileInput = compileInputPojo.clone();
        compileInput.setCode(generateSrc(functionName, compileInput.getCode(), compileInput.getLang(),
                testCase.getInputs(), testCase.getOutput().getDataType()));
        try {
            return compiler.compile(compileInput);
        } catch (Exception e) {
            e.printStackTrace();
            CompileResult errorResult = new CompileResult();
            errorResult.setErrors("Error during compilation: " + e.getMessage());
            return errorResult;
        }
    }

    public boolean isDataMatch(String expected, String actual) {
        return SrcParser.standardiseArgFormat(expected).equals(SrcParser.standardiseArgFormat(actual));
    }
}