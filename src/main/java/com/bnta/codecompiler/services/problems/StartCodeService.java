package com.bnta.codecompiler.services.problems;

import com.bnta.codecompiler.models.problems.StartCode;
import com.bnta.codecompiler.repositories.problems.IStartCodeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class StartCodeService {
    @Autowired
    private IStartCodeRepository startCodeRepo;

    public StartCode add(StartCode startCode) {
        return startCodeRepo.save(startCode);
    }

    public StartCode update(StartCode startCode) throws Exception {
        var optional = startCodeRepo.findById(startCode.getId());
        if(optional.isEmpty()) throw new Exception("No StartCode with id " + startCode.getId());
        var existing = optional.get();
        existing.setJava(startCode.getJava());
        existing.setJs(startCode.getJs());
        existing.setPy(startCode.getPy());
       return startCodeRepo.save(existing);
    }

    public StartCode findById(Long id) throws Exception {
        var optional = startCodeRepo.findById(id);
        if(optional.isEmpty()) throw new Exception("No StartCode with id: " + id);
        return optional.get();
    }

    public void remove(StartCode startCode) {
        startCodeRepo.delete(startCode);
    }

    public void remove(Long id) throws Exception {
        this.remove(this.findById(id));
    }
}
