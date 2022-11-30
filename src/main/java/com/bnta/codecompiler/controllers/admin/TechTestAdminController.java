package com.bnta.codecompiler.controllers.admin;

import com.bnta.codecompiler.services.assessment.TechTestResultService;
import com.bnta.codecompiler.services.assessment.TechTestService;
import com.bnta.codecompiler.services.assessment.TechTestSessionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TechTestAdminController {
    @Autowired
    TechTestService testService;

    @Autowired
    TechTestResultService resultService;

    @Autowired
    TechTestSessionService sessionService;
}
