package com.sam.task_management.Security;

import com.sam.task_management.Respository.UserRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

/**
 * CustomUserDetailsService class that implements UserDetailsService.
 * This class is responsible for loading user details from the database.
 * It implements the UserDetailsService interface and provides a method to load a user by username (email).
 *
 * @author LS Khiba
 * @version 1.0
 */
@Service
public class CustomUserDetailsService implements UserDetailsService {

    /**
     * The user repository.
     * This repository is used to access user data in the database.
     */
    private final UserRepository userRepository;

    /**
     * Constructor that injects the user repository.
     * @param userRepository the user repository
     */
    public CustomUserDetailsService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    /**
     * Loads a user by username (email).
     * This method is called by the authentication framework to load a user's details.
     * It uses the user repository to find a user by email and returns a UserDetails object.
     * If no user is found, it throws a UsernameNotFoundException.
     *
     * @param username the username (email)
     * @return the user details
     * @throws UsernameNotFoundException if the user is not found
     */
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepository.findByEmail(username).orElseThrow(
                () -> new UsernameNotFoundException("User not found with email: " + username)
        );
    }
}