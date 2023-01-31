package com.bnta.codecompiler.models.dtos;

public class CompileInput {
    private String lang;
    private String code;

    public CompileInput() {
    }

    public CompileInput(String code, String lang) {
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

    public void setCode(String newCode) {
        this.code = newCode;
    }

    @Override
    public String toString() {
        return "CodeMessage{" +
                "lang='" + lang + '\'' +
                ", code='" + code + '\'' +
                '}';
    }

    @Override
    public CompileInput clone() {
        return new CompileInput(this.code, this.lang);
    }
}
