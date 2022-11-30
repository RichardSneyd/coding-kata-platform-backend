package com.bnta.codecompiler.services.quizes;

import com.bnta.codecompiler.models.quizes.Question;
import com.bnta.codecompiler.models.quizes.Quiz;
import com.bnta.codecompiler.repositories.quizes.IQuizRepository;
import org.springframework.stereotype.Service;

@Service
public class QuizService {
    private IQuizRepository quizRepo;

    public Quiz addQuestion(Quiz quiz, Question question) {
        quiz.getQuestions().add(question);
        return quizRepo.save(quiz);
    }
}
