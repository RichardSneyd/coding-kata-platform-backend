package com.bnta.codecompiler.models.dtos;

public class EvalInput extends CompileInput {
    private Long userId;

    public EvalInput(String code, String lang, Long userId) {
        super(code, lang);
        this.userId = userId;
    }

    public EvalInput() {
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }
}
