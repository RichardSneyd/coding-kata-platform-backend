package com.bnta.codecompiler.controllers;

import com.bnta.codecompiler.models.CodeResultPojo;
import com.bnta.codecompiler.models.Solution;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@RestController
@RequestMapping("/user/eval")
public class EvalController {

    @GetMapping()
    public String about(){
        return "Evaluation API. Use a POST request with lang and code properties.";
    }

    @PostMapping
    public ResponseEntity<CodeResultPojo> evaluate(@RequestBody Solution solution) throws IOException {
//        System.out.println("message: " + codeMessage.toString());
//        String response = compilerService.compile(codeMessage.getCode(),
//                codeMessage.getLang());
//        return new ResponseEntity<>(new CodeResult(response, codeMessage.getLang()), HttpStatus.OK);
        return null;
    }
}
