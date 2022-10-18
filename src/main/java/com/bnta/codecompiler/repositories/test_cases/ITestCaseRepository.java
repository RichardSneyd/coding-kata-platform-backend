package com.bnta.codecompiler.repositories.test_cases;

import com.bnta.codecompiler.models.TestCase;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ITestCaseRepository extends JpaRepository<TestCase, Long> {
}
