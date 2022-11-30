package com.bnta.codecompiler.repositories.quizes;

import com.bnta.codecompiler.models.quizes.Quiz;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

public interface IQuizRepository extends JpaRepository<Quiz, Long> {
    //todo add derived queries as needed
}
