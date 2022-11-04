package com.bnta.codecompiler.services.tests;

import com.bnta.codecompiler.models.tests.TestCase;
import com.bnta.codecompiler.models.tests.TestSuite;
import com.bnta.codecompiler.repositories.tests.ITestSuiteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@Service
public class TestSuiteService {
    @Autowired
    ITestSuiteRepository testSuiteRepo;
    @Autowired
    TestCaseService testCaseService;

    public TestSuite add(TestSuite testSuite) {
        for(var testCase : testSuite.getPublicCases()) {
            testCaseService.add(testCase);
        }
        for(var testCase : testSuite.getPrivateCases()) {
            testCaseService.add(testCase);
        }
        return testSuiteRepo.save(testSuite);
    }
    public TestSuite findById(Long id) throws Exception {
        Optional<TestSuite> optional = testSuiteRepo.findById(id);
        if(optional.isEmpty()) throw new Exception("No test suite with id: " + id);
        return optional.get();
    }

    public Set<TestSuite> findAll() {
        return new HashSet<>(testSuiteRepo.findAll());
    }

    public TestSuiteService addPublicTest(TestCase testCase, TestSuite testSuite) {
        testSuite.getPublicCases().add(testCase);
        return this;
    }

    public TestSuiteService addPublicTest(TestCase testCase, Long testSuiteId) throws Exception {
        return addPublicTest(testCase, findById(testSuiteId));
    }

    public TestSuiteService addPrivateTest(TestCase testCase, TestSuite testSuite) {
        testSuite.getPrivateCases().add(testCase);
        return this;
    }

    public TestSuiteService addPrivateTest(TestCase testCase, Long testSuiteId) throws Exception {
        return addPrivateTest(testCase, findById(testSuiteId));
    }



}
