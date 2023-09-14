package com.bnta.codecompiler.models.dtos;

import com.bnta.codecompiler.models.problems.Difficulty;
import com.bnta.codecompiler.models.problems.Solution;

import java.time.LocalDate;
import java.util.Objects;

public class SolutionDTO {

    // Solution fields
    private Long id;
    private String code;
    private String lang;
    private int correctness;
    private LocalDate submissionDate;

    // User field
    private String username;

    // Problem fields
    private String title;
    private String description;
    private Difficulty difficulty;

    // Constructors, getters, setters, and other desired methods...

    // Constructor to initialize with provided values
    public SolutionDTO(Long id, String code, String lang, int correctness, LocalDate submissionDate,
                       String username, String title, String description, Difficulty difficulty) {
        this.id = id;
        this.code = code;
        this.lang = lang;
        this.correctness = correctness;
        this.submissionDate = submissionDate;
        this.username = username;
        this.title = title;
        this.description = description;
        this.difficulty = difficulty;
    }

    // Empty constructor
    public SolutionDTO() {}

    // Getters and setters...

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getLang() {
        return lang;
    }

    public void setLang(String lang) {
        this.lang = lang;
    }

    public int getCorrectness() {
        return correctness;
    }

    public void setCorrectness(int correctness) {
        this.correctness = correctness;
    }

    public LocalDate getSubmissionDate() {
        return submissionDate;
    }

    public void setSubmissionDate(LocalDate submissionDate) {
        this.submissionDate = submissionDate;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
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

    public Difficulty getDifficulty() {
        return difficulty;
    }

    public void setDifficulty(Difficulty difficulty) {
        this.difficulty = difficulty;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SolutionDTO coerced = (SolutionDTO) o;
        return Objects.equals(id, coerced.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return "SolutionDTO{" +
                "id=" + code +
                '}';
    }

}
