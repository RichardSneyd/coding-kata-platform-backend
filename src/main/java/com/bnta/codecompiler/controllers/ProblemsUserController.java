package com.bnta.codecompiler.controllers;

import com.bnta.codecompiler.models.problems.Difficulty;
import com.bnta.codecompiler.models.problems.Problem;
import com.bnta.codecompiler.models.problems.ProblemSet;
import com.bnta.codecompiler.services.problems.ProblemService;
import com.bnta.codecompiler.services.problems.ProblemSetService;
import com.bnta.codecompiler.services.problems.SolutionService;
import com.bnta.codecompiler.services.users.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.cache.annotation.Cacheable;

@RestController
@RequestMapping("/user/problems")
public class ProblemsUserController {
    @Autowired
    ProblemService problemService;

    @Autowired
    ProblemSetService problemSetService;

    @Autowired
    SolutionService solutionService;

    @Autowired
    UserService userService;

    @GetMapping
    @Cacheable(value = "problems")
    public List<Problem> allProblems() {
        return problemService.findAll();
    }

    @GetMapping("/tag/{tag}")
    @Cacheable(value = "problemsByTag", key = "#tag")
    public ResponseEntity<?> byTag(@PathVariable String tag) {
        var problems = problemService.findByTag(tag);
        return new ResponseEntity<>(problems, HttpStatus.OK);
    }

    @GetMapping("/tags")
    @Cacheable(value = "allTags")
    public Set<String> allTags() {
        Set<String> tags = new HashSet();
        problemService.findAll().stream().map(problem -> problem.getTags()).collect(Collectors.toSet()).forEach(tags::addAll);
        return tags;
    }

    @GetMapping("/difficulty/{difficulty}")
    @Cacheable(value = "problemsByDifficulty", key = "#difficulty")
    public ResponseEntity<List<Problem>> byDifficulty(@PathVariable("difficulty") Difficulty difficulty) {
        var problems = problemService.findByDifficulty(difficulty);
        // if (problems.isEmpty()) return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        return new ResponseEntity<>(problems, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    @Cacheable(value = "problem", key = "#id")
    public ResponseEntity<?> getProblemById(@PathVariable Long id) {
        try {
            Problem problem = problemService.findById(id);
            return new ResponseEntity<>(problem, HttpStatus.OK);
        } catch (Exception e) {
            //  e.printStackTrace();
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/solutions/{id}")
    @Cacheable(value = "solution", key = "#id")
    public ResponseEntity<?> getSolutionById(@PathVariable Long id) {
        try {
            return ResponseEntity.ok(solutionService.getDTObyId(id));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No solution found with id " + id);
        }
    }

    @GetMapping("/next-for/{id}")
    public ResponseEntity<?> nextForUser(@PathVariable Long id) {
        try {
            return ResponseEntity.ok(problemService.nextForUser(userService.findById(id)));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No recommendation found");
        }
    }

    @GetMapping("/sets")
    @Cacheable(value = "allProblemSets")
    public ResponseEntity<List<ProblemSet>> getAllProblemSets() {
        List<ProblemSet> problemSets = problemSetService.findAll();
        return new ResponseEntity<>(problemSets, HttpStatus.OK);
    }

    @GetMapping("/sets/{id}")
    @Cacheable(value = "problemSet", key = "#id")
    public ResponseEntity<?> getProblemSetById(@PathVariable Long id) {
        var set = problemSetService.findById(id);
        if (set.isEmpty()) return new ResponseEntity<>("No problem set found with id " + id, HttpStatus.NOT_FOUND);
        return new ResponseEntity<>(set, HttpStatus.OK);
    }


}
