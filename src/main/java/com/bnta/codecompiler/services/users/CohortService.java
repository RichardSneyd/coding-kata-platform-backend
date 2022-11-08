package com.bnta.codecompiler.services.users;

import com.bnta.codecompiler.models.users.Cohort;
import com.bnta.codecompiler.repositories.users.ICohortRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CohortService {
    @Autowired
    ICohortRepository cohortRepo;

    public Cohort add(Cohort cohort) {
      return  cohortRepo.save(cohort);
    }
}
