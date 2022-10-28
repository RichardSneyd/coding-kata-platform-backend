package com.bnta.codecompiler.models.dtos;

import javax.persistence.*;

@Entity
public class CompileResult {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long id;
    @Column
    private String output;
    @Column
    private String errors;
    @Column
    private String lang;
    @Column
    private String code;
    @Column
    private boolean compiled;

    public CompileResult(String code, String output, String errors, Boolean compiled, String lang) {
        this.code = code;
        this.output = output;
        this.errors = errors;
        this.compiled = compiled;
        this.lang = lang;
    }

    public CompileResult() {
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getOutput() {
        return output;
    }

    public void setOutput(String output) {
        this.output = output;
    }

    public String getErrors() {
        return errors;
    }

    public void setErrors(String errors) {
        this.errors = errors;
    }

    public boolean isCompiled() {
        return compiled;
    }

    public void setCompiled(boolean compiled) {
        this.compiled = compiled;
    }

    public String getLang() {
        return lang;
    }

    public void setLang(String lang) {
        this.lang = lang;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
