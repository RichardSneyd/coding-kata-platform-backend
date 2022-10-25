package com.bnta.codecompiler.services.code;

import com.bnta.codecompiler.models.dtos.*;
import com.bnta.codecompiler.models.problems.Problem;
import com.bnta.codecompiler.models.tests.TestCase;
import com.bnta.codecompiler.utilities.JSON;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Service
public class EvalService {
    @Autowired
    CompilerService compiler;

    public EvalResult evaluate(CompileInput compileInputPojo, Problem problem) {
        EvalResult evalResult = new EvalResult();
        evalResult.setProblem(problem);

        evalResult.setPublicTestResults(runTestCases(problem.getTestSuite().getPublicCases(), compileInputPojo));
        var privateResults = runTestCases(problem.getTestSuite().getPrivateCases(), compileInputPojo);
        evalResult.setPrivateTestsPassed(privateResults.stream().allMatch(result -> result.isCorrect()));
        evalResult.setSuccessful(evalResult.isPrivateTestsPassed() &&
                evalResult.getPublicTestResults().stream()
                        .allMatch(result -> result.isCorrect()));
        return evalResult;
    }

    public List<TestCaseResult> runTestCases(Set<TestCase> testCases, CompileInput compileInputPojo) {
        List<TestCaseResult> testResults = new ArrayList<>();
        for (var testCase : testCases) {
            var compileResult = compileWithTestInput(testCase, compileInputPojo);
            var correct = testCase.getOutput().equals(compileResult.getOutput());
            testResults.add(new TestCaseResult(compileResult, correct));
        }

        return testResults;
    }

    public CompileResult compileWithTestInput(TestCase testCase, CompileInput compileInputPojo) {
        var compileInput = compileInputPojo.clone();
        compileInput.setCode(wrap(compileInput.getCode(), compileInput.getLang(), testCase.getInput()));

        return compiler.compile(compileInput);
    }

    private String wrap(String src, String lang, String input) {
        switch (lang) {
            case "java":
                return wrapJava(src, input);
            case "js":
                return wrapJs(src, input);
            case "py":
                return wrapPython(src, input);
            default:
                return src;
        }
    }

    private String wrapJava(String src, String input) {
        var inputVals = JSON.parse(input);
        String argsString = "";
        // inputVals.as
        String prefix = "public class Main { \n" +
                "public static void main(String[] args) { \n" +
                "System.out.println(solution(";
        String suffix = "));\n }";
        return prefix + argsString + suffix + src + "\n }";
    }


    private String wrapJs(String src, String input) {
        String prefix = "console.log(solution(";
        String suffix = "));";
        return src + prefix + input + suffix;
    }

    private String wrapPython(String src, String input) {
        return src + "print(solution(" + input + "))";
    }


}
