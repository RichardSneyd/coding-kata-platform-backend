package com.bnta.codecompiler.repositories.problems;

import com.bnta.codecompiler.models.problems.StartCode;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

public interface IStartCodeRepository extends JpaRepository<StartCode, Long> {

}
