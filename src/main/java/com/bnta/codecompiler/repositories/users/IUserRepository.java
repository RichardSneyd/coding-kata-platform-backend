package com.bnta.codecompiler.repositories.users;

import com.bnta.codecompiler.models.users.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.Set;

public interface IUserRepository extends JpaRepository<User, Long> {
    //todo: add derived queries as needed
    List<User> findByCohort(String cohort);

    List<User> findByOrderByScoreDesc();

    List<User> findByCohort_NameOrderByScoreDesc(String cohort);
    List<User> findByCohort_IdOrderByScoreDesc(Long cohortId);


    User findByUsername(String username);

    User findByEmail(String email);
}
