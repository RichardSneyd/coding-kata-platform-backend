package com.bnta.codecompiler.repositories.problems;

import com.bnta.codecompiler.models.problems.Solution;
import com.bnta.codecompiler.models.problems.Problem;
import com.bnta.codecompiler.models.users.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.Set;

public interface ISolutionRepository extends JpaRepository<Solution, Long> {
    public Set<Solution> findAllByUser(User user);
    public Set<Solution> findAllByUser_id(Long id);
    public Set<Solution> findAllByProblem(Problem problem);
    public Set<Solution> findAllByProblem_id(Long id);
    public Set<Solution> findAllByProblemAndUser(Problem problem, User user);
}
