package com.bnta.codecompiler.controllers;

import com.bnta.codecompiler.models.users.PasswordResetInput;
import com.bnta.codecompiler.models.users.User;
import com.bnta.codecompiler.services.email.MailSenderService;
import com.bnta.codecompiler.services.users.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

@Controller
@RequestMapping("/user/users")
public class UserController {
    @Autowired
    private UserService userService;
    @Autowired
    private MailSenderService mailService;
    @Autowired
    private BCryptPasswordEncoder encoder;

    @PutMapping
    public ResponseEntity<?> update(@RequestBody User user) {
        authScreen(user);
        try {
            User updatedUser = userService.update(user); // exception will be thrown if no such user
            return new ResponseEntity<User>(updatedUser, HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }
    @GetMapping("/leaderboard")
    public ResponseEntity<?> globalLeaderboard() {
        return ResponseEntity.ok().body(userService.globalLeaderboard());
    }
    @GetMapping("/leaderboard/cohort-name/{cohort}")
    public ResponseEntity<?> cohortLeaderboardByName(@PathVariable String cohort) {
        return ResponseEntity.ok().body(userService.cohortLeaderboardByName(cohort));
    }

    @GetMapping("/leaderboard/{cohortId}")
    public ResponseEntity<?> cohortLeaderboardById(@PathVariable Long cohortId) {
        return ResponseEntity.ok().body(userService.cohortLeaderboardById(cohortId));
    }

    @GetMapping("/{userId}")
    public ResponseEntity<?> getUserById(@PathVariable Long userId) {
        try {
            return ResponseEntity.ok().body(userService.findById(userId));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No user with id " + userId);
        }
    }

    @GetMapping("/{id}/progress")
    public ResponseEntity<?> getUserProgress(@PathVariable Long id) {
        var progress = userService.getUserProgress(id);
        if(progress.isEmpty()) return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Sorry, no user found with that id");
        return ResponseEntity.ok(progress.get());
    }

    public void authScreen(User user) throws ResponseStatusException {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String uname = (String) auth.getPrincipal();
        var id = userService.findByUname(uname).getId();
        boolean isAdmin = auth.getAuthorities().stream()
                .anyMatch(authority -> authority.getAuthority().equals("ADMIN"));
        if (id != user.getId() && !isAdmin) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, uname + "cannot modify profile of " + user.getUsername());
        }
    }

}
