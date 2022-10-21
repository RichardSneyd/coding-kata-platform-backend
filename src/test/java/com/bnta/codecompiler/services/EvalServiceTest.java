package com.bnta.codecompiler.services;

import com.bnta.codecompiler.services.code.EvalService;
import com.bnta.codecompiler.services.problems.ProblemService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@SpringBootTest
public class EvalServiceTest {
    @Autowired
    private EvalService evalService;

    @BeforeEach
    public void setUp() {

    }

    @Test
    public void evaluatesAccurately() {
        assertThat(evalService).isNotNull();
    }

}
