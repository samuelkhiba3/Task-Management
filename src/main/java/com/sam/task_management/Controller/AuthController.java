package com.sam.task_management.Controller;

import com.sam.task_management.Dto.AuthResponse;
import com.sam.task_management.Dto.CreateUpdateUserDto;
import com.sam.task_management.Dto.LoginRequest;
import com.sam.task_management.Dto.UserDto;
import com.sam.task_management.Security.CustomUserDetailsService;
import com.sam.task_management.Security.JwtTokenUtil;
import com.sam.task_management.Service.BlacklistedTokenService;
import com.sam.task_management.Service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

/**
 * AuthController class that handles authentication and authorization requests.
 *
 * @author LS Khiba
 * @version 1.0
 */
@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final AuthenticationManager authenticationManager;
    private final JwtTokenUtil jwtTokenUtil;
    private final CustomUserDetailsService customUserDetailsService;
    private final UserService userService;
    private final BlacklistedTokenService blacklistedTokenService;

    /**
     * Constructor that initializes the dependencies.
     *
     * @param authenticationManager   the authentication manager
     * @param jwtTokenUtil            the JWT token utility
     * @param customUserDetailsService the custom user details service
     * @param userService             the user service
     * @param blacklistedTokenService the Blacklisted Token Service
     */
    public AuthController(AuthenticationManager authenticationManager, JwtTokenUtil jwtTokenUtil, CustomUserDetailsService customUserDetailsService, UserService userService, BlacklistedTokenService blacklistedTokenService) {
        this.authenticationManager = authenticationManager;
        this.jwtTokenUtil = jwtTokenUtil;
        this.customUserDetailsService = customUserDetailsService;
        this.userService = userService;
        this.blacklistedTokenService = blacklistedTokenService;
    }

    /**
     * Handles login requests and returns a JWT token.
     *
     * @param loginRequest the login request
     * @return the JWT token response
     * @throws Exception if an error occurs
     */
    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(@RequestBody LoginRequest loginRequest) throws Exception {
        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(loginRequest.getEmail(), loginRequest.getPassword())
            );
        } catch (Exception e) {
            throw new Exception("Invalid credentials");
        }
        final UserDetails userDetails = customUserDetailsService.loadUserByUsername(loginRequest.getEmail());

        AuthResponse jwtResponse = new AuthResponse(jwtTokenUtil.generateToken(userDetails));
        return new ResponseEntity<>(jwtResponse, HttpStatus.OK);
    }

    /**
     * Handles signup requests and creates a new user.
     *
     * @param createUpdateUserDto the user data transfer object
     * @return the created user
     */
    @PostMapping("/signup")
    public ResponseEntity<UserDto> signup(@RequestBody CreateUpdateUserDto createUpdateUserDto) {
        return new ResponseEntity<>(userService.createUser(createUpdateUserDto), HttpStatus.CREATED);
    }

    @PostMapping("/logout")
    public ResponseEntity<String> logout(@RequestHeader("Authorization") String tokenHear){
        if(tokenHear!=null && tokenHear.startsWith("Bearer ")){
            String token = tokenHear.substring(7);
            blacklistedTokenService.blacklistToken(token);
        }
        return new ResponseEntity<>("Logged out successfully.",HttpStatus.OK);
    }
}