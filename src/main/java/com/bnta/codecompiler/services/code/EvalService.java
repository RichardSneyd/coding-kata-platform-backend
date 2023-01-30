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
            throw new Exception("Dangerous or possible malicious code detected");
        }
        EvalResult evalResult = new EvalResult();
        evalResult.setProblem(problem);
        evalResult.setTestResultsWithLogs(runTestCases(problem.getTitle(),
                problem.getTestSuite().getPublicCases(), compileInputPojo));

        // remove user-inserted logs before evaluating, or user output Stdout may pollute the result
        compileInputPojo.setCode(SrcParser.removeLogs(compileInputPojo.getCode(), compileInputPojo.getLang()));
        evalResult.setPublicTestResults(runTestCases(problem.getTitle(),
                problem.getTestSuite().getPublicCases(), compileInputPojo));
        var privateResults = runTestCases(problem.getTitle(),
                problem.getTestSuite().getPrivateCases(), compileInputPojo);
        evalResult.setPrivateTestsPassed(privateResults.stream()
                .allMatch(result -> result.isCorrect()));
        evalResult.setSuccessful(evalResult.isPrivateTestsPassed() &&
                evalResult.getPublicTestResults().stream()
                        .allMatch(result -> result.isCorrect()));
        return evalResult;
    }

    public List<TestCaseResult> runTestCases(String functionName, Set<TestCase> testCases,
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
                testCase.getInputs(), testCase.getOutput().getDataType(), functionName));
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
