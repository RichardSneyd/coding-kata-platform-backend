package com.bnta.codecompiler.services.users;

import com.bnta.codecompiler.models.users.Cohort;
import com.bnta.codecompiler.repositories.users.ICohortRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Service
public class CohortService {
    @Autowired
    private ICohortRepository cohortRepo;
    @Autowired
    private UserService userService;

    public Cohort update(Cohort cohort) {
        cohortRepo.save(cohort);
        for(var m : cohort.getMembers()) {
            m.setCohort(cohort);
            userService.update(m);
        }
        return cohort;
    }


    @Transactional
    public Cohort add(Cohort cohort) {
        cohort = cohortRepo.save(cohort);
        for(var m : cohort.getMembers()) {
          //  m.setCohort(cohort);
            userService.add(m);
        }
        cohort = cohortRepo.save(cohort);
//        for(var m : cohort.getMembers()) {
//            m.setCohort(cohort);
//            userService.update(m);
//        }
        return cohort;
    }

    public Page<Cohort> find(Pageable pageable) {
        return cohortRepo.findAll(pageable);
    }

    public Page<Cohort> orderByStartDateDesc(Pageable pageable) {
        return cohortRepo.findAllByOrderByStartDateDesc(pageable);
    }

    public Optional<Cohort> find(Long cohortId) {
        return cohortRepo.findById(cohortId);
    }



    public Optional<Cohort> find(String name) {
        return cohortRepo.findByName(name);
    }

    public Optional<Cohort> findByName(String name) {
        return cohortRepo.findByName(name);
    }

    public void delete(Cohort cohort) {
        for(var member: cohort.getMembers()) {
            userService.delete(member);
        }
        cohortRepo.delete(cohort);
    }

    public void delete(Long id) {
        cohortRepo.delete(cohortRepo.findById(id).get());
    }

}
