package com.bnta.codecompiler.models.assessment;

import com.bnta.codecompiler.models.problems.Data;
import com.bnta.codecompiler.models.problems.Solution;

import javax.persistence.*;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name="tech_test_results")
public class TechTestResult {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToMany
    private List<Solution> solutions;

    public TechTestResult(List<Solution> solutions) {
        this.solutions = solutions;
    }

    public TechTestResult() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        System.out.println("can't change id this way");
    }

    public List<Solution> getSolutions() {
        return solutions;
    }

    public void setSolutions(List<Solution> solutions) {
        this.solutions = solutions;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TechTestResult result = (TechTestResult) o;
        return Objects.equals(id, result.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
