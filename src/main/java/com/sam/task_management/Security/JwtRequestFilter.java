package com.sam.task_management.Security;

import com.sam.task_management.Service.BlacklistedTokenService;
import io.jsonwebtoken.ExpiredJwtException;
import jakarta.annotation.Nullable;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

/**
 * JwtRequestFilter class that extends OncePerRequestFilter.
 * This class is responsible for filtering incoming HTTP requests and validating JWT tokens.
 * It extracts the JWT token from the Authorization header, verifies its validity, and authenticates the user.
 *
 * @author LS Khiba
 * @version 1.0
 */
@Component
public class JwtRequestFilter extends OncePerRequestFilter {

    /**
     * The user details service.
     * This service is used to load user details from the database.
     */
    private final CustomUserDetailsService userDetailsService;

    /**
     * The Blacklist Token service.
     * This service is used to check if the token is blacklisted
     */
    private final BlacklistedTokenService blacklistedTokenService;

    /**
     * The JWT token utility.
     * This utility is used to validate and extract information from JWT tokens.
     */
    private final JwtTokenUtil jwtTokenUtil;

    /**
     * Constructor that injects the user details service and JWT token utility.
     *
     * @param userDetailsService the user details service
     * @param jwtTokenUtil       the JWT token utility
     * @param blacklistedTokenService the blacklisted Token Service
     */
    public JwtRequestFilter(CustomUserDetailsService userDetailsService, BlacklistedTokenService blacklistedTokenService, JwtTokenUtil jwtTokenUtil) {
        this.userDetailsService = userDetailsService;
        this.blacklistedTokenService = blacklistedTokenService;
        this.jwtTokenUtil = jwtTokenUtil;
    }

    /**
     * Performs the filtering operation.
     * This method is called for each incoming HTTP request. It extracts the JWT token from the Authorization header,
     * verifies its validity, checked against the blacklisted token and authenticates the user.
     *
     * @param request     the HTTP request
     * @param response    the HTTP response
     * @param filterChain the filter chain
     * @throws ServletException if a servlet error occurs
     * @throws IOException      if an I/O error occurs
     */
    @Override
    protected void doFilterInternal(HttpServletRequest request, @Nullable HttpServletResponse response, @Nullable FilterChain filterChain) throws ServletException, IOException {
        final String requestTokenHeader = request.getHeader("Authorization");

        String username =null;
        String jwtToken;

        if(requestTokenHeader!=null && requestTokenHeader.startsWith("Bearer ")){
            jwtToken = requestTokenHeader.substring(7);

            try{
                username = jwtTokenUtil.getUsernameFromToken(jwtToken);
            }catch (IllegalArgumentException e){
                System.err.println("Unable to get JWT Token");
            }catch (ExpiredJwtException e){
                System.err.println("JWT Token has expired");
            }
        }else{
            assert filterChain != null;
            filterChain.doFilter(request,response);
            logger.warn("JWT Token header does not begin with Bear String");
            return;
        }

        if (username !=null && SecurityContextHolder.getContext().getAuthentication()==null){
            UserDetails userDetails = this.userDetailsService.loadUserByUsername(username);

            if(jwtTokenUtil.isTokenValid(jwtToken, userDetails) && !blacklistedTokenService.isTokenBlacklisted(jwtToken)){
                UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(userDetails,null,userDetails.getAuthorities());
                usernamePasswordAuthenticationToken.setDetails(
                        new WebAuthenticationDetailsSource().buildDetails(request)
                );
                SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
            }
        }
        assert filterChain != null;
        filterChain.doFilter(request,response);
    }
}
