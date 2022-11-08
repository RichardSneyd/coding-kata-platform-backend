package com.bnta.codecompiler.repositories.users;

import com.bnta.codecompiler.models.users.Cohort;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ICohortRepository extends JpaRepository<Cohort, Long> {

}
