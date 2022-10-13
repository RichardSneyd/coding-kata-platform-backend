package com.bnta.codecompiler.controllers;

import com.bnta.codecompiler.models.CodePojo;
import com.bnta.codecompiler.models.CodeResult;
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

    @GetMapping()
    public String about(){
        return "A remote compiler API. Use a POST request with lang and code properties.";
    }

    @PostMapping
    public ResponseEntity<CodeResult> compile(@RequestBody CodePojo codeMessage) throws IOException {
        System.out.println("message: " + codeMessage.toString());
        String response = compilerService.compile(codeMessage.getCode(),
                codeMessage.getLang());
        return new ResponseEntity<>(new CodeResult(response, codeMessage.getLang()), HttpStatus.OK);
    }

    @GetMapping("/test/js")
    public ResponseEntity<CodeResult> testJS() throws IOException {
        String response = compilerService.compile("console.log(\"hello from js test\")",
                "js");
            return new ResponseEntity<>(new CodeResult(response, "js"), HttpStatus.OK);
    }

    @GetMapping("/test/java")
    public ResponseEntity<CodeResult> testJava() throws IOException {
        String response = compilerService.compile("public class Main { " +
                        "public static void main(String[] args) {" +
                        "System.out.println(\"Hello from java test\");" +
                        "} " +
                        "}",
                "java");
        return new ResponseEntity<>(new CodeResult(response, "java"), HttpStatus.CREATED);
    }

    @GetMapping("/test/python")
    public ResponseEntity<CodeResult> testPython() throws IOException {
        String response = compilerService.compile("print('hello world');",
                "py");
        return new ResponseEntity<>(new CodeResult(response, "py"), HttpStatus.CREATED);
    }

    @GetMapping("/where/{command}")
    public String whereJS(@PathVariable String command) {
        return compilerService.where(command);
    }

    @GetMapping("/echo/{message}")
    public String echo(@PathVariable String message) {
        return compilerService.echo(message);
    }

}
