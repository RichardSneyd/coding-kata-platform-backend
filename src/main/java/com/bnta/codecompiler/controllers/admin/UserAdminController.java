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
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;

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

    @Cacheable(value = "allUsers", key = "'all'")
    @GetMapping
    public List<User> allUsers() {
        return userService.findAll();
    }

    @CacheEvict(value = "allUsers", allEntries = true)
    @PostMapping
    public ResponseEntity<?> add(@RequestBody User user) {
        try {
            return new ResponseEntity<User>(userService.add(user), HttpStatus.CREATED);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_ACCEPTABLE);
        }
    }

    @CacheEvict(value = {"allUsers", "users"}, key = "#id")
    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id) {
        userService.delete(id);
        return ResponseEntity.ok("Removed user");
    }

    @Cacheable(value = "leaderboards", key = "'adminGlobal'")
    @GetMapping("/leaderboard")
    public ResponseEntity<?> globalLeaderboard() {
        return ResponseEntity.ok().body(userService.globalLeaderboard());
    }


}
