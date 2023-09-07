package com.bnta.codecompiler.services.problems;

import com.bnta.codecompiler.models.problems.Difficulty;
import com.bnta.codecompiler.models.problems.Problem;
import com.bnta.codecompiler.models.users.User;
import com.bnta.codecompiler.repositories.problems.IProblemRepository;
import com.bnta.codecompiler.repositories.problems.IProblemSetRepository;
import com.bnta.codecompiler.repositories.problems.ISolutionRepository;
import com.bnta.codecompiler.repositories.users.IUserRepository;
import com.bnta.codecompiler.services.tests.TestSuiteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
//@Transactional
public class ProblemService {
    @Autowired
    private IProblemRepository problemRepository;
    @Autowired
    private TestSuiteService testSuiteService;
    @Autowired
    private StartCodeService startCodeService;

    @Autowired
    private IProblemSetRepository problemSetRepository;
    @Autowired
    private ISolutionRepository solutionRepository;
    @Autowired
    private IUserRepository userRepository;


    public Problem add(Problem problem) {
        // save the startCode first
        problem.setStartCode(startCodeService.add(problem.getStartCode()));
        problem.setTestSuite(testSuiteService.add(problem.getTestSuite()));
        return problemRepository.save(problem);
    }


    public Problem update(Problem problem) throws Exception {
        if (problem == null || problem.getId() == null) {
            throw new IllegalArgumentException("Problem or problem id must not be null");
        }
        var optional = problemRepository.findById(problem.getId());
        if (optional.isEmpty()) throw new EntityNotFoundException("Cannot update, no problem found with that id");
        var existing = optional.get();

        if (problem.getTestSuite() != null) existing.setTestSuite(testSuiteService.update(problem.getTestSuite()));
        if (problem.getStartCode() != null) existing.setStartCode(startCodeService.update(problem.getStartCode()));

        if (problem.getDescription() != null) existing.setDescription(problem.getDescription());
        if (problem.getTitle() != null) existing.setTitle(problem.getTitle());
        if (problem.getTags() != null) existing.setTags(problem.getTags());
        if (problem.getDifficulty() != null) existing.setDifficulty(problem.getDifficulty());

        return problemRepository.save(existing);
    }


    public List<Problem> findAll() {
        return problemRepository.findAll();
    }

    public Optional<Problem> nextForUser(User user) {
        List<Long> problemIds = user.getSolutions().stream().map(solution -> solution.getProblem().getId()).collect(Collectors.toList());

        // Find a problem that the user hasn't solved yet
        return problemRepository.findFirstByIdNotIn(problemIds);
    }

    public Optional<Problem> find(Long id) {
        return problemRepository.findById(id);
    }

    public void delete(Problem problem) {
        problemSetRepository.deleteProblemFromAllProblemSets(problem.getId());
        userRepository.deleteProblemFromAllUsers(problem.getId());
        solutionRepository.deleteAllByProblem_id(problem.getId());
        problemRepository.delete(problem);
    }


    public Problem findById(Long id) throws Exception {
        Optional<Problem> optional = problemRepository.findById(id);
        if (optional.isEmpty()) throw new Exception("No problem with id: " + id);
        return optional.get();
    }

    public List<Problem> findByDifficulty(Difficulty difficulty) {
        return problemRepository.findByDifficulty(difficulty);
    }

    public List<Problem> findByTag(String tag) {
        return problemRepository.findByTags(tag);
    }


}
