package com.bnta.codecompiler.repositories.problems;

import com.bnta.codecompiler.models.dtos.CompileResult;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ICompileResultRepository extends JpaRepository<CompileResult, Long> {
}
