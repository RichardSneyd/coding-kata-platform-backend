package com.bnta.codecompiler.models.tests;

import com.bnta.codecompiler.models.problems.Data;

import javax.persistence.*;
import java.util.List;

@Entity(name="test_cases")
public class TestCase {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long id;
    @OneToMany
    private List<Data> inputs;
    @OneToOne
    private Data output;

    public TestCase(List<Data> inputs, Data output) {
        this.inputs = inputs;
        this.output = output;
    }

    public TestCase() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public List<Data> getInputs() {
        return inputs;
    }

    public void setInputs(List<Data> inputs) {
        this.inputs = inputs;
    }

    public Data getOutput() {
        return output;
    }

    public void setOutput(Data output) {
        this.output = output;
    }
}
