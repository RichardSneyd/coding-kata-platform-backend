package com.bnta.codecompiler.services.problems;

import com.bnta.codecompiler.models.problems.Difficulty;
import com.bnta.codecompiler.models.problems.Problem;
import com.bnta.codecompiler.models.problems.ProblemSet;
import com.bnta.codecompiler.models.users.Cohort;
import com.bnta.codecompiler.repositories.problems.IProblemSetRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
public class ProblemSetService {
    @Autowired
    private IProblemSetRepository problemSetRepo;

    @Autowired
    private ProblemService problemService;

    public ProblemSet add(ProblemSet problemSet) {
        return problemSetRepo.save(problemSet);
    }

    public List<ProblemSet> findAll() {
        return problemSetRepo.findAll();
    }

    public void remove(ProblemSet problemSet) {
        problemSet.getProblems().removeAll(problemSet.getProblems());
        problemSetRepo.save(problemSet);
        problemSetRepo.delete(problemSet);
    }

    public void remove(Long problemSetId) throws Exception {
        var problemSet = problemSetRepo.findById(problemSetId);
        if(problemSet.isEmpty()) throw new Exception("No problemSet with id " + problemSetId + ", cannot remove");
        remove(problemSet.get());
    }

    public Optional<ProblemSet> findById(Long id) {
        Optional<ProblemSet> optional = problemSetRepo.findById(id);
        return optional;
    }

    public Optional<List<ProblemSet>> findByDifficulty(Difficulty difficulty) {
        return problemSetRepo.findByDifficulty(difficulty);
    }

    public Optional<List<ProblemSet>> findByTag(String tag) {
        return problemSetRepo.findByTagsContains(tag);
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
        var set = findById(setId);
        if(set.isEmpty()) throw new Exception("No problem set with that id");
        removeProblemFromSet(problem, set.get());
        return this;
    }

    public ProblemSet update(ProblemSet set) {
//        for(var p : set.getProblems()) {
//            problemService.add(p);
//        }

        problemSetRepo.save(set);
        return set;
    }
}
