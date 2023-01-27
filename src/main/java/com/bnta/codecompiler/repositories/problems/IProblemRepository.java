package com.bnta.codecompiler.repositories.problems;

import com.bnta.codecompiler.models.problems.Difficulty;
import com.bnta.codecompiler.models.problems.Problem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.Set;

public interface IProblemRepository extends JpaRepository<Problem, Long> {
    public Optional<List<Problem>> findByDifficulty(Difficulty difficulty);
    public Optional<List<Problem>> findByTags(String tag);
}
