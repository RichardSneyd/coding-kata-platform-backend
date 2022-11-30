package com.bnta.codecompiler.services.problems;

import com.bnta.codecompiler.models.dtos.CompileResult;
import com.bnta.codecompiler.repositories.problems.ICompileResultRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CompileResultService {
    @Autowired
    private ICompileResultRepository compileResultRepo;

    public CompileResult add(CompileResult compileResult) {
        return compileResultRepo.save(compileResult);
    }
}
