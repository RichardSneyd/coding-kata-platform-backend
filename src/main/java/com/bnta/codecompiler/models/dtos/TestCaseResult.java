package com.bnta.codecompiler.models.dtos;

public class TestCaseResult {
    private CompileResult compileResult;
    private boolean correct;

    public TestCaseResult(CompileResult compileResult, boolean correct) {
        this.compileResult = compileResult;
        this.correct = correct;
    }

    public TestCaseResult() {
    }

    public CompileResult getCompileResult() {
        return compileResult;
    }

    public void setCompileResult(CompileResult compileResult) {
        this.compileResult = compileResult;
    }

    public boolean isCorrect() {
        return correct;
    }

    public void setCorrect(boolean correct) {
        this.correct = correct;
    }
}
