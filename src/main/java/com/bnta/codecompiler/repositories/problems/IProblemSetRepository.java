package com.bnta.codecompiler.repositories.problems;

import com.bnta.codecompiler.models.problems.Difficulty;
import com.bnta.codecompiler.models.problems.ProblemSet;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.Set;

public interface IProblemSetRepository extends JpaRepository<ProblemSet, Long> {
    public Optional<Set<ProblemSet>> findByDifficulty(Difficulty difficulty);
    public Optional<Set<ProblemSet>> findByTagsContains(String tag);
}
