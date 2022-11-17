package com.bnta.codecompiler.models;

import com.bnta.codecompiler.models.users.Cohort;
import com.bnta.codecompiler.models.users.Role;
import com.bnta.codecompiler.models.users.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class UserTest {
    private User admin;
    private User user;

    @BeforeEach
    public  void setUp() {
        admin = new User("RichardSneyd", "richard@fake.com",  "1234", null, List.of(Role.ADMIN));
        user = new User("student", "student@fake.com", "4567", new Cohort("C7"), List.of(Role.USER));

    }
    @Test
    public void getRoleAsString(){
        assertThat(admin.getRoles().get(0).toString()).isEqualTo("ADMIN");
        assertThat(user.getRoles().get(0).toString()).isEqualTo("USER");
    }
}
