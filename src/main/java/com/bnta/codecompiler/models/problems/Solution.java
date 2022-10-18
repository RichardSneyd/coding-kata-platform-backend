package com.bnta.codecompiler.models.problems;

import com.bnta.codecompiler.models.code.CodeResultPojo;
import com.bnta.codecompiler.models.users.User;

import javax.persistence.*;

@Entity
public class Solution extends CodeResultPojo {
    @Id
    private Long id;
    @Column
    private Boolean correct;
    @Column
    private String code;

    @ManyToOne
    private Problem problem;

    @OneToMany
    private User user;

    public Solution() {
    }

    public Solution(String code, String result, String lang, Boolean correct, User user) {
        super(result, lang);
        this.code = code;
        this.correct = correct;
        this.user = user;
    }

    public Boolean getCorrect() {
        return correct;
    }

    public void setCorrect(Boolean correct) {
        this.correct = correct;
    }
}
