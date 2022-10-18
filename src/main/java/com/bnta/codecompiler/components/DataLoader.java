package com.bnta.codecompiler.components;

import com.bnta.codecompiler.models.users.Role;
import com.bnta.codecompiler.models.users.User;
import com.bnta.codecompiler.services.users.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

@Component
public class DataLoader implements ApplicationRunner {

    @Autowired
    UserService userService;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        //todo: add initialization data

       User[] users = {
               new User("richard", "fakepassword", null, Role.ADMIN),
               new User("fakestudent", "phonypassword", "C7", Role.ADMIN)
       };

       for(User user : users) {
           userService.add(user);
           System.out.println("saved: " + user);
       }

    }
}
