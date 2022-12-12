package com.bnta.codecompiler.controllers.admin;

import com.bnta.codecompiler.models.assessment.TechTest;
import com.bnta.codecompiler.services.assessment.TechTestResultService;
import com.bnta.codecompiler.services.assessment.TechTestService;
import com.bnta.codecompiler.services.assessment.TechTestSessionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TechTestAdminController {
    @Autowired
    TechTestService testService;

    @Autowired
    TechTestResultService resultService;

    @Autowired
    TechTestSessionService sessionService;

    @PostMapping
    public TechTest addNew(@RequestBody TechTest techTest) {
        return testService.save(techTest);
    }

    @PutMapping
    public ResponseEntity<?> update(@RequestBody TechTest techTest) {
        var test = testService.find(techTest.getId());
        if(test.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Sorry, no TechTest with that id to update");
        }
        return new ResponseEntity(testService.save(techTest), HttpStatus.OK);
    }
}
