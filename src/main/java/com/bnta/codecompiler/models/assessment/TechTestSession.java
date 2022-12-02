package com.bnta.codecompiler.models.assessment;

import com.bnta.codecompiler.models.users.User;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import java.time.LocalDate;

@Entity(name="tech_test_sessions")
public class TechTestSession {
    @Id
    private Long id;

    @OneToOne
    private User user;

    @OneToOne
    private TechTest test;

    @OneToOne
    private TechTestResult result;

    private LocalDate startTime;

    public TechTestSession(User user, TechTest test, TechTestResult result, LocalDate startTime) {
        this.user = user;
        this.test = test;
        this.result = result;
        this.startTime = startTime;
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

    public LocalDate getStartTime() {
        return startTime;
    }

    public void setStartTime(LocalDate startTime) {
        this.startTime = startTime;
    }
}
