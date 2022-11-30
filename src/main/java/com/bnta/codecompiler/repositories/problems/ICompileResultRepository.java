package com.bnta.codecompiler.repositories.problems;

import com.bnta.codecompiler.models.dtos.CompileResult;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

public interface ICompileResultRepository extends JpaRepository<CompileResult, Long> {
}
