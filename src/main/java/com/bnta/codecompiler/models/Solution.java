package com.bnta.codecompiler.models;

public class Solution extends CodeResult {
    private Boolean correct;
    private String code;

    public Solution() {
    }

    public Solution(String code, String result, String lang, Boolean correct) {
        super(result, lang);
        this.code = code;
        this.correct = correct;
    }

    public Boolean getCorrect() {
        return correct;
    }

    public void setCorrect(Boolean correct) {
        this.correct = correct;
    }
}
