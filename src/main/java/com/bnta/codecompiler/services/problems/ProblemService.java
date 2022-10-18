package com.bnta.codecompiler.services.problems;

import com.bnta.codecompiler.models.Difficulty;
import com.bnta.codecompiler.models.Problem;
import com.bnta.codecompiler.repositories.problems.IProblemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@Service
public class ProblemService {
    @Autowired
    IProblemRepository problemRepository;

    public Problem add(Problem problem) {
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
        return problemRepository.findByTags_tag(tag);
    }


}
