package com.sam.task_management.Mapper;

import com.sam.task_management.Dto.CreateUpdateTaskDto;
import com.sam.task_management.Dto.TaskDto;
import com.sam.task_management.Dto.UserDto;
import com.sam.task_management.Model.Task;
import com.sam.task_management.Model.User;

/**
 * TaskMapper class that maps between CreateUpdateTaskDto, TaskDto and Task entity.
 *
 * @author LS Khiba
 * @version 1.0
 */

public class TaskMapper {

    /**
     * Maps a CreateUpdateTaskDto to a Task entity.
     *
     * @param createUpdateTaskDto    the task DTO
     * @param taskEntity the task entity
     * @param user       the user entity
     * @return the mapped task entity
     */
    public static Task dtoToEntity(CreateUpdateTaskDto createUpdateTaskDto, Task taskEntity, User user){
        taskEntity.setTitle(createUpdateTaskDto.getTitle());
        taskEntity.setDescription(createUpdateTaskDto.getDescription());
        taskEntity.setStatus(createUpdateTaskDto.getStatus());
        taskEntity.setPriority(createUpdateTaskDto.getPriority());
        taskEntity.setUser(user);
        taskEntity.setDueDate(createUpdateTaskDto.getDueDate());
        return taskEntity;
    }

    /**
     * Maps a Task entity to a TaskDto.
     *
     * @param taskEntity the task entity
     * @return the mapped task dto
     */
    public static TaskDto entityToDto(Task taskEntity){
        TaskDto taskDto = new TaskDto();
        taskDto.setId(taskEntity.getId());
        taskDto.setTitle(taskEntity.getTitle());
        taskDto.setDescription(taskEntity.getDescription());
        taskDto.setCreatedDate(taskEntity.getCreatedDate());
        taskDto.setPriority(taskEntity.getPriority());
        taskDto.setStatus(taskEntity.getStatus());
        taskDto.setDueDate(taskEntity.getDueDate());
        UserDto userDto = UserMapper.entityToDto(taskEntity.getUser());
        taskDto.setAssignedUser(userDto);
        return taskDto;
    }
}
