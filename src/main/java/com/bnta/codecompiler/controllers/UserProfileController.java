package com.bnta.codecompiler.controllers;


import com.bnta.codecompiler.models.problems.Solution;
import com.bnta.codecompiler.models.users.User;
import com.bnta.codecompiler.models.users.UserProfile;
import com.bnta.codecompiler.services.users.UserProfileService;
import com.bnta.codecompiler.services.users.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
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

    @Cacheable(value = "profiles", key = "#page + '-' + #size")
    @GetMapping
    public ResponseEntity<?> getAllProfiles(@RequestParam(defaultValue = "0") int page,
                                            @RequestParam(defaultValue = "10") int size) {
        Page<UserProfile> profiles = userProfileService.findAll(PageRequest.of(page, size));
        return ResponseEntity.ok().body(profiles);
    }

    @Cacheable(value = "profiles", key = "#id")
    @GetMapping("/{id}")
    public ResponseEntity<UserProfile> getProfileById(@PathVariable Long id) {
        return userProfileService.findById(id)
                .map(profile -> new ResponseEntity<>(profile, HttpStatus.OK))
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @CacheEvict(value = "profiles", allEntries = true)
    @PostMapping(value = "/{userId}")
    public ResponseEntity<?> createProfile(@RequestBody UserProfile userProfile, @PathVariable Long userId) {
        User user = null;
        try {
            user = userService.findById(userId);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        try {
            authScreen(user);
        }
        catch(ResponseStatusException e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Error: " + e.getMessage());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        userProfile.setUser(user);
        return new ResponseEntity<>(userProfileService.save(userProfile), HttpStatus.CREATED);
    }

    @CacheEvict(value = "profiles", key = "#id")
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

    @CacheEvict(value = "profiles", key = "#id")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProfile(@PathVariable Long id) {
        if (userProfileService.delete(id)) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @CacheEvict(value = "headshots", key = "#id")
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

    @Cacheable(value = "headshots", key = "#id")
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

    @CacheEvict(value = "resumes", key = "#id")
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

    @Cacheable(value = "resumes", key = "#id")
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
        var id = userService.findByUname(uname).getId();
        boolean isAdmin = auth.getAuthorities().stream()
                .anyMatch(authority -> authority.getAuthority().equals("ADMIN"));
        if (id != user.getId() && !isAdmin) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, uname + "cannot modify profile of " + user.getUsername());
        }
    }

}
