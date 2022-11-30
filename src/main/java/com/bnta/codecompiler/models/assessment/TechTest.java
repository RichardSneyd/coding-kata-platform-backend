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


}
