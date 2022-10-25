package com.bnta.codecompiler.controllers.admin;

import com.bnta.codecompiler.models.users.User;
import com.bnta.codecompiler.services.users.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.http.HttpResponse;
import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("/admin/users")
public class UserAdminController {
    @Autowired
    private UserService userService;

    @GetMapping
    public Set<User> allUsers() {
        return userService.findAll();
    }

    @PostMapping
    public ResponseEntity<User> add(@RequestBody User user) {
        try {
            return new ResponseEntity<User>(userService.add(user), HttpStatus.CREATED);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(null, HttpStatus.NOT_ACCEPTABLE);
        }
    }
}
