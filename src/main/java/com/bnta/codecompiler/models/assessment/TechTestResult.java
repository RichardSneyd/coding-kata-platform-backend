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
}
