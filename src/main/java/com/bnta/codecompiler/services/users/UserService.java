package com.bnta.codecompiler.services.users;

import com.bnta.codecompiler.models.problems.Solution;
import com.bnta.codecompiler.models.users.Role;
import com.bnta.codecompiler.models.users.User;
import com.bnta.codecompiler.repositories.users.IUserRepository;
import com.bnta.codecompiler.services.email.MailSenderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class UserService {
    @Autowired
    IUserRepository userRepository;
    @Autowired
    PasswordEncoder encoder;
    @Autowired
    MailSenderService mailService;

    public User add(User user) {
        // encrypt password before saving any user
        if(user.getPassword() == null) user.setPassword("temppassword_0184");
        updatePassword(user, user.getPassword());
        userRepository.save(user);
        try {
            requestPasswordReset(user);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return user;
    }

    public void delete(User user) {
        userRepository.delete(user);
    }

    public void delete(Long id) {
        userRepository.deleteById(id);
    }

    public User update(User user) {
        return userRepository.save(user);
    }

    public User findById(Long id) throws Exception {
        Optional<User> optional = userRepository.findById(id);
        if(optional.isEmpty()) throw new Exception("No User with id: " + id);
        return optional.get();
    }

    public Set<User> findAll() { return new HashSet<>(userRepository.findAll());}

    public Optional<Set<User>> findAllFromCohort(String cohort) {
        return userRepository.findByCohort(cohort);
    }
    public Optional<Set<User>> globalLeaderboard() {
        return userRepository.findByOrderByScoreDesc();
    }

    public Optional<Set<User>> cohortLeaderboardByName(String cohortName) {
        return userRepository.findByCohort_NameOrderByScoreDesc(cohortName.toUpperCase());
    }

    public Optional<Set<User>> cohortLeaderboardById(Long cohortId) {
        return userRepository.findByCohort_IdOrderByScoreDesc(cohortId);
    }

    public User addSolution(User user, Solution solution) {
        user.getSolutions().add(solution);
        increaseScore(user, 50 + (50 * solution.getProblem().getDifficulty().ordinal()));
        return userRepository.save(user);
    }

    public User increaseScore(User user, long amount) {
        user.setScore(user.getScore() + amount);
        return userRepository.save(user);
    }

    public void requestPasswordReset(Long userId) throws Exception {
        requestPasswordReset(findById(userId));
    }

    public void requestPasswordReset(User user) throws Exception {
        var secret = encoder.encode(user.getUsername());
        String formLink = "http://";
        mailService.sendEmail(user.getEmail(), "Password reset link", "Use this link to reset your password: " +
                formLink + "?secret=" + secret);
    }

    public User updatePassword(User user, String newPassword) {
        user.setPassword(encoder.encode(newPassword));
        return user;
    }

}
