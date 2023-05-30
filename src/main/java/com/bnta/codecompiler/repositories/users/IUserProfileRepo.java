package com.bnta.codecompiler.repositories.users;

import com.bnta.codecompiler.models.users.UserProfile;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IUserProfileRepo extends JpaRepository<UserProfile, Long> {
}
