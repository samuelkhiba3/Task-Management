package com.sam.task_management.Dto;

import lombok.Data;

/**
 * LoginRequest class that represents a login request.
 *
 * @author LS Khiba
 * @version 1.0
 */
@Data
public class LoginRequest {
    private String email;
    private String password;
}
