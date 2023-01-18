package com.bnta.codecompiler.controllers.admin;

import com.bnta.codecompiler.models.problems.Solution;
import com.bnta.codecompiler.services.problems.SolutionService;
import org.apache.coyote.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("admin/solutions")
public class SolutionAdminController {
    @Autowired
    SolutionService solutionService;

    @GetMapping
    public ResponseEntity<?> getAll() {
        return ResponseEntity.ok().body(solutionService.findAll());
    }

    @GetMapping("/user/{id}")
    public ResponseEntity<?> getAllForUser(@PathVariable Long id) {
        return ResponseEntity.ok().body(solutionService.findAllByUser_id(id));
    }

    @GetMapping("/problem/{id}")
    public ResponseEntity<?> getAllForProblem(@PathVariable Long id) {
        return ResponseEntity.ok().body(solutionService.findAllByProblem_id(id));
    }

    @DeleteMapping("{id}")
    public ResponseEntity deleteById(@PathVariable Long id) {
        solutionService.remove(id);
        return ResponseEntity.ok().body("successfully removed");
    }
}
