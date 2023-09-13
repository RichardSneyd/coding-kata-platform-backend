package com.bnta.codecompiler.models.quizes;

import com.bnta.codecompiler.models.assessment.TechTestSession;

import javax.persistence.*;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "questions")
public class Question {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String correctAnswer;

    @ElementCollection
    private List<String> wrongAnswers;

    public Question(Long id, String correctAnswer, List<String> wrongAnswers) {
        this.id = id;
        this.correctAnswer = correctAnswer;
        this.wrongAnswers = wrongAnswers;
    }

    public Question() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCorrectAnswer() {
        return correctAnswer;
    }

    public void setCorrectAnswer(String correctAnswer) {
        this.correctAnswer = correctAnswer;
    }

    public List<String> getWrongAnswers() {
        return wrongAnswers;
    }

    public void setWrongAnswers(List<String> wrongAnswers) {
        this.wrongAnswers = wrongAnswers;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Question coerced = (Question) o;
        return Objects.equals(id, coerced.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
