package com.bnta.codecompiler.services.users;

import com.bnta.codecompiler.config.GlobalSettings;
import com.bnta.codecompiler.models.problems.Solution;
import com.bnta.codecompiler.models.users.User;
import com.bnta.codecompiler.models.users.UserProgressDTO;
import com.bnta.codecompiler.repositories.users.IUserRepository;
import com.bnta.codecompiler.services.email.MailSenderService;
import com.bnta.codecompiler.services.problems.ProblemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.DependsOn;
import org.springframework.context.annotation.Lazy;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import javax.transaction.Transactional;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.*;

@Service
public class UserService {
    @Autowired
    IUserRepository userRepository;
    @Autowired
    PasswordEncoder encoder;
    @Autowired
    MailSenderService mailService;

    @Autowired
    ProblemService problemService;

    @Autowired
    GlobalSettings globalSettings;

    @Transactional
    public User add(User user) {
        if (user.getId() == null || userRepository.findById(user.getId()).isEmpty()) user = userRepository.save(user);
        // encrypt password before saving any user
        if (user.getPassword() == null) user.setPassword("temppassword_0184");
        user = updatePassword(user, user.getPassword());

        try {
            requestPasswordReset(user);
        } catch (Exception e) {
            e.printStackTrace();
        }
        user = userRepository.save(user);
        return user;
    }

    @Transactional
    public User add(User user, boolean bypassRegistration) {
        if (user.getPassword() == null) user.setPassword("temppassword_0184");
        if (!bypassRegistration) return add(user);
        user = userRepository.save(user);
        user = updatePassword(user, user.getPassword());
        return user;
    }

    public Optional<UserProgressDTO> getUserProgress(Optional<User> user) {
        if (user.isEmpty()) return Optional.of(new UserProgressDTO());
        var total = problemService.findAll().size();
        return Optional.of(new UserProgressDTO(user.get().getUsername(), user.get().getScore(),
                user.get().getCompletedProblems().size(), total));
    }

    public Optional<UserProgressDTO> getUserProgress(Long id) {
        return getUserProgress(userRepository.findById(id));
    }


    public void delete(User user) {
        userRepository.delete(user);
    }

    public void delete(Long id) {
        userRepository.deleteById(id);
    }

    public User update(User user) {
        var optional = userRepository.findById(user.getId());
        if (optional.isEmpty()) throw new EntityNotFoundException("No user found with id " + user.getId());
        var existing = optional.get();
        if (user.getEmail() != null) existing.setEmail(user.getEmail());
        if (user.getUsername() != null) existing.setUsername(user.getUsername());
        if (user.getRoles() != null) existing.setRoles(user.getRoles());
        if (user.getCohort() != null) existing.setCohort(user.getCohort());
//        if (user.getJoinDate() != null) existing.setJoinDate(user.getJoinDate());
        return userRepository.save(existing);
        //   }
    }

    public User findById(Long id) throws Exception {
        Optional<User> optional = userRepository.findById(id);
        if (optional.isEmpty()) throw new Exception("No User with id: " + id);
        return optional.get();
    }

    public Page<User> findAll(Pageable pageable) {
        return userRepository.findAll(pageable);
    }

    public List<User> findAllFromCohort(String cohort) {
        return userRepository.findByCohort(cohort);
    }

    public Page<User> globalLeaderboard(Pageable pageable) {
        return userRepository.findByOrderByScoreDesc(pageable);
    }

    public List<User> cohortLeaderboardByName(String cohortName) {
        return userRepository.findByCohort_NameOrderByScoreDesc(cohortName.toUpperCase());
    }

    public List<User> cohortLeaderboardById(Long cohortId) {
        return userRepository.findByCohort_IdOrderByScoreDesc(cohortId);
    }

    public User addSolution(User user, Solution solution) {
        if (scorable(solution, user)) {
            long score = 50 + (50 * solution.getProblem().getDifficulty().ordinal() * (solution.getCorrectness() / 100));
            user = increaseScore(user, score);
            user = addCompletedProblem(user.getId(), solution);
            return userRepository.save(user);
        } else {
            System.out.println("matches existing solution, can't add");
        }
        return user;
    }

    public User addCompletedProblem(Long userId, Solution solution) {
        try {
            User user = findById(userId);
            for (var problem : user.getCompletedProblems()) {
                if (problem.getId().equals(solution.getProblem().getId())) {
                    return user;
                }
            }
            user.getCompletedProblems().add(solution.getProblem());
            user = update(user);
            return user;
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        return null;
    }

    public boolean scorable(Solution solution, User user) {
        for (var savedSolution : user.getSolutions()) {
            if (savedSolution.getProblem().getId().equals(solution.getProblem().getId())
                    && savedSolution.getLang().equals(solution.getLang())) return false;
        }
        return true;
    }

    public User increaseScore(User user, long amount) {
        user.setScore(user.getScore() + amount);
        // return user;
        return userRepository.save(user);
    }

    public void requestPasswordReset(String userEmail) throws Exception {
        requestPasswordReset(findByEmail(userEmail));
    }

    public User findByEmail(String email) {
        return this.userRepository.findByEmail(email);
    }

    public User findByUname(String uname) {
        return this.userRepository.findByUsername(uname);
    }

    public void requestPasswordReset(User user) throws Exception {
        var secret = URLEncoder.encode(encoder.encode(user.getUsername()), StandardCharsets.UTF_8);
        String formLink = globalSettings.getFrontEndOrigin() + "/reset-password/" + user.getId();
        mailService.sendEmail(user.getEmail(), "Password reset link", "Welcome to BrightCode!\n\nYou're username is " + user.getUsername() + ".\n\nUse this link to reset your password: " +
                formLink + "/" + secret);
    }

    @Transactional
    public User updatePassword(User user, String newPassword) {
        var existing = userRepository.findById(user.getId());
        if (existing.isEmpty()) throw new EntityNotFoundException("No user with id: " + user.getId());
        existing.get().setPassword(encoder.encode(newPassword));
        return userRepository.save(existing.get());
    }

}
