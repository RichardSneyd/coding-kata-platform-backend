package com.bnta.codecompiler.services.problems;

import com.bnta.codecompiler.models.problems.Solution;
import com.bnta.codecompiler.models.problems.Problem;
import com.bnta.codecompiler.models.users.User;
import com.bnta.codecompiler.repositories.problems.ISolutionRepository;
import com.bnta.codecompiler.services.users.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class SolutionService {
    @Autowired
    private ISolutionRepository solutionRepo;

    @Autowired
    private UserService userService;

    public Solution add(Solution solution) {
        userService.addSolution(solution.getUser(), solution);
        solution = solutionRepo.save(solution);
        return solution;
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

    public Solution update(Solution solution) {
        return solutionRepo.save(solution);
    }

    public List<Solution> findAll() {
        return solutionRepo.findAll();
    }

    public List<Solution> findAllByUser(User user) {
        return solutionRepo.findAllByUser(user);
    }

    public List<Solution> findAllByUser_id(Long id) {
        return solutionRepo.findAllByUser_id(id);
    }

    public List<Solution> findAllByProblem(Problem problem) {
        return solutionRepo.findAllByProblem(problem);
    }

    public List<Solution> findAllByProblem_id(Long id) {
        return solutionRepo.findAllByProblem_id(id);
    }

    public List<Solution> findAllByProblemAndUser(Problem problem, User user) {
        return solutionRepo.findAllByProblemAndUser(problem, user);
    }
 }
