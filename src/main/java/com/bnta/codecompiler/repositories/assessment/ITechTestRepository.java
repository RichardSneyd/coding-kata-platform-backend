package com.bnta.codecompiler.repositories.assessment;

import com.bnta.codecompiler.models.assessment.TechTest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

public interface ITechTestRepository extends JpaRepository<TechTest, Long> {

    @Query("FROM tech_tests t")
    List<TechTest> find();
}
