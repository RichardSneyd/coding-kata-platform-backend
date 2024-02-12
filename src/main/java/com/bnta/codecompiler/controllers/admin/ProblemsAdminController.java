package com.bnta.codecompiler.controllers.admin;

import com.bnta.codecompiler.models.problems.Problem;
import com.bnta.codecompiler.models.problems.ProblemSet;
import com.bnta.codecompiler.services.problems.ProblemService;
import com.bnta.codecompiler.services.problems.ProblemSetService;
import com.bnta.codecompiler.services.problems.SolutionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/admin/problems")
public class ProblemsAdminController {
    //todo: add all problem/problemSet routes for the ADMIN role
    @Autowired
    private ProblemService problemService;

    @Autowired
    SolutionService solutionService;

    @Autowired
    private ProblemSetService problemSetService;

    @CacheEvict(value = "problems", allEntries = true)
    @PostMapping
    public Problem newProblem(@RequestBody Problem problem) {
        return problemService.add(problem);
    }

    @CacheEvict(value = {"problems", "problem"}, key = "#problem.getId()")
    @PutMapping
    public ResponseEntity<?> update(@RequestBody Problem problem) {
        if (problemService.find(problem.getId()).isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(String.format("No problem found with id %s", problem.getId()));
        }
        try {
            var updated = this.problemService.update(problem);
            return ResponseEntity.ok(updated);
        }
        catch(Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    @CacheEvict(value = {"problems", "problem"}, key = "#id")
    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id) {
        var problem = problemService.find(id);
        if (problem.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(String.format("No problem with id %s", id));
        }

        problemService.delete(problem.get());
        return ResponseEntity.ok(String.format("Deleted problem with id %s", id));
    }

    @DeleteMapping("/solution/{id}")
    public ResponseEntity<?> deleteSolution(@PathVariable Long id) {
        var solution = solutionService.findyById(id);
        if (solution.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(String.format("No solution with id %s", id));
        }

        solutionService.remove(solution.get());
        return ResponseEntity.ok(String.format("Deleted solution with id %s", id));
    }

    @CacheEvict(value = "problemSets", allEntries = true)
    @PostMapping("/sets")
    public ResponseEntity<?> newProblemSet(@RequestBody ProblemSet problemSet) {
        problemSet = problemSetService.add(problemSet);

        return ResponseEntity.ok().body(problemSet);
    }

    @CacheEvict(value = {"problemSets", "problemSet"}, key = "#set.getId()")
    @PutMapping("/sets")
    public ResponseEntity<?> updateSet(@RequestBody ProblemSet set) {
        if (problemSetService.findById(set.getId()).isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(String.format("No problem set found with id %s", set.getId()));
        }

        this.problemSetService.update(set);
        return ResponseEntity.ok(String.format("Updated problem set %s", set.getTitle()));
    }

    @CacheEvict(value = {"problemSets", "problemSet"}, key = "#id")
    @DeleteMapping("/sets/{id}")
    public ResponseEntity<?> deleteSet(@PathVariable Long id) {
        var set = problemSetService.findById(id);
        if (set.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(String.format("No problemSet with id %s", id));
        }

        problemSetService.remove(set.get());
        return ResponseEntity.ok(String.format("Deleted problemSet with id %s", id));
    }
}
