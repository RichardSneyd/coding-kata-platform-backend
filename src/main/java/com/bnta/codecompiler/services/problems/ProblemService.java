package com.bnta.codecompiler.services.problems;

import com.bnta.codecompiler.models.problems.Difficulty;
import com.bnta.codecompiler.models.problems.Problem;
import com.bnta.codecompiler.models.tests.TestCase;
import com.bnta.codecompiler.repositories.problems.IProblemRepository;
import com.bnta.codecompiler.services.tests.TestCaseService;
import com.bnta.codecompiler.services.tests.TestSuiteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@Service
public class ProblemService {
    @Autowired
    IProblemRepository problemRepository;
    @Autowired
    TestSuiteService testSuiteService;


    public Problem add(Problem problem) {
        problem.setTestSuite(testSuiteService.add(problem.getTestSuite()));
        return problemRepository.save(problem);
    }

    public Set<Problem> findAll() {
        return new HashSet<>(problemRepository.findAll());
    }

    public void remove(Problem problem) {
        problemRepository.delete(problem);
    }

    public void remove(Long id) {
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
