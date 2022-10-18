package com.bnta.codecompiler.repositories.test_cases;

import com.bnta.codecompiler.models.TestSuite;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ITestSuiteRepository extends JpaRepository<TestSuite, Long> {
}
