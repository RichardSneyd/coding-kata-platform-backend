package com.bnta.codecompiler.models.problems;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.Entity;
import javax.persistence.Id;

@Entity(name="data")
public class Data {
    @Id
    private Long id;
    @Column
    private String value;

    @Column
    private DataType dataType;

    public Data(String value, DataType dataType) {
        this.value = value;
        this.dataType = dataType;
    }

    public Data() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public DataType getDataType() {
        return dataType;
    }

    public void setDataType(DataType dataType) {
        this.dataType = dataType;
    }
}
