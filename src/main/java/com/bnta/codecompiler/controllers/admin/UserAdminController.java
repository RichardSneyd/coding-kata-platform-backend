package com.bnta.codecompiler.controllers.admin;

import com.bnta.codecompiler.models.users.User;
import com.bnta.codecompiler.services.users.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
}
