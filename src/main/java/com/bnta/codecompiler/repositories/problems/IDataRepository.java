package com.bnta.codecompiler.repositories.problems;

import com.bnta.codecompiler.models.problems.Data;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IDataRepository extends JpaRepository<Data, Long> {
}
