package com.bnta.codecompiler.repositories.users;

import com.bnta.codecompiler.models.users.UserProfile;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface IUserProfileRepo extends JpaRepository<UserProfile, Long> {

    @Query("SELECT up FROM UserProfile up WHERE up.fullName IS NOT NULL AND up.bio IS NOT NULL AND NOT up.bio = '' AND up.education IS NOT EMPTY AND up.preferredRoles IS NOT EMPTY AND up.preferredLocations IS NOT EMPTY")
    Page<UserProfile> findProfilesWithRequiredFields(Pageable pageable);
}
