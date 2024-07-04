package com.sam.task_management.Dto;

import com.sam.task_management.Model.Priority;
import com.sam.task_management.Model.Status;
import lombok.Data;

import java.time.LocalDate;

/**
 * CreateUpdateTaskDto class that represents a data transfer object for creating and updating tasks.
 *
 * @author LS Khiba
 * @version 1.0
 */
@Data
public class CreateUpdateTaskDto {
    private String title;
    private String description;
    private LocalDate dueDate;
    private Status status;
    private Priority priority;
    private LocalDate createdDate;
    private Long userId;
}

