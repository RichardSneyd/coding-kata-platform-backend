package com.bnta.codecompiler.models.problems;

import com.bnta.codecompiler.models.problems.Problem;
import com.bnta.codecompiler.models.users.User;

import javax.persistence.*;
import java.time.LocalDate;

@Entity(name="solutions")
public class Solution {
    @Id
    @GeneratedValue
    private Long id;
    @Column
    private String code;
    @Column
    private String lang;
    @Column
    private boolean correct;
    @Column
    private LocalDate submissionDate;
    @ManyToOne
    private Problem problem;
    @ManyToOne
    private User user;

    public Solution(String code, String lang, boolean correct, Problem problem, User user) {
        this.code = code;
        this.lang = lang;
        this.correct = correct;
        this.problem = problem;
        this.user = user;
        this.submissionDate = LocalDate.now();
    }

    public Solution() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getLang() {
        return lang;
    }

    public void setLang(String lang) {
        this.lang = lang;
    }

    public boolean isCorrect() {
        return correct;
    }

    public void setCorrect(boolean correct) {
        this.correct = correct;
    }

    public Problem getProblem() {
        return problem;
    }

    public void setProblem(Problem problem) {
        this.problem = problem;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public LocalDate getSubmissionDate() {
        return submissionDate;
    }

    public void setSubmissionDate(LocalDate submissionDate) {
        this.submissionDate = submissionDate;
    }
}
