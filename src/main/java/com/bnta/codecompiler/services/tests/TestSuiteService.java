package com.bnta.codecompiler.services.tests;

import com.bnta.codecompiler.models.tests.TestCase;
import com.bnta.codecompiler.models.tests.TestSuite;
import com.bnta.codecompiler.repositories.tests.ITestSuiteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class TestSuiteService {
    @Autowired
    private ITestSuiteRepository testSuiteRepo;
    @Autowired
    private TestCaseService testCaseService;

    public TestSuite add(TestSuite testSuite) {
        for(var testCase : testSuite.getPublicCases()) {
            testCaseService.add(testCase);
        }
        for(var testCase : testSuite.getPrivateCases()) {
            testCaseService.add(testCase);
        }
        return testSuiteRepo.save(testSuite);
    }

    public TestSuite update(TestSuite testSuite) throws Exception {
        var existing = testSuiteRepo.findById(testSuite.getId());
        if(existing.isEmpty()) throw new Exception("No TestSuite with id" + testSuite.getId());

        List<TestCase> updatedPublicCases = new LinkedList<>();
        for(var testCase : testSuite.getPublicCases()) {
            if(testCase.getId() != null) {
                updatedPublicCases.add(testCaseService.update(testCase));
            } else {
                updatedPublicCases.add(testCaseService.add(testCase));
            }
        }
        existing.get().setPublicCases(updatedPublicCases);

        List<TestCase> updatedPrivateCases = new LinkedList<>();
        for(var testCase : testSuite.getPrivateCases()) {
            if(testCase.getId() != null) {
                updatedPrivateCases.add(testCaseService.update(testCase));
            } else {
                updatedPrivateCases.add(testCaseService.add(testCase));
            }
        }
        existing.get().setPrivateCases(updatedPrivateCases);

        if(testSuite.getProblem() != null) existing.get().setProblem(testSuite.getProblem());
        return testSuiteRepo.save(existing.get());
    }


    private void updateTestCases(TestSuite testSuite) {
        for(var testCase : testSuite.getPublicCases()) {
            testCaseService.add(testCase);
        }
        for(var testCase : testSuite.getPrivateCases()) {
            testCaseService.add(testCase);
        }
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
