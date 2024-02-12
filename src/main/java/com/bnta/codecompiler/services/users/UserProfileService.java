package com.bnta.codecompiler.services.users;

import com.bnta.codecompiler.models.users.User;
import com.bnta.codecompiler.models.users.UserProfile;
import com.bnta.codecompiler.repositories.users.IUserProfileRepo;
import com.bnta.codecompiler.repositories.users.IUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.EntityNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class UserProfileService {

    private final IUserProfileRepo userProfileRepo;
    final IUserRepository userRepo;

    private final Path headshotDIR;
    private final Path resumeDIR;

    @Autowired
    public UserProfileService(IUserProfileRepo userProfileRepository, IUserRepository userRepo, @Value("${storage.images.headshots}") Path headshotDIR,
                              @Value("${storage.pdfs.resumes}") Path resumeDIR) {
        this.userProfileRepo = userProfileRepository;
        this.userRepo = userRepo;
        this.headshotDIR = headshotDIR;
        this.resumeDIR = resumeDIR;
    }

    public Page<UserProfileDTO> findAllUserProfileDTOs(Pageable pageable) {
        // Fetching UserProfile entities
        Page<UserProfile> userProfiles = userProfileRepo.findAll(pageable);

        // Transforming UserProfile entities to UserProfileDTOs
        Page<UserProfileDTO> userProfileDTOs = userProfiles.map(this::convertToUserProfileDTO);

        return userProfileDTOs;
    }

    private UserProfileDTO convertToUserProfileDTO(UserProfile userProfile) {
        // Assuming UserProfile has getUser() and the User entity has getCohort() and Cohort has getName()
        String cohortName = userProfile.getUser().getCohort().getName();

        // Creating a new UserProfileDTO and setting values from UserProfile and the cohort name
        UserProfileDTO userProfileDTO = new UserProfileDTO(
                userProfile.getId(),
                cohortName,
                userProfile.getHeadshot(),
                userProfile.getResume(),
                userProfile.getBio(),
                userProfile.getFullName(),
                userProfile.getGithubLink(),
                userProfile.getEducation(),
                userProfile.getWorkExperience(),
                userProfile.getPreferredRoles(),
                userProfile.getPreferredLocations(),
                userProfile.getAvailable()
        );

        return userProfileDTO;
    }

    public Page<UserProfile> findAll(Pageable pageable) {
        return userProfileRepo.findAll(pageable);
    }

    public Optional<UserProfile> findById(Long id) {
        return userProfileRepo.findById(id);
    }

    public UserProfile save(UserProfile userProfile) {
        if (userProfile.getUser().getId() == null) throw new IllegalArgumentException("User Id is null");
        Optional<User> userOptional = userRepo.findById(userProfile.getUser().getId());
        User user;
        if (userOptional.isEmpty()) {
            throw new EntityNotFoundException("No User found with that id");
        }

        user = userOptional.get();
        userProfile.setUser(user);
        // in unidirectional with @MapsId, leave id null - derived from associated object
        userProfile.setId(null);

        return userProfileRepo.save(userProfile);
    }

    public Optional<UserProfile> update(Long id, UserProfile userProfileDetails) {
        Optional<UserProfile> userProfileOptional = userProfileRepo.findById(id);
        if (userProfileOptional.isEmpty()) return Optional.empty();

        UserProfile userProfile = userProfileOptional.get();
        if (userProfileDetails.getHeadshot() != null) userProfile.setHeadshot(userProfileDetails.getHeadshot());
        if (userProfileDetails.getEducation() != null) userProfile.setEducation(userProfileDetails.getEducation());
        if (userProfileDetails.getWorkExperience() != null)
            userProfile.setWorkExperience(userProfileDetails.getWorkExperience());
        if (userProfileDetails.getGithubLink() != null) userProfile.setGithubLink(userProfileDetails.getGithubLink());
        if (userProfileDetails.getBio() != null) userProfile.setBio(userProfileDetails.getBio());
        if (userProfileDetails.getFullName() != null) userProfile.setFullName(userProfileDetails.getFullName());
        if (userProfileDetails.getPreferredRoles() != null) userProfile.setPreferredRoles(userProfileDetails.getPreferredRoles());
        if (userProfileDetails.getPreferredLocations() != null) userProfile.setPreferredLocations(userProfileDetails.getPreferredLocations());
        if (userProfileDetails.getAvailable() != null) userProfile.setAvailable(userProfileDetails.getAvailable());


        return Optional.of(userProfileRepo.save(userProfile));

    }

    public boolean delete(Long id) {
        if (userProfileRepo.existsById(id)) {
            userProfileRepo.deleteById(id);
            return true;
        } else {
            return false;
        }
    }

    public String saveHeadshot(Long userId, MultipartFile file) throws IOException {
        return storeFile(file, "" + userId + ".jpg", headshotDIR);

    }

    public byte[] getHeadshot(Long userId) throws IOException {
        return Files.readAllBytes(headshotDIR.resolve("" + userId + ".jpg"));
    }

    public String saveResume(Long userId, MultipartFile file) throws IOException {
        return storeFile(file, "" + userId + ".pdf", resumeDIR);
    }

    public byte[] getResume(Long userId) throws IOException {
        return Files.readAllBytes(resumeDIR.resolve("" + userId + ".pdf"));
    }


    private UserProfile getUserProfile(Long userId) {
        return userProfileRepo.findById(userId)
                .orElseThrow(() -> new IllegalStateException("User Profile not found"));
    }

    private String storeFile(MultipartFile file, String filename, Path dir) throws IOException {
        try (InputStream inputStream = file.getInputStream()) {
            if(!Files.exists(dir)) {
                Files.createDirectories(dir);
            }
            Files.copy(inputStream, dir.resolve(filename), StandardCopyOption.REPLACE_EXISTING);
        }
        return filename;
    }
}
