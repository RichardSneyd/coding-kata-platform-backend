package com.bnta.codecompiler.models;

public class CodeReply {
    private String result;
    private Boolean correct;
    private String lang;

    public CodeReply() {
    }

    public CodeReply(String message, String lang, Boolean correct) {

        this.result = message;
        this.correct = correct;
        this.lang = lang;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public Boolean getCorrect() {
        return correct;
    }

    public void setCorrect(Boolean correct) {
        this.correct = correct;
    }

    public String getLang() {
        return lang;
    }

    public void setLang(String lang) {
        this.lang = lang;
    }

    @Override
    public String toString() {
        return "CodeReply{" +
                "message='" + result + '\'' +
                ", correct=" + correct +
                ", lang='" + lang + '\'' +
                '}';
    }
}
