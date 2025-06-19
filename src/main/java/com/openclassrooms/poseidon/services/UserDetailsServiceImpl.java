package com.openclassrooms.poseidon.services;

import com.openclassrooms.poseidon.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {

    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        com.openclassrooms.poseidon.domain.User user =  userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found with username = " + username));
        return User.builder()
                .username(user.getUsername())
                .password(user.getPassword())
                .build();
    }

}
