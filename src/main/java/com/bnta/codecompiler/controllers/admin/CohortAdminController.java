package com.bnta.codecompiler.controllers.admin;

import com.bnta.codecompiler.models.users.Cohort;
import com.bnta.codecompiler.services.users.CohortService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/admin/cohorts/")
public class CohortAdminController {
    @Autowired
    CohortService cohortService;

    @PostMapping
    public ResponseEntity<?> addNew(@RequestBody Cohort cohort) {
        return ResponseEntity.ok().body(cohortService.add(cohort));
    }
}
