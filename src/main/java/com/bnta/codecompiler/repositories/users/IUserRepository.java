package com.bnta.codecompiler.repositories.users;

import com.bnta.codecompiler.models.users.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.Set;

public interface IUserRepository extends JpaRepository<User, Long> {
    //todo: add derived queries as needed
    Optional<List<User>> findByCohort(String cohort);

    Optional<List<User>> findByOrderByScoreDesc();

    Optional<List<User>> findByCohort_NameOrderByScoreDesc(String cohort);
    Optional<List<User>> findByCohort_IdOrderByScoreDesc(Long cohortId);


    User findByUsername(String username);

    User findByEmail(String email);
}
