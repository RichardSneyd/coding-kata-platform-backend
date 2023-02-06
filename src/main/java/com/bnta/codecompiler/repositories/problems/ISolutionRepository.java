package com.bnta.codecompiler.repositories.problems;

import com.bnta.codecompiler.models.problems.Solution;
import com.bnta.codecompiler.models.problems.Problem;
import com.bnta.codecompiler.models.users.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;
import java.util.Set;

public interface ISolutionRepository extends JpaRepository<Solution, Long> {
    public List<Solution> findAllByUser(User user);
    public List<Solution> findAllByUser_id(Long id);
    public List<Solution> findAllByProblem(Problem problem);
    public List<Solution> findAllByProblem_id(Long id);

    @Transactional
    public void deleteAllByProblem_id(Long id);
    public List<Solution> findAllByProblemAndUser(Problem problem, User user);
}
