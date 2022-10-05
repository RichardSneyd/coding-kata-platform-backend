package com.bnta.codecompiler.models;

public class CodeReply {
    private String message;
    private Boolean correct;
    private String lang;

    public CodeReply() {
    }

    public CodeReply(String message, String lang, Boolean correct) {

        this.message = message;
        this.correct = correct;
        this.lang = lang;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
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
                "message='" + message + '\'' +
                ", correct=" + correct +
                ", lang='" + lang + '\'' +
                '}';
    }
}
