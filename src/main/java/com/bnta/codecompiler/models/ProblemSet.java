package com.bnta.codecompiler.models;

import javax.persistence.*;
import java.util.Set;

@Table
public class ProblemSet {
    @Id
    private Long id;
    @Column
    private Difficulty difficulty;
    @ElementCollection
    private Set<String> tags;
    @ManyToMany
    private Set<Problem> problems;

    public ProblemSet(Set<Problem> problems, Difficulty difficulty, Set<String> tags) {
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

    public void addProblem(Problem problem) {
        this.problems.add(problem);
    }

    public void removeProblem(Problem problem) {
        this.problems.remove(problem);
    }
}
