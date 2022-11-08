package com.bnta.codecompiler.controllers.admin;

import com.bnta.codecompiler.models.users.User;
import com.bnta.codecompiler.services.email.MailSenderService;
import com.bnta.codecompiler.services.users.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMessage;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.net.http.HttpResponse;
import java.util.List;
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

            return ResponseEntity.ok("Thank you. A link as been sent to the registered email address for reset");
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping("/password/reset/{userId}")
    public ResponseEntity<?> resetPassword(@PathVariable Long userId, @RequestBody ) {
        try {
            var user = userService.findById(userId);

        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }
}
