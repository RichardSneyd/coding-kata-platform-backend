package com.bnta.codecompiler.services.users;

import com.bnta.codecompiler.models.User;
import com.bnta.codecompiler.repositories.users.IUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
public class UserService {
    @Autowired
    IUserRepository userRepository;

    public User add(User user) {
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

}
