package com.bnta.codecompiler.models.dtos;

import com.bnta.codecompiler.models.problems.Problem;

import java.util.List;

public class EvalResult {
    private boolean successful;
    private int correctness;
    private boolean privateTestsPassed;

    private List<TestCaseResult> testResultsWithLogs;
    private List<TestCaseResult> publicTestResults;

    private Problem problem;

    public EvalResult(boolean successful, int correctness, boolean privateTestsPassed, List<TestCaseResult> publicTestResults, List<TestCaseResult> testResultsWithLogs, Problem problem) {
        this.successful = successful;
        this.correctness = correctness;
        this.privateTestsPassed = privateTestsPassed;
        this.publicTestResults = publicTestResults;
        this.testResultsWithLogs = testResultsWithLogs;
        this.problem = problem;
    }

    public EvalResult() {
    }

    public boolean isSuccessful() {
        return successful;
    }

    public void setSuccessful(boolean successful) {
        this.successful = successful;
    }

    public boolean isPrivateTestsPassed() {
        return privateTestsPassed;
    }

    public void setPrivateTestsPassed(boolean privateTestsPassed) {
        this.privateTestsPassed = privateTestsPassed;
    }

    public List<TestCaseResult> getPublicTestResults() {
        return publicTestResults;
    }

    public void setPublicTestResults(List<TestCaseResult> publicTestResults) {
        this.publicTestResults = publicTestResults;
    }

    public Problem getProblem() {
        return problem;
    }

    public void setProblem(Problem problem) {
        this.problem = problem;
    }

    public List<TestCaseResult> getTestResultsWithLogs() {
        return testResultsWithLogs;
    }

    public void setTestResultsWithLogs(List<TestCaseResult> testResultsWithLogs) {
        this.testResultsWithLogs = testResultsWithLogs;
    }

    public int getCorrectness() {
        return correctness;
    }

    public void setCorrectness(int correctness) {
        this.correctness = correctness;
    }
}
