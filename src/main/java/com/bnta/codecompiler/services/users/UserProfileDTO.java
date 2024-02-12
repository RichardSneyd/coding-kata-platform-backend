package com.bnta.codecompiler.services.users;

import java.util.Set;

public class UserProfileDTO {

    private Long id;
    private String cohort;
    private String headshot;
    private String resume;
    private String bio;
    private String fullName;
    private String githubLink;
    private Set<String> education;
    private Set<String> workExperience;
    private Set<String> preferredRoles;
    private Set<String> preferredLocations;
    private Boolean available;

    // Constructor with all fields
    public UserProfileDTO(Long id, String cohort, String headshot, String resume, String bio, String fullName, String githubLink, Set<String> education, Set<String> workExperience, Set<String> preferredRoles, Set<String> preferredLocations, Boolean available) {
        this.id = id;
        this.cohort = cohort;
        this.headshot = headshot;
        this.resume = resume;
        this.bio = bio;
        this.fullName = fullName;
        this.githubLink = githubLink;
        this.education = education;
        this.workExperience = workExperience;
        this.preferredRoles = preferredRoles;
        this.preferredLocations = preferredLocations;
        this.available = available;
    }

    // Getters and setters for all fields

    // ID
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    // Cohort
    public String getCohort() {
        return cohort;
    }

    public void setCohort(String cohort) {
        this.cohort = cohort;
    }

    // Headshot
    public String getHeadshot() {
        return headshot;
    }

    public void setHeadshot(String headshot) {
        this.headshot = headshot;
    }

    // Resume
    public String getResume() {
        return resume;
    }

    public void setResume(String resume) {
        this.resume = resume;
    }

    // Bio
    public String getBio() {
        return bio;
    }

    public void setBio(String bio) {
        this.bio = bio;
    }

    // Full Name
    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    // GitHub Link
    public String getGithubLink() {
        return githubLink;
    }

    public void setGithubLink(String githubLink) {
        this.githubLink = githubLink;
    }

    // Education
    public Set<String> getEducation() {
        return education;
    }

    public void setEducation(Set<String> education) {
        this.education = education;
    }

    // Work Experience
    public Set<String> getWorkExperience() {
        return workExperience;
    }

    public void setWorkExperience(Set<String> workExperience) {
        this.workExperience = workExperience;
    }

    // Preferred Roles
    public Set<String> getPreferredRoles() {
        return preferredRoles;
    }

    public void setPreferredRoles(Set<String> preferredRoles) {
        this.preferredRoles = preferredRoles;
    }

    // Preferred Locations
    public Set<String> getPreferredLocations() {
        return preferredLocations;
    }

    public void setPreferredLocations(Set<String> preferredLocations) {
        this.preferredLocations = preferredLocations;
    }

    // Availability
    public Boolean getAvailable() {
        return available;
    }

    public void setAvailable(Boolean available) {
        this.available = available;
    }
}
