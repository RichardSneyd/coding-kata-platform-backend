package com.bnta.codecompiler.controllers.admin;

import com.bnta.codecompiler.models.problems.Solution;
import com.bnta.codecompiler.services.problems.SolutionService;
import org.apache.coyote.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("admin/solutions")
public class SolutionAdminController {
    @Autowired
    SolutionService solutionService;

    @GetMapping
    @Cacheable(value = "solutions")
    public ResponseEntity<?> getAll() {
        return ResponseEntity.ok().body(solutionService.findAll());
    }

    @GetMapping("/{id}")
    @Cacheable(value = "solution", key = "#id")
    public ResponseEntity<?> getById(@PathVariable Long id) {
        try {
            return ResponseEntity.ok().body(solutionService.findById(id));
        }
        catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No solution found with id " + id);
        }
    }

    @GetMapping("/user/{id}")
    @Cacheable(value="solutionsForUser", key = "#id")
    public ResponseEntity<?> getAllForUser(@PathVariable Long id) {
        return ResponseEntity.ok().body(solutionService.findAllByUser_id(id));
    }

    @GetMapping("/problem/{id}")
    @Cacheable(value = "solutionsForProblem", key = "#id")
    public ResponseEntity<?> getAllForProblem(@PathVariable Long id) {
        return ResponseEntity.ok().body(solutionService.findAllByProblem_id(id));
    }

    @DeleteMapping("{id}")
    @CacheEvict(value = "solution", key = "#id")
    public ResponseEntity deleteById(@PathVariable Long id) {
        solutionService.remove(id);
        return ResponseEntity.ok().body("successfully removed");
    }
}
