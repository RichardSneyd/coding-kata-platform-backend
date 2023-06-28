package com.bnta.codecompiler.controllers;


import com.bnta.codecompiler.models.users.User;
import com.bnta.codecompiler.models.users.UserProfile;
import com.bnta.codecompiler.services.users.UserProfileService;
import com.bnta.codecompiler.services.users.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/user/profiles")
public class UserProfileController {

    private final UserProfileService userProfileService;
    private final UserService userService;

    @Autowired
    public UserProfileController(UserProfileService userProfileService, UserService userService) {
        this.userProfileService = userProfileService;
        this.userService = userService;
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
            authScreen(userProfile.getUser());
        }
        catch(ResponseStatusException e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Error: " + e.getMessage());
        }
        return new ResponseEntity<>(userProfileService.save(userProfile), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateProfile(@PathVariable Long id, @RequestBody UserProfile userProfile) {
        try {
            authScreen(userProfile.getUser());
        }
        catch(ResponseStatusException e) {
            e.printStackTrace();
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
            authScreen(userService.findById(id));
        }
        catch(ResponseStatusException e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Error: " + e.getMessage());
        }
        catch(Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Error: " + e.getMessage());
        }
        try {
            userProfileService.saveHeadshot(id, file);
            return ResponseEntity.ok().body("Headshot uploaded successfully.");
        } catch (Exception e) {
            e.printStackTrace();
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
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error retrieving headshot: " + e.getMessage());
        }
    }

    @PostMapping("/{id}/resume")
    public ResponseEntity<?> uploadResume(@PathVariable Long id, @RequestParam("file") MultipartFile file) {
        try {
            authScreen(userService.findById(id));
            String filename = userProfileService.saveResume(id, file);
            return ResponseEntity.ok(filename);
        } catch (IOException e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error uploading CV: " + e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    @GetMapping("/{id}/resume")
    public ResponseEntity<byte[]> getResume(@PathVariable Long id) {
        try {
            byte[] resume = userProfileService.getResume(id);
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_PDF);
            String filename = "resume.pdf";
            headers.setContentDisposition(ContentDisposition.builder("inline").filename(filename).build());
            return new ResponseEntity<>(resume, headers, HttpStatus.OK);
        } catch (IOException e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }




    public void authScreen(User user) throws ResponseStatusException {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String uname = (String) auth.getPrincipal();
     //   var user = userService.findByUname(uname);
        boolean isAdmin = auth.getAuthorities().stream()
                .anyMatch(authority -> authority.getAuthority().equals("ADMIN"));
        if (!user.getUsername().equals(uname) && !isAdmin) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "You can only modify your own profile");
        }
    }

}
