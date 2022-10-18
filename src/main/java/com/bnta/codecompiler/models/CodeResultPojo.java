package com.bnta.codecompiler.models;

public class CodeResultPojo {
    private String result;
    private String lang;

    public CodeResultPojo() {
    }

    public CodeResultPojo(String result, String lang) {
        this.result = result;
        this.lang = lang;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
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
                ", lang='" + lang + '\'' +
                '}';
    }
}
