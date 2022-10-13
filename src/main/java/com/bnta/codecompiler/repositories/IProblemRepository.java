package com.bnta.codecompiler.repositories;

import com.bnta.codecompiler.models.Problem;
import org.springframework.boot.autoconfigure.data.jpa.JpaRepositoriesAutoConfiguration;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IProblemRepository extends JpaRepository<Problem, Long> {
}
