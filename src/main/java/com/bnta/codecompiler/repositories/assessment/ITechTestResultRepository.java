package com.bnta.codecompiler.repositories.assessment;

import com.bnta.codecompiler.models.assessment.TechTestResult;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

public interface ITechTestResultRepository extends JpaRepository<TechTestResult, Long> {
}
