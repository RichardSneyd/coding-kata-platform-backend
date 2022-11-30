package com.bnta.codecompiler.repositories.quizes;

import com.bnta.codecompiler.models.quizes.Question;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IQuestionRepository extends JpaRepository<Question, Long> {
    //todo: add derived queries as required
}
