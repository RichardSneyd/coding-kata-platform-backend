package com.bnta.codecompiler.repositories;

import com.bnta.codecompiler.models.Problem;
import com.bnta.codecompiler.models.ProblemSet;
import org.springframework.boot.autoconfigure.data.jpa.JpaRepositoriesAutoConfiguration;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IProblemSetRepository extends JpaRepository<ProblemSet, Long> {
}
