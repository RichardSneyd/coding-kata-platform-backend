package com.bnta.codecompiler.services;

import com.auth0.jwt.algorithms.Algorithm;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@SpringBootTest
public class UserServiceTest {
    @Autowired
    BCryptPasswordEncoder encoder;

    @Test
    public void passwordEncodeIsConsistent() {

        var a = encoder.encode("TestUsername");
        var b = encoder.encode("TestUsername");
       // encoder.matches("")
        assertThat(encoder.matches("TestUsername", a)).isTrue();
        assertThat(encoder.matches("TestUsername", b)).isTrue();
    }
}
