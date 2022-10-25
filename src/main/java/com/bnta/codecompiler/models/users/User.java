package com.bnta.codecompiler.models.users;

import com.bnta.codecompiler.models.problems.Solution;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@Entity(name="users")
public class User {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long id;
    @Column
    private String uname;
    @Column
    private String password;
    @Column
    private String cohort = null;
    @Column
    private Role role = Role.USER;
    @Column
    private long score;
    @Column
    private LocalDate joinDate;

    @OneToMany
    private Set<Solution> solutions;

    public User(String uname, String password, String cohort, Role role) {
        this.uname = uname;
        this.password = password;
        this.cohort = cohort;
        if(role != null) this.role = role;
        this.solutions = new HashSet();
        this.score = 0;
        this.joinDate = LocalDate.now();
    }

    public User() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUname() {
        return uname;
    }

    public void setUname(String uname) {
        this.uname = uname;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getCohort() {
        return cohort;
    }

    public void setCohort(String cohort) {
        this.cohort = cohort;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public Set<Solution> getSolutions() {
        return solutions;
    }

    public void setSolutions(Set<Solution> solutions) {
        this.solutions = solutions;
    }

    public long getScore() {
        return score;
    }

    public void setScore(long score) {
        this.score = score;
    }

    public LocalDate getJoinDate() {
        return joinDate;
    }

    public void setJoinDate(LocalDate joinDate) {
        this.joinDate = joinDate;
    }
}
