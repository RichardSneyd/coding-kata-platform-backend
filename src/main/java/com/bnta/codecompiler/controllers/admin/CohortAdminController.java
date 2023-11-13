package com.bnta.codecompiler.controllers.admin;

import com.bnta.codecompiler.models.users.Cohort;
import com.bnta.codecompiler.services.users.CohortService;
import com.bnta.codecompiler.services.users.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;

import java.util.Objects;

@RestController
@RequestMapping("/admin/cohorts/")
public class CohortAdminController {
    @Autowired
    CohortService cohortService;

    @Autowired
    UserService userService;

    @CacheEvict(value = "cohorts", allEntries = true)
    @PostMapping
    public ResponseEntity<?> addNew(@RequestBody Cohort cohort) {
        if(cohortService.findByName(cohort.getName()).isPresent()) return ResponseEntity.status(HttpStatus.CONFLICT)
                .body(String.format("A cohort with the name %s already exists", cohort.getName()));
        return ResponseEntity.ok().body(cohortService.add(cohort));
    }

    @CacheEvict(value = {"cohort", "cohorts"}, allEntries = true)
    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id) {
        var cohort = cohortService.find(id);
        if(cohort.isEmpty()) {
           return ResponseEntity.status(HttpStatus.NOT_FOUND).body(String.format("No cohort with id %s", id));
        }

        cohortService.delete(cohort.get());
        return ResponseEntity.ok(String.format("Deleted cohort with id %s", id));
    }


    @CacheEvict(value = {"cohort", "cohorts"}, allEntries = true)
    @PutMapping
    public ResponseEntity<?> update(@RequestBody Cohort cohort) throws Exception {
        if(cohortService.find(cohort.getId()).isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(String.format("No cohort found with id %s", cohort.getId()));
        }

        this.cohortService.update(cohort);
        return ResponseEntity.ok(String.format("Updated cohort %s", cohort.getName()));
    }
}
