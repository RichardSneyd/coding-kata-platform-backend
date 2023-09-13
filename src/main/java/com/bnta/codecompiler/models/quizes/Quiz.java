package com.bnta.codecompiler.models.quizes;

import com.bnta.codecompiler.models.assessment.TechTestSession;

import javax.persistence.*;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "quizzes")
public class Quiz {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToMany
    private List<Question> questions;

    public Quiz(Long id, List<Question> questions) {
        this.id = id;
        this.questions = questions;
    }

    public Quiz() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public List<Question> getQuestions() {
        return questions;
    }

    public void setQuestions(List<Question> questions) {
        this.questions = questions;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Quiz coerced = (Quiz) o;
        return Objects.equals(id, coerced.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
