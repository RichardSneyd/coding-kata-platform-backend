package com.bnta.codecompiler.models.tests;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
public class TestCase {
    @Id
    private Long id;
    @Column
    private String input;
    @Column
    private String output;

    public TestCase(String input, String output) {
        this.input = input;
        this.output = output;
    }

    public TestCase() {
    }

    public String getInput() {
        return input;
    }

    public void setInput(String input) {
        this.input = input;
    }

    public String getOutput() {
        return output;
    }

    public void setOutput(String output) {
        this.output = output;
    }
}
