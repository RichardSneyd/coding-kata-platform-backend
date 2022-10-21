package com.bnta.codecompiler.models.users;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity(name="users")
public class User {
    @Id
    @GeneratedValue
    private Long id;
    @Column
    private String uname;
    @Column
    private String password;
    @Column
    private String cohort = null;
    @Column
    private Role role = Role.USER;

    public User(String uname, String password, String cohort, Role role) {
        this.uname = uname;
        this.password = password;
        this.cohort = cohort;
        if(role != null) this.role = role;
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
}
