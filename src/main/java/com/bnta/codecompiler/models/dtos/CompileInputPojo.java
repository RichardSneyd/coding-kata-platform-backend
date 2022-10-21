package com.bnta.codecompiler.models.dtos;

public class CompileInputPojo {
    private String lang;
    private String code;

    public CompileInputPojo() {
    }

    public CompileInputPojo(String code, String lang) {
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

    @Override
    public CompileInputPojo clone() {
        return new CompileInputPojo(this.code, this.lang);
    }
}
