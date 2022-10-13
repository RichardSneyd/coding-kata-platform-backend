package com.bnta.codecompiler.repositories;

import com.bnta.codecompiler.models.Problem;
import com.bnta.codecompiler.models.TestCase;
import org.springframework.boot.autoconfigure.data.jpa.JpaRepositoriesAutoConfiguration;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ITestCaseRepository extends JpaRepository<TestCase, Long> {
}
