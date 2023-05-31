package com.bnta.codecompiler.services.users;

import com.bnta.codecompiler.models.users.User;
import com.bnta.codecompiler.models.users.UserProfile;
import com.bnta.codecompiler.repositories.users.IUserProfileRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.util.List;
import java.util.Optional;

@Service
public class UserProfileService {

    private final IUserProfileRepo userProfileRepo;


    private final Path rootLocation;

    @Autowired
    public UserProfileService(IUserProfileRepo userProfileRepository, @Value("${storage.images.headshots}") Path rootLocation) {
        this.userProfileRepo = userProfileRepository;
        this.rootLocation = rootLocation;
    }

    public List<UserProfile> findAll() {
        return userProfileRepo.findAll();
    }

    public Optional<UserProfile> findById(Long id) {
        return userProfileRepo.findById(id);
    }

    public UserProfile save(UserProfile userProfile) {
        return userProfileRepo.save(userProfile);
    }

    public Optional<UserProfile> update(Long id, UserProfile userProfileDetails) {
        Optional<UserProfile> userProfileOptional = userProfileRepo.findById(id);
        if (userProfileOptional.isPresent()) {
            UserProfile userProfile = userProfileOptional.get();
            if(userProfileDetails.getHeadshot() != null) userProfile.setHeadshot(userProfileDetails.getHeadshot());
            if(userProfileDetails.getBio() != null) userProfile.setBio(userProfileDetails.getBio());
            if(userProfileDetails.getEducation() != null) userProfile.setEducation(userProfileDetails.getEducation());
            if(userProfileDetails.getWorkExperience() != null) userProfile.setWorkExperience(userProfileDetails.getWorkExperience());
            return Optional.of(userProfileRepo.save(userProfile));
        } else {
            return Optional.empty();
        }
    }

    public boolean delete(Long id) {
        if (userProfileRepo.existsById(id)) {
            userProfileRepo.deleteById(id);
            return true;
        } else {
            return false;
        }
    }

    public UserProfile saveHeadshot(Long userId, MultipartFile file) throws IOException {
        UserProfile userProfile = getUserProfile(userId);
        String headshotFileName = storeFile(file);
        userProfile.setHeadshot(headshotFileName);
        return userProfileRepo.save(userProfile);
    }

    public byte[] getHeadshot(Long userId) throws IOException {
        UserProfile userProfile = getUserProfile(userId);
        return Files.readAllBytes(rootLocation.resolve(userProfile.getHeadshot()));
    }

    public UserProfile saveResume(Long userId, MultipartFile file) throws IOException {
        UserProfile userProfile = getUserProfile(userId);
        String cvFileName = storeFile(file);
        userProfile.setResume(cvFileName);
        return userProfileRepo.save(userProfile);
    }

    public byte[] getResume(Long userId) throws IOException {
        UserProfile userProfile = getUserProfile(userId);
        return Files.readAllBytes(rootLocation.resolve(userProfile.getResume()));
    }


    private UserProfile getUserProfile(Long userId) {
        return userProfileRepo.findById(userId)
                .orElseThrow(() -> new IllegalStateException("User Profile not found"));
    }

    private String storeFile(MultipartFile file) throws IOException {
        String originalFilename = StringUtils.cleanPath(file.getOriginalFilename());
        try (InputStream inputStream = file.getInputStream()) {
            Files.copy(inputStream, this.rootLocation.resolve(originalFilename), StandardCopyOption.REPLACE_EXISTING);
        }
        return originalFilename;
    }
}
