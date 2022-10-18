package com.bnta.codecompiler.repositories.tests;

import com.bnta.codecompiler.models.tests.TestCase;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ITestCaseRepository extends JpaRepository<TestCase, Long> {
}
