package com.bnta.codecompiler.models.users;

import com.bnta.codecompiler.models.problems.Solution;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.*;

@Entity
@Table(name="users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(unique = true)
    private String username;
    @Column
    private String password;
    @Column
    private String email;
    @JsonIgnoreProperties({"members"})
    @ManyToOne(fetch = FetchType.EAGER)
    private Cohort cohort;
    @ElementCollection(fetch = FetchType.EAGER, targetClass = Role.class)
    private List<Role> roles;
    @Column
    private long score;
    @Column
    private LocalDate joinDate;
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
    @JsonIgnoreProperties({"user"})
    private Set<Solution> solutions;

//    @ManyToMany(fetch = FetchType.EAGER)
//    @JoinTable(name = "users_problems",
//            joinColumns = @JoinColumn(name = "user_id"),
//            inverseJoinColumns = @JoinColumn(name = "problem_id"))
//    private Set<Problem> completedProblems;

    public User(String uname, String email, String password, Cohort cohort, List<Role> roles) {
        this.init();
        this.username = uname;
        this.password = password;
        this.email = email;
        this.cohort = cohort;
        if (roles != null) this.roles = roles;
        this.score = 0;
    }

    public User() {
        init();
    }

    private void init() {
        this.roles = new ArrayList<>();
        this.roles.add(Role.USER);
        this.joinDate = LocalDate.now();
        this.solutions = new HashSet<>();
    }


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    @JsonIgnore
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Cohort getCohort() {
        return cohort;
    }

    public void setCohort(Cohort cohort) {
        this.cohort = cohort;
    }

    public List<Role> getRoles() {
        return roles;
    }

    public void setRoles(List<Role> roles) {
        this.roles = roles;
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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User coerced = (User) o;
        return Objects.equals(id, coerced.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                '}';
    }
}