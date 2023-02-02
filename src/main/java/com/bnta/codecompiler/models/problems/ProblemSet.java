package com.bnta.codecompiler.models.problems;

import javax.persistence.*;
import java.util.List;
import java.util.Objects;
import java.util.Set;

@Entity
@Table(name="problem_sets")
public class ProblemSet {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long id;
    @Column(length = 200)
    private String title;
    @Column(length = 500)
    private String description;
    @Column
    private Difficulty difficulty;
    @ElementCollection
    private Set<String> tags = null;

    @ManyToMany(fetch = FetchType.EAGER, cascade = {CascadeType.MERGE, CascadeType.REMOVE})
    @JoinTable(name="problem_sets_problems",
            joinColumns = @JoinColumn(name="problem_set_id"),
            inverseJoinColumns = @JoinColumn(name="problem_id"))
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ProblemSet that = (ProblemSet) o;
        return Objects.equals(id, that.id) && Objects.equals(title, that.title) && Objects.equals(description, that.description) && difficulty == that.difficulty && Objects.equals(tags, that.tags) && Objects.equals(problems, that.problems);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, title, description, difficulty, tags, problems);
    }
}
