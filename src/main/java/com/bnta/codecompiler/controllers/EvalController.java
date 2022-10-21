package com.bnta.codecompiler.controllers;

import com.bnta.codecompiler.models.dtos.CompileInputPojo;
import com.bnta.codecompiler.models.problems.Solution;
import com.bnta.codecompiler.services.code.EvalService;
import com.bnta.codecompiler.services.problems.ProblemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user/eval")
public class EvalController {
    @Autowired
    EvalService evalService;
    @Autowired
    ProblemService problemService;

    @GetMapping()
    public String about(){
        return "Evaluation API. Use a POST request with lang and code properties.";
    }

    @PostMapping("/{problemId}")
    public ResponseEntity<Solution> evaluate(@RequestBody CompileInputPojo compileInputPojo, @PathVariable Long problemId) {
        try {

        evalService.evaluate(compileInputPojo, problemService.findById(problemId));
            //todo: return successful response
        }
        catch (Exception e) {
            e.printStackTrace();
            //todo: return not found error code
        }

        return null;
    }
}
