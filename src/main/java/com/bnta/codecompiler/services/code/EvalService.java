package com.bnta.codecompiler.services.code;

import com.bnta.codecompiler.models.dtos.*;
import com.bnta.codecompiler.models.problems.Problem;
import com.bnta.codecompiler.models.tests.TestCase;
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
    CompilerService compiler;

    public EvalResult evaluate(CompileInput compileInputPojo, Problem problem) {
        EvalResult evalResult = new EvalResult();
        evalResult.setProblem(problem);

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
            var compileResult = compileWithTestInput(functionName, testCase, compileInputPojo);
            var correct = isDataMatch(testCase.getOutput().getValue(),
                    compileResult.getOutput());
            testResults.add(new TestCaseResult(compileResult, correct));
        }

        return testResults;
    }

    public CompileResult compileWithTestInput(String functionName, TestCase testCase,
                                              CompileInput compileInputPojo) {
        var compileInput = compileInputPojo.clone();
        compileInput.setCode(generateSrc(functionName, compileInput.getCode(), compileInput.getLang(),
                testCase.getInputs(), testCase.getOutput().getDataType()));

        return compiler.compile(compileInput);
    }



    private boolean isDataMatch(String expected, String actual) {
        return SrcParser.standardiseArgFormat(expected) == SrcParser.standardiseArgFormat(actual);
    }
}
