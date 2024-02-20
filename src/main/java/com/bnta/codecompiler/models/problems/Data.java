package com.bnta.codecompiler.models.problems;

import com.bnta.codecompiler.models.assessment.TechTestSession;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name="data")
public class Data {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long id;
    @Column(length = 1000)
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Data coerced = (Data) o;
        return Objects.equals(id, coerced.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return "Data{" +
                "id=" + id +
                ", value='" + value + '\'' +
                ", dataType=" + dataType +
                '}';
    }
}
