package com.bnta.codecompiler.services.tests;

import com.bnta.codecompiler.models.problems.Data;
import com.bnta.codecompiler.models.tests.TestCase;
import com.bnta.codecompiler.repositories.tests.ITestCaseRepository;
import com.bnta.codecompiler.services.problems.DataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@Service
public class TestCaseService {
    @Autowired
    private ITestCaseRepository testCaseRepo;
    @Autowired
    private DataService dataService;

    public TestCase add(TestCase testCase) {
        for(var data : testCase.getInputs()) {
            dataService.add(data);
        }
        dataService.add(testCase.getOutput());
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

    public TestCase addArg(Data arg, TestCase testCase) {
        testCase.getInputs().add(arg);
        return testCase;
    }

    public TestCase addArg(Data arg, Long id) throws Exception {
        return addArg(arg, findById(id));
    }

}
