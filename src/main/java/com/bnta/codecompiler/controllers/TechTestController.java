package com.bnta.codecompiler.controllers;

import com.bnta.codecompiler.models.assessment.TechTest;
import com.bnta.codecompiler.services.assessment.TechTestResultService;
import com.bnta.codecompiler.services.assessment.TechTestService;
import com.bnta.codecompiler.services.assessment.TechTestSessionService;
import org.hibernate.hql.internal.ast.tree.ResolvableNode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users/tests")
public class TechTestController {
    @Autowired
    private TechTestService testService;
    @Autowired
    private TechTestResultService resultService;
    @Autowired
    private TechTestSessionService sessionService;

    @GetMapping
    public List<TechTest> getAll() {
        return testService.find();
    }

}
