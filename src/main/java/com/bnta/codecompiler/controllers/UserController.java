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


    @GetMapping("/password/forgot/{userId}")
    public ResponseEntity<?> forgotPassword(@PathVariable Long userId) {
        try {
            var user = userService.findById(userId);
            var secret = encoder.encode(user.getUsername());
            String formLink = "http://";
            mailService.sendEmail(user.getEmail(), "Password reset link", "Use this link to reset your password: " +
                    formLink + "?secret=" + secret);
            return ResponseEntity.ok("Thank you. We've sent an email with reset instructions.");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    @PostMapping("/password/reset")
    public ResponseEntity<?> resetPassword(@RequestBody PasswordResetInput pr) {
        try {
            var user = userService.findById(pr.getUserId());
            if(!encoder.matches(user.getUsername(), pr.getSecret())) {
                System.out.println("got: " + pr.getSecret());
                System.out.println("required: " + encoder.encode(user.getUsername()));
                throw new Exception("Wrong secret provided");
            }
            user.setPassword(encoder.encode(pr.getNewPassword()));
            return ResponseEntity.ok().body("Password successfully updated");

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    @GetMapping("/leaderboard")
    public ResponseEntity<?> globalLeaderboard() {
        return ResponseEntity.ok().body(userService.globalLeaderboard().get());
    }

    @GetMapping("/leaderboard/cohort-name/{cohort}")
    public ResponseEntity<?> cohortLeaderboardByName(@PathVariable String cohort) {
        return ResponseEntity.ok().body(userService.cohortLeaderboardByName(cohort));
    }

    @GetMapping("/leaderboard/{cohortId}")
    public ResponseEntity<?> cohortLeaderboardById(@PathVariable Long cohortId) {
        return ResponseEntity.ok().body(userService.cohortLeaderboardById(cohortId));
    }
}
