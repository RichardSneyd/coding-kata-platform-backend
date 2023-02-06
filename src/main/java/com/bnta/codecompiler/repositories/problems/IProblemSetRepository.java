package com.bnta.codecompiler.repositories.problems;

import com.bnta.codecompiler.models.problems.Difficulty;
import com.bnta.codecompiler.models.problems.Problem;
import com.bnta.codecompiler.models.problems.ProblemSet;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;
import java.util.Set;

public interface IProblemSetRepository extends JpaRepository<ProblemSet, Long> {
    public Optional<List<ProblemSet>> findByDifficulty(Difficulty difficulty);
    public Optional<List <ProblemSet>> findByTagsContains(String tag);

    @Modifying
    @Transactional
    @Query(value = "DELETE FROM problem_sets_problems WHERE problem_id = :problemId", nativeQuery = true)
    void deleteProblemFromAllProblemSets(@Param("problemId") Long problemId);




}
