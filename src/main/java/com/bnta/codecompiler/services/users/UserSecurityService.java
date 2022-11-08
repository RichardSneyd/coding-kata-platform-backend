package com.bnta.codecompiler.services.users;

import com.bnta.codecompiler.models.users.User;
import com.bnta.codecompiler.repositories.users.IUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
public class UserSecurityService implements UserDetailsService {

    @Autowired
    IUserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUsername(username);
        if(user == null) throw new UsernameNotFoundException("User " + username + " not found in DB");
        System.out.println("Roles: " + user.getRoles().get(0));
        List<SimpleGrantedAuthority> authorities  = user.getRoles().stream()
                .map(role -> {
                    System.out.println("Role in map: " + role);
                    return new SimpleGrantedAuthority(role.toString());})
                .collect(Collectors.toList());
        System.out.println("authorities: " + authorities);
        return new SpringSecurityUser(user.getUsername(), user.getId(), user.getPassword(),
               authorities);
    }
}
