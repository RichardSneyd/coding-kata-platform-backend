package com.bnta.codecompiler.repositories;

import com.bnta.codecompiler.models.Problem;
import com.bnta.codecompiler.models.TestSuite;
import org.springframework.boot.autoconfigure.data.jpa.JpaRepositoriesAutoConfiguration;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ITestSuiteRepository extends JpaRepository<TestSuite, Long> {
}
