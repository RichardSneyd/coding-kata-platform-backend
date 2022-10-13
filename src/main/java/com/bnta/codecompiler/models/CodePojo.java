package com.bnta.codecompiler.models;

public class CodePojo {
    private String lang;
    private String code;

    public CodePojo() {
    }

    public CodePojo(String code, String lang) {
        this.lang = lang;
        this.code = code;
    }

    public String getLang() {
        return lang;
    }

    public void setLang(String lang) {
        this.lang = lang;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    @Override
    public String toString() {
        return "CodeMessage{" +
                "lang='" + lang + '\'' +
                ", code='" + code + '\'' +
                '}';
    }
}
