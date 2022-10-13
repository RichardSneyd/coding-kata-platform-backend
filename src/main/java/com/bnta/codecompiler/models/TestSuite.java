package com.bnta.codecompiler.models;

import javax.persistence.*;
import java.util.Set;

@Table
public class TestSuite {
    @Id
    private Long id;
    @OneToOne
    private TestCase publicCase;
    @OneToMany
    private Set<TestCase> privateCases;

    public TestSuite(TestCase publicCase, Set<TestCase> privateCases) {
        this.publicCase = publicCase;
        this.privateCases = privateCases;
    }

    public TestSuite() {
    }

    public TestCase getPublicCase() {
        return publicCase;
    }

    public void setPublicCase(TestCase publicCase) {
        this.publicCase = publicCase;
    }

    public Set<TestCase> getPrivateCases() {
        return privateCases;
    }

    public void setPrivateCases(Set<TestCase> privateCases) {
        this.privateCases = privateCases;
    }

    public TestSuite addPrivateCase(TestCase privateCase) {
        this.privateCases.add(privateCase);
        return this;
    }
}
