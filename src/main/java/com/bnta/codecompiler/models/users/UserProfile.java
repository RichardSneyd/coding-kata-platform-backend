package com.bnta.codecompiler.models.users;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name="user_profiles")
public class UserProfile {

    @Id
    private Long id;

    @OneToOne(cascade = {})
    @MapsId
    @JoinColumn(name = "id")
    @JsonIgnoreProperties({"profile"})
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
    @ElementCollection
    private List<String> education;

    @Column(nullable = true)
    @ElementCollection
    private List<String> workExperience;

    @Column(nullable = true)
    @ElementCollection
    private List<String> preferredRoles;

    @Column(nullable = true)
    @ElementCollection
    private List<String> preferredLocations;

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

    public List<String> getEducation() {
        return education;
    }

    public void setEducation(List<String> education) {
        this.education = education;
    }

    public List<String> getWorkExperience() {
        return workExperience;
    }

    public void setWorkExperience(List<String> workExperience) {
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

    public List<String> getPreferredRoles() {
        return preferredRoles;
    }

    public void setPreferredRoles(List<String> preferredRoles) {
        this.preferredRoles = preferredRoles;
    }

    public List<String> getPreferredLocations() {
        return preferredLocations;
    }

    public void setPreferredLocations(List<String> preferredLocations) {
        this.preferredLocations = preferredLocations;
    }
}
