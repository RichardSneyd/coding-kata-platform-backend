package com.bnta.codecompiler.services.problems;

import com.bnta.codecompiler.models.dtos.EvalResult;
import com.bnta.codecompiler.models.problems.Solution;
import com.bnta.codecompiler.models.problems.Problem;
import com.bnta.codecompiler.models.users.User;
import com.bnta.codecompiler.repositories.problems.ISolutionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@Service
public class SolutionService {
    @Autowired
    ISolutionRepository solutionRepo;

    public Solution add(Solution solution) {
        return solutionRepo.save(solution);
    }

    public void remove(Solution solution) {
        solutionRepo.delete(solution);
    }

    public void remove(Long id) {
        solutionRepo.deleteById(id);
    }

    public Solution findById(Long id) throws Exception{
        Optional<Solution> optional = solutionRepo.findById(id);
        if(optional.isEmpty()) throw new Exception("No solution with id: " + id);
        return optional.get();
    }

    public Set<Solution> findAll() {
        return new HashSet<>(solutionRepo.findAll());
    }

    public Set<Solution> findAllByUser(User user) {
        return solutionRepo.findAllByUser(user);
    }

    public Set<Solution> findAllByUser_id(Long id) {
        return solutionRepo.findAllByUser_id(id);
    }

    public Set<Solution> findAllByProblem(Problem problem) {
        return solutionRepo.findAllByProblem(problem);
    }

    public Set<Solution> findAllByProblemId(Long id) {
        return solutionRepo.findAllByProblem_id(id);
    }

    public Set<Solution> findAllByProblemAndUser(Problem problem, User user) {
        return solutionRepo.findAllByProblemAndUser(problem, user);
    }
 }
