package com.bnta.codecompiler.models.problems;

import javax.persistence.*;
import java.util.Set;

@Entity(name="problem_sets")
public class ProblemSet {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long id;
    @Column
    private String title;
    @Column
    private String description;
    @Column
    private Difficulty difficulty;
    @ElementCollection
    private Set<String> tags = null;
    @ManyToMany
    private Set<Problem> problems;

    public ProblemSet(String title, String description, Set<Problem> problems, Difficulty difficulty, Set<String> tags) {
        this.title = title;
        this.description = description;
        this.problems = problems;
        this.difficulty = difficulty;
        this.tags = tags;
    }

    public ProblemSet() {
    }

    public Set<Problem> getProblems() {
        return problems;
    }

    public void setProblems(Set<Problem> problems) {
        this.problems = problems;
    }

    public Difficulty getDifficulty() {
        return difficulty;
    }

    public void setDifficulty(Difficulty difficulty) {
        this.difficulty = difficulty;
    }

    public Set<String> getTags() {
        return tags;
    }

    public void setTags(Set<String> tags) {
        this.tags = tags;
    }

}
