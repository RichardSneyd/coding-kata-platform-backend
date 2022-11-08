package com.bnta.codecompiler.controllers;

import com.bnta.codecompiler.services.users.CohortService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user/cohorts")
public class CohortController {

    @Autowired
    CohortService cohortService;

    @GetMapping
    public ResponseEntity<?> getAll() {
        return ResponseEntity.ok().body(cohortService.find());
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getById(@PathVariable Long id) {
        return ResponseEntity.ok().body(cohortService.find());
    }

    @GetMapping("/by-name/{name}")
    public ResponseEntity<?> getByName(@PathVariable String name) {
        return ResponseEntity.ok().body(cohortService.find());
    }
}
