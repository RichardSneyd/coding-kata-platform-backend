package com.bnta.codecompiler.controllers;

import com.bnta.codecompiler.models.users.Cohort;
import com.bnta.codecompiler.services.users.CohortService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.cache.annotation.Cacheable;

@RestController
@RequestMapping("/user/cohorts")
public class CohortController {

    @Autowired
    CohortService cohortService;

    @Cacheable(value = "cohorts", key = "'page:' + #pageable.pageNumber + '-size:' + #pageable.pageSize")
    @GetMapping
    public ResponseEntity<?> getAll(@PageableDefault Pageable pageable) {
        return ResponseEntity.ok().body(cohortService.orderByStartDateDesc(pageable));
    }

    @Cacheable(value = "cohort", key = "#id")
    @GetMapping("/{id}")
    public ResponseEntity<?> getById(@PathVariable Long id) {
        return ResponseEntity.ok().body(cohortService.find(id));
    }

}
