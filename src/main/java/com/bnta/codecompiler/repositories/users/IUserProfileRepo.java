package com.bnta.codecompiler.repositories.users;

import com.bnta.codecompiler.models.users.UserProfile;
import com.bnta.codecompiler.models.users.UserProfileDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface IUserProfileRepo extends JpaRepository<UserProfile, Long> {
//    @Query("SELECT new com.bnta.codecompiler.models.users.UserProfileDTO(up.id, u.cohort.name, up.headshot, up.resume, up.bio, up.fullName, up.githubLink, up.education, up.workExperience, up.preferredRoles, up.preferredLocations, up.available) " +
//            "FROM UserProfile up " +
//            "JOIN up.user u")
//    Page<UserProfileDTO> findAllDTOs(Pageable pageable);
}
