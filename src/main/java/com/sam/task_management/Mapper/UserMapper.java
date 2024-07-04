package com.sam.task_management.Mapper;

import com.sam.task_management.Dto.CreateUpdateUserDto;
import com.sam.task_management.Dto.UserDto;
import com.sam.task_management.Model.Task;
import com.sam.task_management.Model.User;

/**
 * UserMapper class that maps CreateUpdateUserDto to User entity.
 *
 * @author LS Khiba
 * @version 1.0
 */
public class UserMapper {

    /**
     * Maps a CreateUpdateUserDto to a User entity.
     *
     * @param createUpdateUserDto     the user DTO
     * @param userEntity  the user entity
     * @param task        the task entity (optional)
     * @return the mapped user entity
     */
    public static User dtoToEntity(CreateUpdateUserDto createUpdateUserDto, User userEntity, Task task){
        userEntity.setFirstName(createUpdateUserDto.getFirstName());
        userEntity.setLastName(createUpdateUserDto.getLastName());
        userEntity.setEmail(createUpdateUserDto.getEmail());
        userEntity.setPassword(createUpdateUserDto.getPassword());
        userEntity.setRole(createUpdateUserDto.getRole());
        if(task!=null){
            userEntity.getTasks().add(task);
        }
        return userEntity;
    }

    public static UserDto entityToDto(User userEntity){
        UserDto userDto = new UserDto();
        userDto.setId(userEntity.getId());
        userDto.setFirstName(userEntity.getFirstName());
        userDto.setLastName(userEntity.getLastName());
        userDto.setEmail(userEntity.getEmail());
        userDto.setRole(userEntity.getRole());
        return userDto;
    }
}
