package com.bnta.codecompiler.services.users;

import com.bnta.codecompiler.models.problems.Solution;
import com.bnta.codecompiler.models.users.Role;
import com.bnta.codecompiler.models.users.User;
import com.bnta.codecompiler.repositories.users.IUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
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

    public User add(User user) {
        // encrypt password before saving any user
        user.setPassword(encoder.encode(user.getPassword()));
        return userRepository.save(user);
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

    public Optional<Set<User>> cohortLeaderboard(String cohortName) {
        return userRepository.findByCohort_NameOrderByScoreDesc(cohortName.toUpperCase());
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

}
