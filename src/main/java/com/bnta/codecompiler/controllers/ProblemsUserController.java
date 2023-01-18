package com.bnta.codecompiler.controllers;

import com.bnta.codecompiler.models.problems.Difficulty;
import com.bnta.codecompiler.models.problems.Problem;
import com.bnta.codecompiler.models.problems.ProblemSet;
import com.bnta.codecompiler.services.problems.ProblemService;
import com.bnta.codecompiler.services.problems.ProblemSetService;
import com.bnta.codecompiler.services.problems.SolutionService;
import com.bnta.codecompiler.services.quizes.QuestionService;
import com.bnta.codecompiler.services.quizes.QuizService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/user/problems")
public class ProblemsUserController {
    @Autowired
    ProblemService problemService;

    @Autowired
    ProblemSetService problemSetService;

    @Autowired
    SolutionService solutionService;

    @GetMapping
    public Set<Problem> allProblems() {
        return problemService.findAll();
    }

    @GetMapping("/tag/{tag}")
    public ResponseEntity<?> byTag(@PathVariable String tag) {
        Optional<Set<Problem>> optional = problemService.findByTag(tag);
        if (optional.isPresent() && optional.get().size() > 0) {
            return new ResponseEntity<>(optional.get(), HttpStatus.OK);
        } else return new ResponseEntity<>(String.format("No problems found with tag: %s", tag), HttpStatus.NOT_FOUND);
    }

    @GetMapping("/tags")
    public Set<String> allTags() {
        Set<String> tags = new HashSet();
        problemService.findAll().stream().map(problem -> problem.getTags()).collect(Collectors.toSet()).forEach(tags::addAll);
        return tags;
    }

    @GetMapping("/difficulty/{difficulty}")
    public ResponseEntity<Set<Problem>> byDifficulty(@PathVariable("difficulty") Difficulty difficulty) {
        var optional = problemService.findByDifficulty(difficulty);
        if (optional.isEmpty()) return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        else return new ResponseEntity<>(optional.get(), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getProblemById(@PathVariable Long id) {
        try {
            Problem problem = problemService.findById(id);
            return new ResponseEntity<>(problem, HttpStatus.OK);
        } catch (Exception e) {
            //  e.printStackTrace();
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/{id}/solutions")
    public ResponseEntity<?> getSolutionsForProblemWithId(@PathVariable Long id) {
        var solutions = solutionService.findAllByProblem_id(id);
        if(!solutions.isEmpty()) {
            return new ResponseEntity<>(solutions, HttpStatus.OK);
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No solutions found for problem with id " + id);
    }

    @GetMapping("/sets")
    public ResponseEntity<Set<ProblemSet>> getAllProblemSets() {
        Set<ProblemSet> problemSets = problemSetService.findAll();
        return new ResponseEntity<>(problemSets, HttpStatus.OK);
    }

    @GetMapping("/sets/{id}")
    public ResponseEntity<?> getProblemSetById(@PathVariable Long id) {
            var set = problemSetService.findById(id);
            if(set.isEmpty()) return new ResponseEntity<>("No problem set found with id " + id, HttpStatus.NOT_FOUND);
            return new ResponseEntity<>(set, HttpStatus.OK);
    }

}
