package com.sam.task_management.Security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

/**
 * JwtTokenUtil class that generates and verifies JWT tokens.
 * This class is responsible for generating JWT tokens based on user details and verifying their validity.
 * It uses a secret key to sign and verify the tokens.
 *
 * @author LS Khiba
 * @version 1.0
 */
@Component
public class JwtTokenUtil {

    /**
     * Returns the signing key.
     *
     * @return the signing key
     */
    private SecretKey getSigningKey() {
        //The secret key used for signing and verifying JWT tokens.
        String SECRET_KEY = "Z3VsZmJhbmRyYW5nZXRob3NlbWF0aGVtYXRpY3NleHBsYW5hdGlvbnBhaWRpZGVudGk=";
        byte[] keyBytes = Decoders.BASE64URL.decode(SECRET_KEY);
        return Keys.hmacShaKeyFor(keyBytes);
    }

    /**
     * Generates a JWT token based on user details.
     *
     * @param userDetails the user details
     * @return the JWT token
     */
    public String generateToken(UserDetails userDetails) {
        Map<String, Object> claims = new HashMap<>();
        claims.put("role", userDetails.getAuthorities().iterator().next().getAuthority());
        claims.put("enabled", userDetails.isEnabled());
        claims.put("accountNonLocked", userDetails.isAccountNonLocked());
        claims.put("accountNonExpired", userDetails.isAccountNonExpired());
        claims.put("credentialsNonExpired", userDetails.isCredentialsNonExpired());

        return Jwts.builder()
                .claims(claims)
                .subject(userDetails.getUsername())
                .issuedAt(new Date(System.currentTimeMillis()))
                .expiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60 * 10))
                .signWith(getSigningKey())
                .compact();
    }

    /**
     * Returns all claims from a JWT token.
     *
     * @param token the JWT token
     * @return the claims
     */
    private Claims getAllClaimsFromToken(String token) {
        return Jwts
                .parser()
                .verifyWith(getSigningKey())
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }

    /**
     * Returns a claim from a JWT token using a function.
     *
     * @param token            the JWT token
     * @param claimsTFunction the function to extract the claim
     * @return the claim
     */
    public <T> T getClaimFromToken(String token, Function<Claims, T> claimsTFunction) {
        return claimsTFunction.apply(getAllClaimsFromToken(token));
    }

    /**
     * Returns the username from a JWT token.
     *
     * @param token the JWT token
     * @return the username
     */
    public String getUsernameFromToken(String token) {
        return getClaimFromToken(token, Claims::getSubject);
    }

    /**
     * Returns the expiration date from a JWT token.
     *
     * @param token the JWT token
     * @return the expiration date
     */
    public Date getExpirationDateFromToken(String token) {
        return getClaimFromToken(token, Claims::getExpiration);
    }

    private Boolean isTokenExpired(String token) {
        return getExpirationDateFromToken(token).before(new Date(System.currentTimeMillis()));
    }

    /**
     * Checks if a JWT token is expired.
     *
     * @param token the JWT token
     * @return true if the token is expired, false otherwise
     */
    public Boolean isTokenValid(String token, UserDetails userDetails) {
        final String username = getUsernameFromToken(token);
        return username.equals(userDetails.getUsername())
                && !isTokenExpired(token);
    }

}
