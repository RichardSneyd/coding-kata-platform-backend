package com.bnta.codecompiler.models.assessment;

import com.bnta.codecompiler.models.problems.Problem;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import java.util.List;

@Entity
public class TechTest {
    @Id
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
}
