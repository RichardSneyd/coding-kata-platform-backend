package com.bnta.codecompiler.models.assessment;

import com.bnta.codecompiler.models.problems.Data;
import com.bnta.codecompiler.models.users.User;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Objects;

@Entity
@Table(name="tech_test_sessions")
public class TechTestSession {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    private User user;

    @OneToOne
    private TechTest test;

    @OneToOne
    private TechTestResult result;

    private LocalDateTime startTime;

    public TechTestSession(User user, TechTest test, TechTestResult result) {
        this.user = user;
        this.test = test;
        this.result = result;
        this.startTime = LocalDateTime.now();
    }

    public TechTestSession() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        System.out.println("can't change id this way");
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public TechTest getTest() {
        return test;
    }

    public void setTest(TechTest test) {
        this.test = test;
    }

    public TechTestResult getResult() {
        return result;
    }

    public void setResult(TechTestResult result) {
        this.result = result;
    }

    public LocalDateTime getStartTime() {
        return startTime;
    }

    public void setStartTime(LocalDateTime startTime) {
        this.startTime = startTime;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TechTestSession coerced = (TechTestSession) o;
        return Objects.equals(id, coerced.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
