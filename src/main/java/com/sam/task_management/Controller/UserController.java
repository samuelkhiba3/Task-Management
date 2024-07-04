package com.sam.task_management.Controller;

import com.sam.task_management.Dto.CreateUpdateUserDto;
import com.sam.task_management.Dto.UserDto;
import com.sam.task_management.Service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * UserController class that handles user-related requests.
 *
 * @author LS Khiba
 * @version 1.0
 */
@RestController
@RequestMapping("/api/users")
public class UserController {

    private final UserService userService;

    /**
     * Constructor that initializes the user service.
     *
     * @param userService the user service
     */
    public UserController(UserService userService) {
        this.userService = userService;
    }

    /**
     * Gets a user by ID.
     *
     * @param id the user ID
     * @return the user
     */
    @GetMapping("/{id}")
    public ResponseEntity<UserDto> getUserById(@PathVariable Long id) {
        return new ResponseEntity<>(userService.getUserById(id), HttpStatus.OK);
    }

    /**
     * Gets all users.
     *
     * @return the list of users
     */
    @GetMapping
    public ResponseEntity<List<UserDto>> getAllUsers() {
        return new ResponseEntity<>(userService.getAllUser(), HttpStatus.OK);
    }

    /**
     * Updates a user's password.
     *
     * @param id       the user ID
     * @param password the new password
     * @return the updated user
     */
    @PatchMapping("/{id}/password")
    public ResponseEntity<UserDto> updatePassword(@PathVariable Long id, @RequestBody String password) {
        return new ResponseEntity<>(userService.updatePassword(id, password), HttpStatus.OK);
    }

    /**
     * Updates a user.
     *
     * @param id     the user ID
     * @param createUpdateUserDto the user data transfer object
     * @return the updated user
     */
    @PutMapping("/{id}")
    public ResponseEntity<UserDto> updateUser(@PathVariable Long id, @RequestBody CreateUpdateUserDto createUpdateUserDto) {
        return new ResponseEntity<>(userService.updateUser(id, createUpdateUserDto), HttpStatus.OK);
    }

    /**
     * Deletes a user.
     *
     * @param id the user ID
     * @return the response
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long id) {
        userService.deleteUser(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}