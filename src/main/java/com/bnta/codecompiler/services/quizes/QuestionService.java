package com.bnta.codecompiler.services.quizes;

import com.bnta.codecompiler.models.quizes.Question;
import com.bnta.codecompiler.repositories.quizes.IQuestionRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class QuestionService {
    private IQuestionRepository questionRepo;

    public List<Question> find() {
        return questionRepo.findAll();
    }

    public Optional<Question> find(Long id) {
        return questionRepo.findById(id);
    }
}
