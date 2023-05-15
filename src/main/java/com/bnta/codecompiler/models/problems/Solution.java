package com.bnta.codecompiler.models.problems;

import com.bnta.codecompiler.models.users.User;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.Objects;

@Entity
@Table(name="solutions")
public class Solution {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long id;
    @Column(length = 2500)
    private String code;
    @Column
    private String lang;
    @Column
    private int correctness;
    @Column
    private LocalDate submissionDate;
    @ManyToOne(cascade = {})
    private Problem problem;
    @ManyToOne(fetch = FetchType.EAGER)
    @JsonIgnoreProperties({"solutions"})
    private User user;

    public Solution(String code, String lang, int correctness, Problem problem, User user) {
        this.code = code;
        this.lang = lang;
        this.correctness = correctness;
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

    public int getCorrectness() {
        return correctness;
    }

    public void setCorrectness(int correctness) {
        this.correctness = correctness;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Solution solution = (Solution) o;
        return correctness == solution.correctness && Objects.equals(id, solution.id) && Objects.equals(code, solution.code) && Objects.equals(lang, solution.lang) && Objects.equals(submissionDate, solution.submissionDate) && Objects.equals(problem, solution.problem) && Objects.equals(user, solution.user);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, code, lang, correctness, submissionDate, problem, user);
    }

    @Override
    public String toString() {
        return "Solution{" +
                "id=" + id +
                ", code='" + code + '\'' +
                ", lang='" + lang + '\'' +
                ", correct=" + correctness +
                ", submissionDate=" + submissionDate +
                ", problem=" + problem +
                ", user=" + user +
                '}';
    }
}
