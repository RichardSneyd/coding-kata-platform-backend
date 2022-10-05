package com.bnta.codecompiler.controllers;

import com.bnta.codecompiler.models.CodeMessage;
import com.bnta.codecompiler.models.CodeReply;
import com.bnta.codecompiler.services.CompilerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@RestController
@RequestMapping("/compile")
public class CompilerController {

    @Autowired
    private CompilerService compilerService;

    @PostMapping
    public ResponseEntity<CodeReply> compileAndReturnResult(@RequestBody CodeMessage codeMessage) throws IOException {
        System.out.println("message: " + codeMessage.toString());
        String response = compilerService.compile(codeMessage.getCode(),
                codeMessage.getLang());
        return new ResponseEntity<>(new CodeReply(response, codeMessage.getLang(), true), HttpStatus.OK);
    }

    @PostMapping("/test/js")
    public ResponseEntity<CodeReply> testJS() throws IOException {
        String response = compilerService.compile("console.log(\"hello from js test\")",
                "js");
            return new ResponseEntity<>(new CodeReply(response, "js", true), HttpStatus.OK);
    }

    @PostMapping("/test/java")
    public ResponseEntity<CodeReply> testJava() throws IOException {
        String response = compilerService.compile("public class Main { " +
                        "public static void main(String[] args) {" +
                        "System.out.println(\"Hello from java test\");" +
                        "} " +
                        "}",
                "java");
        return new ResponseEntity<>(new CodeReply(response, "java", true), HttpStatus.CREATED);
    }
}
