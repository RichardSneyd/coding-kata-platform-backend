package com.bnta.codecompiler.controllers;

import com.bnta.codecompiler.models.dtos.CompileInput;
import com.bnta.codecompiler.models.dtos.CompileResult;
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
    public String about() {
        return "A remote compiler API. Use a POST request with lang and code properties.";
    }

    @PostMapping
    public ResponseEntity<CompileResult> compile(@RequestBody CompileInput compileInputPojo) throws IOException {
        System.out.println("message: " + compileInputPojo.toString());
        CompileResult result = compilerService.compile(compileInputPojo.getCode(),
                compileInputPojo.getLang());
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @GetMapping("/test/js")
    public ResponseEntity<CompileResult> testJS() throws IOException {
        CompileResult result = compilerService.compile("console.log(\"hello from js test\")",
                "js");
        result.setLang("js");
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @GetMapping("/test/java")
    public ResponseEntity<CompileResult> testJava() throws IOException {
        CompileResult result = compilerService.compile("public class Main { " +
                        "public static void main(String[] args) {" +
                        "System.out.println(\"Hello from java test\");" +
                        "} " +
                        "}",
                "java");
        result.setLang("java");
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @GetMapping("/test/python")
    public ResponseEntity<CompileResult> testPython() throws IOException {
        CompileResult result = compilerService.compile("print('hello world');",
                "py");
        result.setLang("py");
        return new ResponseEntity<>(result, HttpStatus.CREATED);
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
