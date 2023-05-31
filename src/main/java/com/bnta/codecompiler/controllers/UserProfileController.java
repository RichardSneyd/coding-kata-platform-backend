package com.bnta.codecompiler.controllers;


import com.bnta.codecompiler.models.users.User;
import com.bnta.codecompiler.models.users.UserProfile;
import com.bnta.codecompiler.services.users.UserProfileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping("/profiles")
public class UserProfileController {

    private final UserProfileService userProfileService;

    @Autowired
    public UserProfileController(UserProfileService userProfileService) {
        this.userProfileService = userProfileService;
    }

    @GetMapping
    public ResponseEntity<List<UserProfile>> getAllProfiles() {
        return new ResponseEntity<>(userProfileService.findAll(), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserProfile> getProfileById(@PathVariable Long id) {
        return userProfileService.findById(id)
                .map(profile -> new ResponseEntity<>(profile, HttpStatus.OK))
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @PostMapping
    public ResponseEntity<?> createProfile(@RequestBody UserProfile userProfile) {
        try {
            authScreen(userProfile.getUser().getId());
        }
        catch(ResponseStatusException e) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Error: " + e.getMessage());
        }
        return new ResponseEntity<>(userProfileService.save(userProfile), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateProfile(@PathVariable Long id, @RequestBody UserProfile userProfile) {
        try {
            authScreen(userProfile.getUser().getId());
        }
        catch(ResponseStatusException e) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Error: " + e.getMessage());
        }
        return userProfileService.update(id, userProfile)
                .map(updatedProfile -> new ResponseEntity<>(updatedProfile, HttpStatus.OK))
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProfile(@PathVariable Long id) {
        if (userProfileService.delete(id)) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    // Method to upload headshot
    @PostMapping("/{id}/headshot")
    public ResponseEntity<?> uploadHeadshot(@PathVariable Long id, @RequestParam("file") MultipartFile file) {
        try {
            authScreen(id);
        }
        catch(ResponseStatusException e) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Error: " + e.getMessage());
        }
        try {
            userProfileService.saveHeadshot(id, file);
            return ResponseEntity.ok().body("Headshot uploaded successfully.");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Error uploading headshot: " + e.getMessage());
        }
    }

    @GetMapping("/{id}/headshot")
    public ResponseEntity<?> getHeadshot(@PathVariable Long id) {
        try {
            byte[] headshot = userProfileService.getHeadshot(id);
            if (headshot == null) return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No headshot found for this user.");

            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.IMAGE_JPEG); // Or whatever the type of your image files is

            return new ResponseEntity<>(headshot, headers, HttpStatus.OK);

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error retrieving headshot: " + e.getMessage());
        }
    }

    public void authScreen(Long id) throws ResponseStatusException {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User currentUser = (User) auth.getPrincipal();
        if (!currentUser.getId().equals(id)) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "You can only modify your own profile");
        }
    }

}
