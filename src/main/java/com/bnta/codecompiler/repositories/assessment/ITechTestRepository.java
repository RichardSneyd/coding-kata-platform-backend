package com.bnta.codecompiler.repositories.assessment;

import com.bnta.codecompiler.models.assessment.TechTest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ITechTestRepository extends JpaRepository<TechTest, Long> {

}
