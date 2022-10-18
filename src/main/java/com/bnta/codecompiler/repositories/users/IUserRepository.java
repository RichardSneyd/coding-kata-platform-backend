package com.bnta.codecompiler.repositories.users;

import com.bnta.codecompiler.models.users.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.Set;

public interface IUserRepository extends JpaRepository<User, Long> {
    //todo: add derived queries as needed
    Optional<Set<User>> findByCohort(String cohort);
}
