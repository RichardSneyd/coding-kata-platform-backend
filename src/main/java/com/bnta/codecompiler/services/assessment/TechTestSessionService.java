package com.bnta.codecompiler.services.assessment;

import com.bnta.codecompiler.models.assessment.TechTest;
import com.bnta.codecompiler.models.assessment.TechTestSession;
import com.bnta.codecompiler.repositories.assessment.ITechTestSessionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TechTestSessionService {
    @Autowired
    private ITechTestSessionRepository testSessionRepo;

    public List<TechTestSession> find() {
        return testSessionRepo.findAll();
    }
}
