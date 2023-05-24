package com.bnta.codecompiler.services.tests;

import com.bnta.codecompiler.models.problems.Data;
import com.bnta.codecompiler.models.tests.TestCase;
import com.bnta.codecompiler.repositories.tests.ITestCaseRepository;
import com.bnta.codecompiler.services.problems.DataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class TestCaseService {
    @Autowired
    private ITestCaseRepository testCaseRepo;
    @Autowired
    private DataService dataService;

    public TestCase add(TestCase testCase) {
        for (var data : testCase.getInputs()) {
            dataService.add(data);
        }
        dataService.add(testCase.getOutput());
        return testCaseRepo.save(testCase);
    }

    public TestCase update(TestCase testCase) throws Exception {
        if (testCase.getId() == null) throw new Exception("TestCase id provided is null");
        if (!testCaseRepo.findById(testCase.getId()).isPresent())
            throw new Exception("No TestCase with id " + testCase.getId());

        var existing = testCaseRepo.findById(testCase.getId()).get();
        List<Data> updatedInputs = new ArrayList<>();
        for (var data : testCase.getInputs()) {
            if(data.getId() != null) {
                updatedInputs.add(dataService.update(data));
            } else {
                updatedInputs.add(dataService.add(data));
            }
        }
        existing.setInputs(updatedInputs);

        Data updatedOutput;
        if(testCase.getOutput().getId() != null) {
            updatedOutput = dataService.update(testCase.getOutput());
        } else {
            updatedOutput = dataService.add(testCase.getOutput());
        }
        existing.setOutput(updatedOutput);

        return testCaseRepo.save(existing);
    }


    public TestCase findById(Long id) throws Exception {
        Optional<TestCase> optional = testCaseRepo.findById(id);
        if (optional.isEmpty()) throw new Exception("No test case with id: " + id);
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
