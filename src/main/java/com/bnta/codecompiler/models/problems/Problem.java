package com.bnta.codecompiler.models.problems;

import com.bnta.codecompiler.models.tests.TestSuite;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Entity
@Table(name="problems")
public class Problem {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long id;
    @Column
    private String title;
    @Column
    private String description = "Solve me!";
    @Column
    private Difficulty difficulty = Difficulty.EASY;
    @OneToOne(cascade = {})
    private TestSuite testSuite;
    @OneToOne(cascade = {})
    private StartCode startCode;
    // tag the concepts this problem trains/tests, i.e 'objects', 'arrays',
    @ElementCollection
    private Set<String> tags = new HashSet<>();

    public Problem(String title, String description, Difficulty difficulty, TestSuite testSuite, StartCode startCode, Set<String> tags) {
        this.title = title;
        this.description = description;
        this.difficulty = difficulty;
        this.testSuite = testSuite;
        this.startCode = startCode;
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

    public StartCode getStartCode() {
        return startCode;
    }

    public void setStartCode(StartCode startCode) {
        this.startCode = startCode;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "Problem{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", description='" + description + '\'' +
                ", difficulty=" + difficulty +
                ", testSuite=" + testSuite +
                ", startCode=" + startCode +
                ", tags=" + tags +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Problem problem = (Problem) o;
        return Objects.equals(id, problem.id) && Objects.equals(title, problem.title) && Objects.equals(description, problem.description) && difficulty == problem.difficulty && Objects.equals(testSuite, problem.testSuite) && Objects.equals(startCode, problem.startCode) && Objects.equals(tags, problem.tags);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, title, description, difficulty, testSuite, startCode, tags);
    }
}
