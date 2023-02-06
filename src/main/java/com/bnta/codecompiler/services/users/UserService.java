package com.bnta.codecompiler.services.users;

import com.bnta.codecompiler.config.GlobalSettings;
import com.bnta.codecompiler.models.problems.Problem;
import com.bnta.codecompiler.models.problems.Solution;
import com.bnta.codecompiler.models.users.User;
import com.bnta.codecompiler.models.users.UserProgressDTO;
import com.bnta.codecompiler.repositories.users.IUserRepository;
import com.bnta.codecompiler.services.email.MailSenderService;
import com.bnta.codecompiler.services.problems.ProblemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

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

    public User add(User user) {
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

    public User add(User user, boolean bypassRegistration) {
        if (!bypassRegistration) user = add(user);
        if (user.getPassword() == null) user.setPassword("temppassword_0184");
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
       // if (user.getId() != null && user.equals(findById(user.getId()))) {
            return userRepository.save(user);
     //   }
    }

    public User findById(Long id) throws Exception {
        Optional<User> optional = userRepository.findById(id);
        if (optional.isEmpty()) throw new Exception("No User with id: " + id);
        return optional.get();
    }

    public Set<User> findAll() {
        return new HashSet<>(userRepository.findAll());
    }

    public List<User> findAllFromCohort(String cohort) {
        return userRepository.findByCohort(cohort);
    }

    public List<User> globalLeaderboard() {
        return userRepository.findByOrderByScoreDesc();
    }

    public List<User> cohortLeaderboardByName(String cohortName) {
        return userRepository.findByCohort_NameOrderByScoreDesc(cohortName.toUpperCase());
    }

    public List<User> cohortLeaderboardById(Long cohortId) {
        return userRepository.findByCohort_IdOrderByScoreDesc(cohortId);
    }

    public User addSolution(User user, Solution solution) {
        if (scorable(solution, user)) {
            user = increaseScore(user, 50 + (50 * solution.getProblem().getDifficulty().ordinal()));
            user = addCompletedProblem(user, solution);
            return userRepository.save(user);
        } else {
            System.out.println("matches existing solution, can't add");
        }
        return user;
    }

    public User addCompletedProblem(User user, Solution solution) {
        try {
            user = findById(user.getId());
        }catch(Exception e) {}

        for (var problem : user.getCompletedProblems()) {
            if (problem.getId().equals(solution.getProblem().getId())) {
                return user;
            }
        }
        user.getCompletedProblems().add(solution.getProblem());
        user = update(user);
        return user;
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

    public void requestPasswordReset(User user) throws Exception {
        var secret = encoder.encode(user.getUsername());
        String formLink = GlobalSettings.getFrontEndOrigin() + "/user/reset-password";
        mailService.sendEmail(user.getEmail(), "Password reset link", "Use this link to reset your password: " +
                formLink + "?secret=" + secret);
    }

    public User updatePassword(User user, String newPassword) {
        user.setPassword(encoder.encode(newPassword));
        update(user);
        return user;
    }

}
