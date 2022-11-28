package com.bnta.codecompiler.controllers;

import com.bnta.codecompiler.models.dtos.EvalInput;
import com.bnta.codecompiler.models.dtos.EvalResult;
import com.bnta.codecompiler.models.problems.Solution;
import com.bnta.codecompiler.services.code.EvalService;
import com.bnta.codecompiler.services.problems.ProblemService;
import com.bnta.codecompiler.services.problems.SolutionService;
import com.bnta.codecompiler.services.users.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user/eval")
public class EvalController {
    @Autowired
    EvalService evalService;
    @Autowired
    ProblemService problemService;
    @Autowired
    SolutionService solutionService;
    @Autowired
    UserService userService;

    @GetMapping()
    public String about() {
        return "Evaluation API. Use a POST request with lang, code and userId properties in the request body.";
    }

    @PostMapping("/{problemId}")
    public ResponseEntity<?> evaluate(@RequestBody EvalInput evalInput,
                                               @PathVariable Long problemId) {
        try {
            var user = userService.findById(evalInput.getUserId());
            var problem = problemService.findById(problemId);
            var evalResult = evalService.evaluate(evalInput, problem);
            if (evalResult.isSuccessful()) {
                userService.addCompletedProblem(user, problem);
                solutionService.add(new Solution(evalInput.getCode(), evalInput.getLang(),
                        evalResult.isSuccessful(), evalResult.getProblem(), user));
            }
            return new ResponseEntity<>(evalResult, HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_ACCEPTABLE);
        }

    }
}
