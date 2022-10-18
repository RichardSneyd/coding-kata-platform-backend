package com.bnta.codecompiler.repositories.problems;

import com.bnta.codecompiler.models.problems.Difficulty;
import com.bnta.codecompiler.models.problems.Problem;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.Set;

public interface IProblemRepository extends JpaRepository<Problem, Long> {
    public Optional<Set<Problem>> findByDifficulty(Difficulty difficulty);
    public Optional<Set<Problem>> findByTags_tag(String tag);
}
