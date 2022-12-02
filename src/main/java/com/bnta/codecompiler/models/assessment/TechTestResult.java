package com.bnta.codecompiler.models.assessment;

import com.bnta.codecompiler.models.problems.Solution;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import java.util.List;

@Entity
public class TechTestResult {
    @Id
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
}
