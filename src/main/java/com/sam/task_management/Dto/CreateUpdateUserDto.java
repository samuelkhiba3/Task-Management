package com.sam.task_management.Dto;

import com.sam.task_management.Model.Role;

import lombok.Data;

/**
 * CreateUpdateUserDto class that represents a data transfer object for creating and updating user.
 *
 * @author LS Khiba
 * @version 1.0
 */
@Data
public class CreateUpdateUserDto {
    private String firstName;
    private String lastName;
    private String password;
    private String email;
    private Role role;
    private Long taskId;
}
