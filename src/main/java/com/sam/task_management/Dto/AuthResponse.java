package com.sam.task_management.Dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * AuthResponse class that represents an authentication response.
 * @author LS Khiba
 * @version 1.0
 */
@Data
@AllArgsConstructor
public class AuthResponse {

    /**
     * The authentication token.
     */
    @JsonProperty("token")
    private String token;
}
