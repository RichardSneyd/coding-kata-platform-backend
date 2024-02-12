package com.bnta.codecompiler.services.problems;

import com.bnta.codecompiler.models.dtos.SolutionDTO;
import com.bnta.codecompiler.models.problems.Solution;
import com.bnta.codecompiler.models.problems.Problem;
import com.bnta.codecompiler.models.users.User;
import com.bnta.codecompiler.repositories.problems.ISolutionRepository;
import com.bnta.codecompiler.services.users.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class SolutionService {
    @Autowired
    private ISolutionRepository solutionRepo;

    @Autowired
    private UserService userService;

    //    @Transactional
    @CacheEvict(value = {"solutions", "solutionsForUser", "solutionsForProblem"}, allEntries = true)
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

    public SolutionDTO getDTObyId(Long id) throws Exception {
        Optional<SolutionDTO> optional = solutionRepo.getDTOById(id);
        if (optional.isEmpty()) throw new Exception("No solution with id: " + id);
        return optional.get();
    }

    public Optional<Solution> findyById(Long id) {
        return solutionRepo.findById(id);
    }

    public Solution update(Solution solution) {
        return solutionRepo.save(solution);
    }

    public Page<SolutionDTO> findAll(Pageable pageable) {
        return solutionRepo.findSolutionsWithUserAndProblemDetails(pageable);
    }


    public List<SolutionDTO> findAllByUser(User user) {
        return solutionRepo.findAllByUser(user);
    }

    public List<SolutionDTO> findAllByUser_id(Long id) {
        return solutionRepo.findAllByUserId(id);
    }

    public List<SolutionDTO> findAllByProblem(Problem problem) {
        return solutionRepo.findAllByProblem(problem);
    }

    public List<SolutionDTO> findAllByProblem_id(Long id) {
        return solutionRepo.findAllByProblemId(id);
    }

    public List<SolutionDTO> findAllByProblemAndUser(Problem problem, User user) {
        return solutionRepo.findAllByProblemAndUser(problem, user);
    }
}
