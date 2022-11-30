package com.bnta.codecompiler.controllers.admin;

import com.bnta.codecompiler.services.quizes.QuestionService;
import com.bnta.codecompiler.services.quizes.QuizService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class QuizAdminController {
    @Autowired
    private QuizService quizService;
    @Autowired
    private QuestionService questionService;
}
