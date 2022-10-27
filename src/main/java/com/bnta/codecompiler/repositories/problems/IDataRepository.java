package com.bnta.codecompiler.repositories.problems;

import com.bnta.codecompiler.models.problems.Data;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IDataRepository extends JpaRepository<Data, Long> {
}
