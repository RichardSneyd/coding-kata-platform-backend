package com.bnta.codecompiler.services.problems;

import com.bnta.codecompiler.models.Difficulty;
import com.bnta.codecompiler.models.Problem;
import com.bnta.codecompiler.models.ProblemSet;
import com.bnta.codecompiler.repositories.problems.IProblemSetRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@Service
public class ProblemSetService {
    @Autowired
    IProblemSetRepository problemSetRepo;

    @Autowired
    ProblemService problemService;

    public ProblemSet add(ProblemSet problemSet) {
        return problemSetRepo.save(problemSet);
    }

    public Set<ProblemSet> findAll() {
        return new HashSet<>(problemSetRepo.findAll());
    }

    public void remove(ProblemSet problemSet) {
        problemSetRepo.delete(problemSet);
    }

    public void remove(Long problemSetId) {
        problemSetRepo.deleteById(problemSetId);
    }

    public ProblemSet findById(Long id) throws Exception {
        Optional<ProblemSet> optional = problemSetRepo.findById(id);
        if(optional.isEmpty()) throw new Exception("No problem set with id: " + id);
        return optional.get();
    }

    public Optional<Set<ProblemSet>> findByDifficulty(Difficulty difficulty) {
        return problemSetRepo.findByDifficulty(difficulty);
    }

    public Optional<Set<ProblemSet>> findByTag(String tag) {
        return problemSetRepo.findByTags_tag(tag);
    }

    public ProblemSetService addProblemToSet(Problem problem, ProblemSet set) {
        set.getProblems().add(problem);
        problemSetRepo.save(set);
        return this;
    }

    public ProblemSetService addProblemToSet(Problem problem, Long setId) throws Exception {
        Optional<ProblemSet> optional = problemSetRepo.findById(setId);
        if (optional.isEmpty()) {
            throw new Exception("No ProblemSet with id: " + setId);
        }
        addProblemToSet(problem, optional.get());
        return this;
    }

    public ProblemSetService addProblemToSet(Long problemId, Long setId) throws Exception {
        Problem problem = problemService.findById(problemId);
        return addProblemToSet(problem, setId);
    }


    public ProblemSetService removeProblemFromSet(Problem problem, ProblemSet set) {
        set.getProblems().remove(problem);
        problemSetRepo.save(set);
        return this;
    }

    public ProblemSetService removeProblemFromSet(Problem problem, Long setId) throws Exception {
        ProblemSet set = findById(setId);
        removeProblemFromSet(problem, set);
        return this;
    }
}
