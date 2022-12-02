package com.bnta.codecompiler.repositories.assessment;

import com.bnta.codecompiler.models.assessment.TechTestSession;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

public interface ITechTestSessionRepository extends JpaRepository<TechTestSession, Long> {
}
