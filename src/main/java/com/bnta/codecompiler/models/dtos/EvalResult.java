package com.bnta.codecompiler.models.dtos;

import java.util.List;

public class EvalResult {
    private boolean successful;
    private boolean privateTestsPassed;
    private List<TestCaseResult> publicTestResults;

    public EvalResult(boolean successful, boolean privateTestsPassed, List<TestCaseResult> publicTestResults) {
        this.successful = successful;
        this.privateTestsPassed = privateTestsPassed;
        this.publicTestResults = publicTestResults;
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
}
