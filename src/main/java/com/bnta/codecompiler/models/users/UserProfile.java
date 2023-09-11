package com.bnta.codecompiler.models.users;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import java.util.List;
import java.util.Objects;
import java.util.Set;

@Entity
@Table(name="user_profiles")
public class UserProfile {

    @Id
    private Long id;

    @OneToOne(cascade = {}, fetch = FetchType.EAGER)
    @MapsId
    @JoinColumn(name = "id")
    @JsonIgnoreProperties({"profile", "solutions", "completedProblems"})
    private User user;

    @Column(nullable = true)
    private String headshot;

    @Column(nullable = true)
    private String resume;

    @Column(nullable = false, length = 500)
    private String bio;

    @Column(nullable = false)
    private String fullName;

    @Column(nullable = true)
    private String githubLink;

    @Column(nullable = true)
    @ElementCollection(fetch = FetchType.EAGER)
    private Set<String> education;

    @Column(nullable = true)
    @ElementCollection(fetch = FetchType.EAGER)
    private Set<String> workExperience;

    @Column(nullable = true)
    @ElementCollection(fetch = FetchType.EAGER)
    private Set<String> preferredRoles;

    @Column(nullable = true)
    @ElementCollection(fetch = FetchType.EAGER)
    private Set<String> preferredLocations;

    @Column
    private Boolean available = true;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
//        if (user.getProfile() != this) {
//            user.setProfile(this);
//        }
    }

    public String getHeadshot() {
        return headshot;
    }

    public void setHeadshot(String headshot) {
        this.headshot = headshot;
    }

    public String getBio() {
        return bio;
    }

    public void setBio(String bio) {
        this.bio = bio;
    }

    public Set<String> getEducation() {
        return education;
    }

    public void setEducation(Set<String> education) {
        this.education = education;
    }

    public Set<String> getWorkExperience() {
        return workExperience;
    }

    public void setWorkExperience(Set<String> workExperience) {
        this.workExperience = workExperience;
    }

    public String getResume() {
        return resume;
    }

    public void setResume(String resume) {
        this.resume = resume;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getGithubLink() {
        return githubLink;
    }

    public void setGithubLink(String githubLink) {
        this.githubLink = githubLink;
    }

    public Set<String> getPreferredRoles() {
        return preferredRoles;
    }

    public void setPreferredRoles(Set<String> preferredRoles) {
        this.preferredRoles = preferredRoles;
    }

    public Set<String> getPreferredLocations() {
        return preferredLocations;
    }

    public void setPreferredLocations(Set<String> preferredLocations) {
        this.preferredLocations = preferredLocations;
    }

    public Boolean getAvailable() {
        return available;
    }

    public void setAvailable(Boolean available) {
        this.available = available;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return "UserProfile{" +
                "id=" + id +
                '}';
    }
}
