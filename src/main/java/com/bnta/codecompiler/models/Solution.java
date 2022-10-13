package com.bnta.codecompiler.models;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Table
public class Solution extends CodeResult {
    @Id
    private Long id;
    @Column
    private Boolean correct;
    @Column
    private String code;

    @ManyToOne
    private Problem problem;

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
