package com.bnta.codecompiler.repositories.problems;

import com.bnta.codecompiler.models.Difficulty;
import com.bnta.codecompiler.models.Problem;
import com.bnta.codecompiler.models.ProblemSet;
import org.springframework.boot.autoconfigure.data.jpa.JpaRepositoriesAutoConfiguration;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.Set;

public interface IProblemSetRepository extends JpaRepository<ProblemSet, Long> {
    public Optional<Set<ProblemSet>> findByDifficulty(Difficulty difficulty);
    public Optional<Set<ProblemSet>> findByTags_tag(String tag);
}
