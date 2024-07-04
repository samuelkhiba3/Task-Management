package com.sam.task_management.Dto;

import com.sam.task_management.Model.Priority;
import com.sam.task_management.Model.Status;
import lombok.Data;

import java.time.LocalDate;

@Data
public class TaskDto {
    private Long id;
    private String title;
    private String description;
    private LocalDate dueDate;
    private Status status;
    private Priority priority;
    private LocalDate createdDate;
    private UserDto assignedUser;
}
