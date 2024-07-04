package com.sam.task_management.Respository;

import com.sam.task_management.Model.BlacklistedToken;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

/**
 * Repository for blacklisted tokens.
 * This repository provides methods for accessing and managing blacklisted tokens.
 * @author LS Khiba
 * @version 1.0
 */
public interface BlacklistedTokenRepository extends JpaRepository<BlacklistedToken,Long> {

    /**
     * Finds a blacklisted token by its token value.
     *
     * @param token the token to search for
     * @return an Optional containing the blacklisted token, or empty if not found
     */
    Optional<BlacklistedToken> findByToken(String token);
}
