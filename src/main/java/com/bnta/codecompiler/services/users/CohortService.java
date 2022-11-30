package com.bnta.codecompiler.services.users;

import com.bnta.codecompiler.models.users.Cohort;
import com.bnta.codecompiler.repositories.users.ICohortRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CohortService {
    @Autowired
    private ICohortRepository cohortRepo;
    @Autowired
    private UserService userService;

    public Cohort add(Cohort cohort) {
        cohortRepo.save(cohort);
        for(var m : cohort.getMembers()) {
            m.setCohort(cohort);
            userService.add(m);
        }
        return cohort;
    }

    public List<Cohort> find() {
        return cohortRepo.findAll();
    }

    public Optional<Cohort> find(Long cohortId) {
        return cohortRepo.findById(cohortId);
    }

    public Optional<Cohort> find(String name) {
        return cohortRepo.findByName(name);
    }

}
