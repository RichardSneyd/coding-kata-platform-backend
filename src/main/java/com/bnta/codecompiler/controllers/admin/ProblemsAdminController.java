package com.bnta.codecompiler.controllers.admin;

import com.bnta.codecompiler.models.problems.Problem;
import com.bnta.codecompiler.models.problems.ProblemSet;
import com.bnta.codecompiler.services.problems.ProblemService;
import com.bnta.codecompiler.services.problems.ProblemSetService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/admin/problems")
public class ProblemsAdminController {
    //todo: add all problem/problemSet routes for the ADMIN role
    @Autowired
    private ProblemService problemService;

    @Autowired
    private ProblemSetService problemSetService;

    @GetMapping
    public String helloWorld() {
        return "Hello, you've been authorized as an ADMIN!";
    }

    @PostMapping("/")
    public Problem newProblem(@RequestBody Problem problem) {
        return problemService.add(problem);
    }

    @PostMapping("/sets/")
    public ProblemSet newProblemSet(@RequestBody ProblemSet problemSet) {
        return problemSetService.add(problemSet);
    }
}
