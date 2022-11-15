package com.bnta.codecompiler.controllers.admin;

import com.bnta.codecompiler.models.problems.Difficulty;
import com.bnta.codecompiler.models.problems.Problem;
import com.bnta.codecompiler.models.problems.ProblemSet;
import com.bnta.codecompiler.services.problems.ProblemService;
import com.bnta.codecompiler.services.problems.ProblemSetService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@RestController
@RequestMapping("/admin/problems")
public class ProblemsAdminController {
    //todo: add all problem/problemSet routes for the ADMIN role
    @Autowired
    private ProblemService problemService;

    @Autowired
    private ProblemSetService problemSetService;



    @PostMapping("/")
    public Problem newProblem(@RequestBody Problem problem) {
        return problemService.add(problem);
    }

    @PostMapping("/sets")
    public ResponseEntity<?> newProblemSet(@RequestBody ProblemSet problemSet) {
        problemSet = problemSetService.add(problemSet);

        return ResponseEntity.ok().body(problemSet);
    }
}
