package com.sam.task_management.Dto;

import com.sam.task_management.Model.Role;
import lombok.Data;

@Data
public class UserDto {
    private Long id;
    private String firstName;
    private String lastName;
    private String email;
    private Role role;
}
