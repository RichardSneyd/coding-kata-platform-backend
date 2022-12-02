package com.bnta.codecompiler.models.assessment;

import com.bnta.codecompiler.models.users.User;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name="tech_test_sessions")
public class TechTestSession {
    @Id
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
}
