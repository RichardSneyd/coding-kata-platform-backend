package com.bnta.codecompiler.models.tests;

import javax.persistence.*;
import java.util.Set;

@Entity(name="test_suites")
public class TestSuite {
    @Id
    @GeneratedValue
    private Long id;
    @OneToMany
    private Set<TestCase> publicCases;
    @OneToMany
    private Set<TestCase> privateCases;

    public TestSuite(Set<TestCase> publicCases, Set<TestCase> privateCases) {
        this.publicCases = publicCases;
        this.privateCases = privateCases;
    }

    public TestSuite() {
    }

    public Set<TestCase> getPublicCases() {
        return publicCases;
    }

    public void setPublicCases(Set<TestCase> publicCases) {
        this.publicCases = publicCases;
    }

    public Set<TestCase> getPrivateCases() {
        return privateCases;
    }

    public void setPrivateCases(Set<TestCase> privateCases) {
        this.privateCases = privateCases;
    }

}
