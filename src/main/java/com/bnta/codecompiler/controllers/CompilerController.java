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
    public ResponseEntity<?> compile(@RequestBody CompileInput compileInputPojo)  {
        System.out.println("message: " + compileInputPojo.toString());
        try {
        CompileResult result = compilerService.compile(compileInputPojo);
        return new ResponseEntity<>(result, HttpStatus.OK);

        }
        catch(Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body(e.getMessage());
        }
    }

    @GetMapping("/test/js")
    public ResponseEntity<?> testJS() throws IOException {
        try {
        CompileResult result = compilerService.compile(new CompileInput("console.log(\"hello from js test\")",
                "js"));
        return new ResponseEntity<>(result, HttpStatus.OK);

        }
        catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body(e.getMessage());
        }
    }

    @GetMapping("/test/java")
    public ResponseEntity<?> testJava() throws IOException {
        try {

        CompileResult result = compilerService.compile(new CompileInput("public class Main { " +
                        "public static void main(String[] args) {" +
                        "System.out.println(\"Hello from java test\");" +
                        "} " +
                        "}",
                "java"));
        return new ResponseEntity<>(result, HttpStatus.OK);
        }
        catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body(e.getMessage());
        }
    }

    @GetMapping("/test/python")
    public ResponseEntity<?> testPython() throws IOException {
        try {
            CompileResult result = compilerService.compile(new CompileInput("print('hello world';",
                    "py"));
            return new ResponseEntity<>(result, HttpStatus.CREATED);
        }
        catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }

    }

//    @GetMapping("/where/{command}")
//    public CompileResult whereJS(@PathVariable String command) {
//        return compilerService.where(command);
//    }

    @GetMapping("/echo/{message}")
    public CompileResult echo(@PathVariable String message) throws IOException {
        return compilerService.echo(message);
    }

}
