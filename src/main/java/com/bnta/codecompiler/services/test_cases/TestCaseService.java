package com.bnta.codecompiler.services.test_cases;

import com.bnta.codecompiler.models.TestCase;
import com.bnta.codecompiler.repositories.test_cases.ITestCaseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@Service
public class TestCaseService {
    @Autowired
    private ITestCaseRepository testCaseRepo;

    public TestCase add(TestCase testCase) {
        return testCaseRepo.save(testCase);
    }

    public TestCase findById(Long id) throws Exception {
        Optional<TestCase> optional = testCaseRepo.findById(id);
        if(optional.isEmpty()) throw new Exception("No test case with id: " + id);
        return optional.get();
    }

    public Set<TestCase> findAll() {
        return new HashSet(testCaseRepo.findAll());
    }

    public void remove(TestCase testCase) {
        testCaseRepo.delete(testCase);
    }

    public void remove(Long id) throws Exception {
        TestCase testCase = findById(id);
        testCaseRepo.delete(testCase);
    }

}
