package com.bnta.codecompiler.services.problems;

import com.bnta.codecompiler.models.problems.Difficulty;
import com.bnta.codecompiler.models.problems.Problem;
import com.bnta.codecompiler.models.problems.Solution;
import com.bnta.codecompiler.models.tests.TestCase;
import com.bnta.codecompiler.models.users.User;
import com.bnta.codecompiler.repositories.problems.IProblemRepository;
import com.bnta.codecompiler.services.tests.TestCaseService;
import com.bnta.codecompiler.services.tests.TestSuiteService;
import com.bnta.codecompiler.services.users.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashSet;
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
    private UserService userService;


    public Problem add(Problem problem) {
        // save the startCode first
        problem.setStartCode(startCodeService.add(problem.getStartCode()));
        problem.setTestSuite(testSuiteService.add(problem.getTestSuite()));
        return problemRepository.save(problem);
    }

    public Set<Problem> findAll() {
        return new HashSet<>(problemRepository.findAll());
    }

    public Problem nextForUser(User user) {
        Set<Long> problemIds = user.getSolutions().stream().map(solution -> solution.getProblem().getId()).collect(Collectors.toSet());
        for(var problem : problemRepository.findAll()) {
            if(!problemIds.contains(problem.getId())) return problem;
        }

        return null;
    }

    public Problem nextForUser(Long userId) throws Exception {
        return nextForUser(userService.findById(userId));
    }


    public Optional<Problem> find(Long id) {return problemRepository.findById(id);}

    public void delete(Problem problem) {
        problemRepository.delete(problem);
    }

    public void delete(Long id) {
        problemRepository.deleteById(id);
    }

    public Problem findById(Long id) throws Exception {
        Optional<Problem> optional = problemRepository.findById(id);
        if(optional.isEmpty()) throw new Exception("No problem with id: " + id);
        return optional.get();
    }

    public Optional<Set<Problem>> findByDifficulty(Difficulty difficulty) {
        return problemRepository.findByDifficulty(difficulty);
    }

    public Optional<Set<Problem>> findByTag(String tag) {
        return problemRepository.findByTags(tag);
    }


}
