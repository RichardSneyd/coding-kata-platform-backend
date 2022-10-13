package com.bnta.codecompiler.models;

import java.util.Set;

public class TestSuite {
    private TestCase publicCase;
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
