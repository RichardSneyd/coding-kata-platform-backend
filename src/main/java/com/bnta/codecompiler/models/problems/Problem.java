package com.bnta.codecompiler.models.problems;

import com.bnta.codecompiler.models.tests.TestSuite;

import javax.persistence.*;
import java.util.Set;

@Entity
public class Problem {
    @Id
    private Long id;
    @Column
    private String title;
    @Column
    private String description = "Solve me!";
    @Column
    private Difficulty difficulty = Difficulty.EASY;
    @OneToOne
    private TestSuite testSuite;

    // tag the concepts this problem trains/tests, i.e 'objects', 'arrays',
    @ElementCollection
    private Set<String> tags = null;

    public Problem(String title, String description, Difficulty difficulty, TestSuite testSuite, Set<String> tags) {
        this.title = title;
        this.description = description;
        this.difficulty = difficulty;
        this.testSuite = testSuite;
        this.tags = tags;
    }

    public Problem() {
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Long getId() {
        return id;
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
