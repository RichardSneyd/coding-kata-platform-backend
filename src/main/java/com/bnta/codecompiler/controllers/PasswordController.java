package com.bnta.codecompiler.controllers;

import com.bnta.codecompiler.models.users.PasswordResetInput;
import com.bnta.codecompiler.services.users.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

@RestController
public class PasswordController {
    @Autowired
    UserService userService;

    @Autowired
    private BCryptPasswordEncoder encoder;

    @GetMapping("/password/forgot/{userEmail}")
    public ResponseEntity<?> forgotPassword(@PathVariable String userEmail) {
        try {
            userService.requestPasswordReset(userEmail);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
        return ResponseEntity.ok("Thank you. We've sent an email with reset instructions.");
    }

    @PostMapping("/password/reset")
    public ResponseEntity<?> resetPassword(@RequestBody PasswordResetInput pr) {
        try {
            var user = userService.findById(pr.getUserId());
            if (!encoder.matches(user.getUsername(), pr.getSecret())) { // match plain username with hashed
                throw new Exception("Wrong secret provided");
            }
            userService.updatePassword(user, pr.getNewPassword());
            return ResponseEntity.ok().body("Password successfully updated");

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }
}
