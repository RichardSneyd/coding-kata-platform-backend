package com.bnta.codecompiler.models.quizes;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import java.util.Arrays;
import java.util.List;

@Entity(name = "quizzes")
public class Quiz {
    @Id
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
}
