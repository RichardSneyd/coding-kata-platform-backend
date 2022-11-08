package com.bnta.codecompiler.repositories.users;

import com.bnta.codecompiler.models.users.Cohort;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ICohortRepository extends JpaRepository<Cohort, Long> {
    public Optional<Cohort> findByName(String name);
}
