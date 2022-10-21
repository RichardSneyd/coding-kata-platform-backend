package com.bnta.codecompiler.models.problems;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity(name="start_codes")
public class StartCode {
    @Id
    @GeneratedValue
    private Long id;
    @Column
    private String js;
    @Column
    private String py;
    @Column
    private String java;

    public StartCode(String js, String py, String java) {
        this.js = js;
        this.py = py;
        this.java = java;
    }

    public StartCode() {
    }

    public String getJs() {
        return js;
    }

    public void setJs(String js) {
        this.js = js;
    }

    public String getPy() {
        return py;
    }

    public void setPy(String py) {
        this.py = py;
    }

    public String getJava() {
        return java;
    }

    public void setJava(String java) {
        this.java = java;
    }

    public Long getId() {return this.id;}
}
