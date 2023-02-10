package com.bnta.codecompiler.services.code;

import com.bnta.codecompiler.models.dtos.*;
import com.bnta.codecompiler.models.problems.Problem;
import com.bnta.codecompiler.models.tests.TestCase;
import com.bnta.codecompiler.utilities.SafetyFilter;
import com.bnta.codecompiler.utilities.SrcParser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import static com.bnta.codecompiler.utilities.SrcParser.generateSrc;

@Service
public class EvalService {
    @Autowired
    private CompilerService compiler;

    public EvalResult evaluate(CompileInput compileInputPojo, Problem problem) throws Exception {
        if(!SafetyFilter.isInputSafe(compileInputPojo)) {
            throw new Exception("Dangerous or possibly malicious code detected");
        }

    //    System.out.println(logFreeInput);
        EvalResult evalResult = new EvalResult();
        evalResult.setProblem(problem);
        evalResult.setTestResultsWithLogs(runTestCases(problem.getTitle(),
                problem.getTestSuite().getPublicCases(), compileInputPojo));

        // remove user-inserted logs before evaluating, or user output Stdout may pollute the result
        var logFreeInput = compileInputPojo.clone();
        logFreeInput.setCode(SrcParser.removeLogs(logFreeInput.getCode(), logFreeInput.getLang()));

        evalResult.setPublicTestResults(runTestCases(problem.getTitle(),
                problem.getTestSuite().getPublicCases(), logFreeInput));

        var privateResults = runTestCases(problem.getTitle(),
                problem.getTestSuite().getPrivateCases(), logFreeInput);

        evalResult.setPrivateTestsPassed(privateResults.stream()
                .allMatch(result -> result.isCorrect()));

        var correctness = ((double)evalResult.getPublicTestResults().stream().filter(testCase -> testCase.isCorrect()).count()
        + (double)privateResults.stream().filter(testCase -> testCase.isCorrect()).count())
                / (problem.getTestSuite().getPublicCases().size() + privateResults.size()) * 100;
        evalResult.setCorrectness((int)correctness);
        evalResult.setSuccessful(correctness >= 70);

        return evalResult;
    }

    public List<TestCaseResult> runTestCases(String functionName, List<TestCase> testCases,
                                             CompileInput compileInputPojo) {
        List<TestCaseResult> testResults = new ArrayList<>();
        for (var testCase : testCases) {
            var compileResult = executeTestCase(functionName, testCase, compileInputPojo);
            var correct = isDataMatch(testCase.getOutput().getValue(),
                    compileResult.getOutput());
            testResults.add(new TestCaseResult(compileResult, correct));
        }

        return testResults;
    }

    public CompileResult executeTestCase(String functionName, TestCase testCase,
                                         CompileInput compileInputPojo) {
        var compileInput = compileInputPojo.clone();
        compileInput.setCode(generateSrc(functionName, compileInput.getCode(), compileInput.getLang(),
                testCase.getInputs(), testCase.getOutput().getDataType()));
        try {
            return compiler.compile(compileInput);

        } catch(Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public boolean isDataMatch(String expected, String actual) {
        return SrcParser.standardiseArgFormat(expected).equals(SrcParser.standardiseArgFormat(actual));
    }
}
