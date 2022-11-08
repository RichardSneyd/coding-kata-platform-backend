package com.bnta.codecompiler.controllers.admin;

import com.bnta.codecompiler.models.users.PasswordResetInput;
import com.bnta.codecompiler.models.users.User;
import com.bnta.codecompiler.services.email.MailSenderService;
import com.bnta.codecompiler.services.users.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.Set;

@RestController
@RequestMapping("/admin/users")
public class UserAdminController {
    @Autowired
    private UserService userService;
    @Autowired
    private MailSenderService mailService;
    @Autowired
    private PasswordEncoder encoder;

    @GetMapping
    public Set<User> allUsers() {
        return userService.findAll();
    }

    // allow password reset
    //
    @PostMapping
    public ResponseEntity<User> add(@RequestBody User user) {
        try {
            return new ResponseEntity<User>(userService.add(user), HttpStatus.CREATED);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(null, HttpStatus.NOT_ACCEPTABLE);
        }
    }

    @GetMapping("/password/forgot/{userId}")
    public ResponseEntity<?> forgotPassword(@PathVariable Long userId) {
        try {
            var user = userService.findById(userId);
            var secret = encoder.encode(user.getUsername());
            String formLink = "http://...";
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
            if(!encoder.encode(user.getUsername()).equals(pr.getSecret())) {
                throw new Exception("Wrong secret provided");
            }
            user.setPassword(encoder.encode(pr.getNewPassword()));
            return ResponseEntity.ok().body("Password successfully updated");

        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }
}
