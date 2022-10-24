package com.bnta.codecompiler.controllers.admin;

import com.bnta.codecompiler.models.problems.Difficulty;
import com.bnta.codecompiler.models.problems.Problem;
import com.bnta.codecompiler.models.problems.ProblemSet;
import com.bnta.codecompiler.services.problems.ProblemService;
import com.bnta.codecompiler.services.problems.ProblemSetService;
import org.springframework.beans.factory.annotation.Autowired;
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

    @GetMapping
    public Set<Problem> allProblems() {
        return problemService.findAll();
    }

    @GetMapping("/tag/{tag}")
    public Set<Problem> byTag(@PathVariable String tag) {
        return problemService.findByTag(tag).get();
    }

    @GetMapping("/tags")
    public Set<String> allTags() {
        Set<String> tags = new HashSet();
        problemService.findAll().stream().map(problem -> problem.getTags()).collect(Collectors.toSet()).forEach(tags::addAll);
        return tags;
    }

    @GetMapping("/difficulty/{difficulty}")
    public Set<Problem> byDifficulty(@PathVariable("difficulty") Difficulty difficulty) {
        return problemService.findByDifficulty(difficulty).get();
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
