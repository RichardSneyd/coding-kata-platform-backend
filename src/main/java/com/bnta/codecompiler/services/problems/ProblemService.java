package com.bnta.codecompiler.services.problems;

import com.bnta.codecompiler.models.problems.Difficulty;
import com.bnta.codecompiler.models.problems.Problem;
import com.bnta.codecompiler.models.problems.Solution;
import com.bnta.codecompiler.models.tests.TestCase;
import com.bnta.codecompiler.models.users.User;
import com.bnta.codecompiler.repositories.problems.IProblemRepository;
import com.bnta.codecompiler.repositories.problems.IProblemSetRepository;
import com.bnta.codecompiler.repositories.problems.ISolutionRepository;
import com.bnta.codecompiler.services.tests.TestCaseService;
import com.bnta.codecompiler.services.tests.TestSuiteService;
import com.bnta.codecompiler.services.users.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
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

    public Problem add(Problem problem) {
        // save the startCode first
        problem.setStartCode(startCodeService.add(problem.getStartCode()));
        problem.setTestSuite(testSuiteService.add(problem.getTestSuite()));
        return problemRepository.save(problem);
    }

    public Problem update(Problem problem) throws Exception {
        if (problemRepository.findById(problem.getId()).isEmpty()) {
            throw new Exception("Cannot update, no problem found with that id");
        }

        startCodeService.update(problem.getStartCode());
        testSuiteService.update(problem.getTestSuite());
        return problemRepository.save(problem);
    }

    public List<Problem> findAll() {
        return problemRepository.findAll();
    }

    public Problem nextForUser(User user) {
        List<Long> problemIds = user.getSolutions().stream().map(solution -> solution.getProblem().getId()).collect(Collectors.toList());
        for (var problem : problemRepository.findAll()) {
            if (!problemIds.contains(problem.getId())) return problem;
        }

        return null;
    }

    public Optional<Problem> find(Long id) {
        return problemRepository.findById(id);
    }

    public void delete(Problem problem) {
        for (var problemSet : problemSetRepository.findAll()) {
            if (problemSet.getProblems().contains(problem)) problemSet.getProblems().remove(problem);
            problemSetRepository.save(problemSet);
        }
        solutionRepository.deleteAll(solutionRepository.findAllByProblem(problem));
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
