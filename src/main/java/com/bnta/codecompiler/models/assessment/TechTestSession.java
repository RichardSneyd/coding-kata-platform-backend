package com.bnta.codecompiler.models.assessment;

import com.bnta.codecompiler.models.users.User;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import java.time.LocalDate;

@Entity
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
}
