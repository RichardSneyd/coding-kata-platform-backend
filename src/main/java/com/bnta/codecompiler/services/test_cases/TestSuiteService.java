package com.bnta.codecompiler.services.test_cases;

import com.bnta.codecompiler.models.TestSuite;
import com.bnta.codecompiler.repositories.test_cases.ITestSuiteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
public class TestSuiteService {
    @Autowired
    ITestSuiteRepository testSuiteRepo;

    public TestSuite add(TestSuite testSuite) { return testSuiteRepo.save(testSuite); }
    public TestSuite findById(Long id) throws Exception {
        Optional<TestSuite> optional = testSuiteRepo.findById(id);
        if(optional.isEmpty()) throw new Exception("No test suite with id: " + id);
        return optional.get();
    }

    public Set<TestSuite> findAll() {
        return new HashSet<>(testSuiteRepo.findAll());
    }
}
