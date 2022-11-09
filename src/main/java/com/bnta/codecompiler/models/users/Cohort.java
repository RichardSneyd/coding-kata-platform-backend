package com.bnta.codecompiler.models.users;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Cohort {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column
    private String name;
    @Column
    private LocalDate startDate;

    @JsonIgnoreProperties({"cohort"})
    @OneToMany(mappedBy="cohort")
    private List<User> members;


    public Cohort(String name, List<User> members) {
        this.name = name;
        this.members = members != null ? members : new ArrayList<>();
        this.startDate = LocalDate.now();
    }

    public Cohort (String name) {
        this.name = name;
        this.members = new ArrayList<>();
    }

    public Cohort() {
        this.members = new ArrayList<>();
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
