package com.bnta.codecompiler.repositories.users;

import com.bnta.codecompiler.models.users.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.Set;

public interface IUserRepository extends JpaRepository<User, Long> {
    //todo: add derived queries as needed
    Optional<Set<User>> findByCohort(String cohort);

    Optional<Set<User>> findByOrderByScoreDesc();

    Optional<Set<User>> findByCohort_NameOrderByScoreDesc(String cohort);
    Optional<Set<User>> findByCohort_IdOrderByScoreDesc(Long cohortId);


    User findByUsername(String username);
}
