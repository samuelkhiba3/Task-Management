package com.sam.task_management.Model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDate;

/**
 * Represents a blacklisted token entity.
 * This entity stores tokens that have been blacklisted and are no longer valid.
 * @author LS Khiba
 * @version 1.0
 */
@Entity
@Data
public class BlacklistedToken {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @CreationTimestamp
    private LocalDate dateBlacklisted;

    private String token;
}
