package com.bnta.codecompiler.repositories.users;

import com.bnta.codecompiler.models.users.Cohort;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

public interface ICohortRepository extends JpaRepository<Cohort, Long> {
    public Optional<Cohort> findByName(String name);
    Page<Cohort> findAllByOrderByStartDateDesc(Pageable pageable);
}
