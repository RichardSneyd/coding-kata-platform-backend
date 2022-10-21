package com.bnta.codecompiler.models.dtos;

import com.bnta.codecompiler.models.problems.Problem;

import java.util.List;

public class EvalResult {
    private boolean successful;

    private boolean privateTestsPassed;
    private List<TestCaseResult> publicTestResults;

    private Problem problem;

    public EvalResult(boolean successful, boolean privateTestsPassed, List<TestCaseResult> publicTestResults, Problem problem) {
        this.successful = successful;
        this.privateTestsPassed = privateTestsPassed;
        this.publicTestResults = publicTestResults;
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
}
