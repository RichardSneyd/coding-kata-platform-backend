package com.bnta.codecompiler.controllers;

import com.bnta.codecompiler.models.code.CodePojo;
import com.bnta.codecompiler.models.code.CodeResultPojo;
import com.bnta.codecompiler.services.code.CompilerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@RestController
@RequestMapping("/user/compile")
public class CompilerController {

    @Autowired
    private CompilerService compilerService;

    @GetMapping()
    public String about(){
        return "A remote compiler API. Use a POST request with lang and code properties.";
    }

    @PostMapping
    public ResponseEntity<CodeResultPojo> compile(@RequestBody CodePojo codeMessage) throws IOException {
        System.out.println("message: " + codeMessage.toString());
        String response = compilerService.compile(codeMessage.getCode(),
                codeMessage.getLang());
        return new ResponseEntity<>(new CodeResultPojo(response, codeMessage.getLang()), HttpStatus.OK);
    }

    @GetMapping("/test/js")
    public ResponseEntity<CodeResultPojo> testJS() throws IOException {
        String response = compilerService.compile("console.log(\"hello from js test\")",
                "js");
            return new ResponseEntity<>(new CodeResultPojo(response, "js"), HttpStatus.OK);
    }

    @GetMapping("/test/java")
    public ResponseEntity<CodeResultPojo> testJava() throws IOException {
        String response = compilerService.compile("public class Main { " +
                        "public static void main(String[] args) {" +
                        "System.out.println(\"Hello from java test\");" +
                        "} " +
                        "}",
                "java");
        return new ResponseEntity<>(new CodeResultPojo(response, "java"), HttpStatus.CREATED);
    }

    @GetMapping("/test/python")
    public ResponseEntity<CodeResultPojo> testPython() throws IOException {
        String response = compilerService.compile("print('hello world');",
                "py");
        return new ResponseEntity<>(new CodeResultPojo(response, "py"), HttpStatus.CREATED);
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
