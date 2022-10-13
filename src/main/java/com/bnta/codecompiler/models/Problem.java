package com.bnta.codecompiler.models;

import java.util.Set;

public class Problem {
    private String description = "Solve me!";
    private Difficulty difficulty = Difficulty.EASY;
    private TestSuite testSuite;
    private String modelOutput = null;
    // tag the concepts this problem trains/tests, i.e 'objects', 'arrays',
    private Set<String> tags = null;

    public Problem(String description, Difficulty difficulty, TestSuite testSuite, Set<String> tags) {
        this.description = description;
        this.difficulty = difficulty;
        this.testSuite = testSuite;
        this.tags = tags;
    }

    public Problem() {
    }

    public String getModelOutput() {
        return modelOutput;
    }

    public void setModelOutput(String modelOutput) {
        this.modelOutput = modelOutput;
    }

    public Set<String> getTags() {
        return tags;
    }

    public void setTags(Set<String> tags) {
        this.tags = tags;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Difficulty getDifficulty() {
        return difficulty;
    }

    public void setDifficulty(Difficulty difficulty) {
        this.difficulty = difficulty;
    }

    public TestSuite getTestSuite() {
        return testSuite;
    }

    public void setTestSuite(TestSuite testSuite) {
        this.testSuite = testSuite;
    }
}
