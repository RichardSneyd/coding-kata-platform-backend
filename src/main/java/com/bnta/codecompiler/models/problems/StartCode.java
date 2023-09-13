package com.bnta.codecompiler.models.problems;

import com.bnta.codecompiler.models.assessment.TechTestSession;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name="start_codes")
public class StartCode {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
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

    public void setId(Long id) {
        this.id = id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        StartCode coerced = (StartCode) o;
        return Objects.equals(id, coerced.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
