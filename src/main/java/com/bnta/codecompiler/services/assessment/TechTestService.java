package com.bnta.codecompiler.services.assessment;

import com.bnta.codecompiler.models.assessment.TechTest;
import com.bnta.codecompiler.repositories.assessment.ITechTestRepository;
import com.bnta.codecompiler.repositories.assessment.ITechTestResultRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TechTestService {
    @Autowired
    private ITechTestRepository testRepository;

    public List<TechTest> find() {
        return testRepository.findAll();
    }
}
