package com.bnta.codecompiler.models.problems;

import javax.persistence.*;
import java.util.List;
import java.util.Set;

@Entity
@Table(name="problem_sets")
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
    private List<String> tags = null;
    @ManyToMany
    private List<Problem> problems;

    public ProblemSet(String title, String description, List<Problem> problems, Difficulty difficulty, List<String> tags) {
        this.title = title;
        this.description = description;
        this.problems = problems;
        this.difficulty = difficulty;
        this.tags = tags;
    }

    public ProblemSet() {
    }

    public List<Problem> getProblems() {
        return problems;
    }

    public void setProblems(List<Problem> problems) {
        this.problems = problems;
    }

    public Difficulty getDifficulty() {
        return difficulty;
    }

    public void setDifficulty(Difficulty difficulty) {
        this.difficulty = difficulty;
    }

    public List<String> getTags() {
        return tags;
    }

    public void setTags(List<String> tags) {
        this.tags = tags;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
