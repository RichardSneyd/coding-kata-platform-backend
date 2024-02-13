package com.bnta.codecompiler.services.users;

import com.bnta.codecompiler.models.dtos.CohortDTO;
import com.bnta.codecompiler.models.users.Cohort;
import com.bnta.codecompiler.models.users.User;
import com.bnta.codecompiler.repositories.users.ICohortRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class CohortService {
    @Autowired
    private ICohortRepository cohortRepo;
    @Autowired
    private UserService userService;

    @Transactional
    public Cohort update(Cohort cohort) throws Exception {
        var existingOptional = cohortRepo.findById(cohort.getId());
        if (existingOptional.isEmpty()) {
            throw new Exception("No cohort by that id to update");
        }

        var existing = existingOptional.get();

        if (cohort.getName() != null) {
            existing.setName(cohort.getName());
        }

        // Update members if the new member list is not null
        if (cohort.getMembers() != null) {
            // Create a list to store members that should be removed
            List<User> membersToRemove = new ArrayList<>();

            // Identify members to be removed
            for (var m : existing.getMembers()) {
                if (!cohort.getMembers().contains(m)) {
                    m.setCohort(null);
                    userService.update(m);
                    membersToRemove.add(m);
                }
            }

            // Remove members outside of the iteration loop
            existing.getMembers().removeAll(membersToRemove);

            // Add or update new members
            for (var m : cohort.getMembers()) {
                if (!existing.getMembers().contains(m)) {
                    m = userService.add(m);
                } else {
                    userService.update(m);
                }
                m.setCohort(cohort);
            }

            // Update the members in the existing cohort
            existing.setMembers(cohort.getMembers());
        }

        cohortRepo.save(existing);

        return cohort;
    }



    @Transactional
    public Cohort add(Cohort cohort) {
        cohort = cohortRepo.save(cohort);
        for(var m : cohort.getMembers()) {
            m.setCohort(cohort);
            userService.add(m);
        }
        cohort = cohortRepo.save(cohort);
        return cohort;
    }

    public Page<Cohort> find(Pageable pageable) {
        return cohortRepo.findAll(pageable);
    }

    public Page<Cohort> orderByStartDateDesc(Pageable pageable) {
        return cohortRepo.findAllByOrderByStartDateDesc(pageable);
    }

    public Page<CohortDTO>getListOfCohorts (Pageable pageable) {
        return cohortRepo.getListOfCohorts(pageable);
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
