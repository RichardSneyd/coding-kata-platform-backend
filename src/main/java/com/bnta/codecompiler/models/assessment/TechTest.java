package com.bnta.codecompiler.models.assessment;

import com.bnta.codecompiler.models.problems.Data;
import com.bnta.codecompiler.models.problems.Problem;

import javax.persistence.*;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name="tech_tests")
public class TechTest {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Integer maxMinutes = 120;

    @OneToMany
    private List<Problem> problems;

    public TechTest(Integer maxMinutes, List<Problem> problems) {
        this.maxMinutes = maxMinutes;
        this.problems = problems;
    }

    public TechTest() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        System.out.println("can't change id this way");
    }

    public Integer getMaxMinutes() {
        return maxMinutes;
    }

    public void setMaxMinutes(Integer maxMinutes) {
        this.maxMinutes = maxMinutes;
    }

    public List<Problem> getProblems() {
        return problems;
    }

    public void setProblems(List<Problem> problems) {
        this.problems = problems;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TechTest test = (TechTest) o;
        return Objects.equals(id, test.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
