package com.bnta.codecompiler.controllers;

import com.bnta.codecompiler.services.assessment.TechTestResultService;
import com.bnta.codecompiler.services.assessment.TechTestService;
import com.bnta.codecompiler.services.assessment.TechTestSessionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TechTestController {
    @Autowired
    private TechTestService testService;

    @Autowired
    private TechTestResultService resultService;

    @Autowired
    private TechTestSessionService sessionService;
}
