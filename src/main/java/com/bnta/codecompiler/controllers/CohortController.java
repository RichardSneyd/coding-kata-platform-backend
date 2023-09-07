package com.bnta.codecompiler.controllers;

import com.bnta.codecompiler.models.users.Cohort;
import com.bnta.codecompiler.services.users.CohortService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.cache.annotation.Cacheable;

@RestController
@RequestMapping("/user/cohorts")
public class CohortController {

    @Autowired
    CohortService cohortService;

    @Cacheable(value = "cohorts")
    @GetMapping
    public ResponseEntity<?> getAll() {
        return ResponseEntity.ok().body(cohortService.find());
    }

    @Cacheable(value = "cohort", key = "#id")
    @GetMapping("/{id}")
    public ResponseEntity<?> getById(@PathVariable Long id) {
        return ResponseEntity.ok().body(cohortService.find(id));
    }

    @Cacheable(value = "cohortsByName", key = "#name")
    @GetMapping("/by-name/{name}")
    public ResponseEntity<?> getByName(@PathVariable String name) {
        return ResponseEntity.ok().body(cohortService.find());
    }
}
