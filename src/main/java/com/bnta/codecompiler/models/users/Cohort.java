package com.bnta.codecompiler.models.users;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "cohorts")
public class Cohort {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(unique = true)
    private String name;
    @Column
    private LocalDate startDate;

    @JsonIgnoreProperties(value = {"cohort"}, allowSetters = true)
    @OneToMany(mappedBy = "cohort")
    private List<User> members;


    public Cohort(String name, List<User> members) {
        this.init();
        this.name = name;
        this.members = members != null ? members : new ArrayList<>();
    }

    public Cohort(String name) {
        this.init();
        this.name = name;
    }

    public Cohort() {
        this.init();
    }

    private void init() {
        this.members = new ArrayList<>();
        this.startDate = LocalDate.now();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public List<User> getMembers() {
        return members;
    }

    public void setMembers(List<User> members) {
        this.members = members;
    }
}
