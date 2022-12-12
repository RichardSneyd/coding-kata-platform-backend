package com.bnta.codecompiler.controllers.admin;

import com.bnta.codecompiler.models.users.Cohort;
import com.bnta.codecompiler.services.users.CohortService;
import com.bnta.codecompiler.services.users.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Objects;

@RestController
@RequestMapping("/admin/cohorts/")
public class CohortAdminController {
    @Autowired
    CohortService cohortService;

    @Autowired
    UserService userService;

    @PostMapping
    public ResponseEntity<?> addNew(@RequestBody Cohort cohort) {
        return ResponseEntity.ok().body(cohortService.add(cohort));
    }

    @DeleteMapping("/{cohortId}")
    public ResponseEntity<?> delete(@PathVariable Long id) {
        var cohort = cohortService.find(id);
        if(cohort.isEmpty()) {
           return ResponseEntity.status(HttpStatus.NOT_FOUND).body(String.format("No cohort with id $s", id));
        }

        for(var member: cohort.get().getMembers()) {
            userService.delete(member);
        }

        cohortService.delete(id);
        return ResponseEntity.ok(String.format("Delete cohort with id $s", id));
    }


    @PutMapping
    public ResponseEntity<?> update(@RequestBody Cohort cohort) {
        if(cohortService.find(cohort.getId()).isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(String.format("No cohort found with id $s", cohort.getId()));
        }

        this.cohortService.update(cohort);
        return ResponseEntity.ok(String.format("Updated cohort $s", cohort.getName()));
    }
}
