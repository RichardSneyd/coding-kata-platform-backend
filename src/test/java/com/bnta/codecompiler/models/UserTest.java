package com.bnta.codecompiler.models;

import com.bnta.codecompiler.models.users.Role;
import com.bnta.codecompiler.models.users.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class UserTest {
    private User admin;
    private User user;

    @BeforeEach
    public  void setUp() {
        admin = new User("RichardSneyd", "1234", null, Role.ADMIN);
        user = new User("student", "4567", "C7", Role.USER);

    }
    @Test
    public void getRoleAsString(){
        assertThat(admin.getRole().toString()).isEqualTo("ADMIN");
        assertThat(user.getRole().toString()).isEqualTo("USER");
    }
}
