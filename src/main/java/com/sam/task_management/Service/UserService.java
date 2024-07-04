package com.sam.task_management.Service;

import com.sam.task_management.Dto.CreateUpdateUserDto;
import com.sam.task_management.Dto.UserDto;
import com.sam.task_management.Exception.ResourceNotFoundException;
import com.sam.task_management.Mapper.UserMapper;
import com.sam.task_management.Model.Task;
import com.sam.task_management.Model.User;
import com.sam.task_management.Respository.TaskRepository;
import com.sam.task_management.Respository.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * UserService class that provides CRUD operations for users.
 * This class uses the UserRepository and TaskRepository to perform database operations.
 * It also uses the UserMapper to map between DTOs and entities.
 * Additionally, it uses the PasswordEncoder to encode and decode passwords.
 *
 * @author LS Khiba
 * @version 1.0
 */
@Service
public class UserService {

    private final UserRepository userRepository;
    private final TaskRepository taskRepository;
    private final PasswordEncoder passwordEncoder;

    /**
     * Constructor that injects the user repository, task repository, and password encoder.
     *
     * @param userRepository the user repository
     * @param taskRepository the task repository
     * @param passwordEncoder the password encoder
     */
    public UserService(UserRepository userRepository, TaskRepository taskRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.taskRepository = taskRepository;
        this.passwordEncoder = passwordEncoder;
    }

    /**
     * Creates a new user.
     *
     * @param createUpdateUserDto the user DTO
     * @return the created userDto
     */
    @Transactional
    public UserDto createUser(CreateUpdateUserDto  createUpdateUserDto){
        Long taskId = createUpdateUserDto.getTaskId();
        Task task =null;
        if(taskId!=null){
            task = taskRepository.findById(taskId).orElse(null);
        }
        User newUser = UserMapper.dtoToEntity(createUpdateUserDto,new User(),task);
        newUser.setPassword(passwordEncoder.encode(newUser.getPassword()));
        userRepository.save(newUser);
        return UserMapper.entityToDto(newUser);
    }

    /**
     * Gets a user by ID.
     *
     * @param userId the user ID
     * @return the userDto
     */
    public UserDto getUserById(Long userId){
        User user = userRepository.findById(userId).orElseThrow(
                ()-> new ResourceNotFoundException("User not found with id: "+userId)
        );
        return UserMapper.entityToDto(user);
    }

    /**
     * Gets all users.
     *
     * @return the usersDto
     */
    public List<UserDto>  getAllUser(){
        List<User> users = userRepository.findAll();
        return users.stream().map(UserMapper::entityToDto).toList();
    }

    /**
     * Updates a user.
     *
     * @param userId the user ID
     * @param userDto the user DTO
     * @return the updated userDto
     */
    @Transactional
    public UserDto updateUser(Long userId, CreateUpdateUserDto userDto){
        User user = userRepository.findById(userId).orElseThrow(
                ()-> new ResourceNotFoundException("User not found with id: "+userId)
        );
        User updatedUser = UserMapper.dtoToEntity(userDto,user,null);
        userRepository.save(updatedUser);
        return UserMapper.entityToDto(updatedUser);
    }

    /**
     * Updates a user's password.
     *
     * @param userId the user ID
     * @param password the new password
     * @return the updated userDto
     */
    @Transactional
    public UserDto updatePassword(Long userId,String password){
        User user = userRepository.findById(userId).orElseThrow(
                ()-> new ResourceNotFoundException("User not found with id: "+userId)
        );
        user.setPassword(passwordEncoder.encode(password));
        userRepository.save(user);
        return UserMapper.entityToDto(user);
    }

    /**
     * Deletes a user by ID.
     *
     * @param userId the user ID
     */
    @Transactional
    public void deleteUser(Long userId){
        userRepository.deleteById(userId);
    }

}
