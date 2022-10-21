package com.bnta.codecompiler.controllers;

import com.bnta.codecompiler.models.problems.Problem;
import com.bnta.codecompiler.models.problems.ProblemSet;
import com.bnta.codecompiler.services.problems.ProblemService;
import com.bnta.codecompiler.services.problems.ProblemSetService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;
import java.util.Set;

@RestController
@RequestMapping("/users/problems")
public class ProblemsUserController {
    @Autowired
    ProblemService problemService;

    @Autowired
    ProblemSetService problemSetService;

    @GetMapping
    public ResponseEntity<Set<Problem>> getAllProblems(){
        Set<Problem> problems = problemService.findAll();
        return new ResponseEntity<>(problems, HttpStatus.FOUND);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Problem> getProblemById(@PathVariable Long id) {
        try {
           Problem problem = problemService.findById(id);
           return new ResponseEntity<>(problem, HttpStatus.FOUND);
        }
        catch (Exception e) {
          //  e.printStackTrace();
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/{tag}")
    public ResponseEntity<Set<Problem>> getProblemsByTag(@PathVariable String tag) {
        Optional<Set<Problem>> optional = problemService.findByTag(tag);
        if(optional.isPresent() && optional.get().size() > 0) {
            return new ResponseEntity<>(optional.get(), HttpStatus.FOUND);
        }
        else return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
    }

    @GetMapping("/sets/")
    public ResponseEntity<Set<ProblemSet>> getAllProblemSets(){
        Set<ProblemSet> problemSets = problemSetService.findAll();
        return new ResponseEntity<>(problemSets, HttpStatus.FOUND);
    }

    @GetMapping("/sets/{id}")
    public ResponseEntity<ProblemSet> getProblemSetById(@PathVariable Long id) {
        try {
            ProblemSet problemSet = problemSetService.findById(id);
            return new ResponseEntity<>(problemSet, HttpStatus.FOUND);
        }
        catch (Exception e) {
            //  e.printStackTrace();
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }
    }


}
