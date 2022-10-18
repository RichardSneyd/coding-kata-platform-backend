package com.bnta.codecompiler.repositories.tests;

import com.bnta.codecompiler.models.tests.TestSuite;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ITestSuiteRepository extends JpaRepository<TestSuite, Long> {
}
