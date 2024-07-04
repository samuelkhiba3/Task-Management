package com.sam.task_management.Dto;

import com.sam.task_management.Model.Role;
import lombok.Data;

/**
 * SignupRequest class that represents a signup request.
 *
 * @author LS Khiba
 * @version 1.0
 */
@Data
public class SignupRequest {
    private String lastName;
    private String firstName;
    private String email;
    private String password;
    private Role role;
}
