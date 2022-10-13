package com.bnta.codecompiler.repositories;

import com.bnta.codecompiler.models.Solution;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ISolutionRepository extends JpaRepository<Solution, Long> {
}
