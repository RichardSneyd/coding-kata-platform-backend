package com.bnta.codecompiler.models.tests;

import com.bnta.codecompiler.models.problems.Problem;
import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.util.List;
import java.util.Set;

@Entity
@Table(name="test_suites")
public class TestSuite {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long id;
    @OneToMany
    private List<TestCase> publicCases;
    @OneToMany
    private List<TestCase> privateCases;

    @OneToOne(mappedBy = "testSuite")
    @JsonIgnore
    private Problem problem;

    public TestSuite(List<TestCase> publicCases, List<TestCase> privateCases) {
        this.publicCases = publicCases;
        this.privateCases = privateCases;
    }

    public TestSuite() {
    }

    public List<TestCase> getPublicCases() {
        return publicCases;
    }

    public void setPublicCases(List<TestCase> publicCases) {
        this.publicCases = publicCases;
    }

    public List<TestCase> getPrivateCases() {
        return privateCases;
    }

    public void setPrivateCases(List<TestCase> privateCases) {
        this.privateCases = privateCases;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Problem getProblem() {
        return problem;
    }

    public void setProblem(Problem problem) {
        this.problem = problem;
    }
}
