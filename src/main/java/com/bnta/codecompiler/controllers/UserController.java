package com.bnta.codecompiler.controllers;

import com.bnta.codecompiler.models.users.PasswordResetInput;
import com.bnta.codecompiler.services.email.MailSenderService;
import com.bnta.codecompiler.services.users.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/user/users")
public class UserController {
    @Autowired
    private UserService userService;
    @Autowired
    private MailSenderService mailService;
    @Autowired
    private BCryptPasswordEncoder encoder;

    @GetMapping("/leaderboard/")
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
}
