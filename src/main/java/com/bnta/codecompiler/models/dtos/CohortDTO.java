package com.bnta.codecompiler.models.dtos;

import java.time.LocalDate;

public class CohortDTO {

    private Long id;
    private String name;
    private LocalDate startDate;
    private Long numberOfMembers;

    public CohortDTO(Long id, String name, LocalDate startDate, Long numberOfMembers) {
        this.id = id;
        this.name = name;
        this.startDate = startDate;
        this.numberOfMembers = numberOfMembers;
    }

    // getters and setters

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public Long getNumberOfMembers() {
        return numberOfMembers;
    }

    public void setNumberOfMembers(Long numberOfMembers) {
        this.numberOfMembers = numberOfMembers;
    }
}
