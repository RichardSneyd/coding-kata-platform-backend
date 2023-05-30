package com.bnta.codecompiler.services.users;

import com.bnta.codecompiler.models.users.User;
import com.bnta.codecompiler.models.users.UserProfile;
import com.bnta.codecompiler.repositories.users.IUserProfileRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;

@Service
public class UserProfileService {

    private final IUserProfileRepo userProfileRepo;
    private final Path rootLocation;

    @Autowired
    public UserProfileService(IUserProfileRepo userProfileRepository, Path rootLocation) {
        this.userProfileRepo = userProfileRepository;
        this.rootLocation = rootLocation;
    }

    public UserProfile saveHeadshot(User user, MultipartFile file) throws IOException {
        UserProfile userProfile = getUserProfile(user);
        String headshotFileName = storeFile(file);
        userProfile.setHeadshot(headshotFileName);
        return userProfileRepo.save(userProfile);
    }

    public byte[] getHeadshot(User user) throws IOException {
        UserProfile userProfile = getUserProfile(user);
        return Files.readAllBytes(rootLocation.resolve(userProfile.getHeadshot()));
    }

    private UserProfile getUserProfile(User user) {
        return userProfileRepo.findById(user.getId())
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
