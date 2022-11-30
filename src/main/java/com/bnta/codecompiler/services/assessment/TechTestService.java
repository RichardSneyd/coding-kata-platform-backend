package com.bnta.codecompiler.services.assessment;

import com.bnta.codecompiler.repositories.assessment.ITechTestRepository;
import com.bnta.codecompiler.repositories.assessment.ITechTestResultRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TechTestService {
    @Autowired
    private ITechTestRepository testRepository;

}
