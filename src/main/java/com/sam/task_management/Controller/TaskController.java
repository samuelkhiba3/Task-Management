package com.sam.task_management.Controller;

import com.sam.task_management.Dto.CreateUpdateTaskDto;
import com.sam.task_management.Dto.TaskDto;
import com.sam.task_management.Service.TaskService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * TaskController class that handles task-related requests.
 *
 * @author LS Khiba
 * @version 1.0
 */
@RestController
@RequestMapping("/api/tasks")
public class TaskController {
    private final TaskService taskService;

    /**
     * Constructor that initializes the task service.
     *
     * @param taskService the task service
     */
    public TaskController(TaskService taskService) {
        this.taskService = taskService;
    }

    /**
     * Creates a new task.
     *
     * @param createUpdateTaskDto the task data transfer object
     * @return the created task
     */
    @PostMapping
    public ResponseEntity<TaskDto> createTask(@RequestBody CreateUpdateTaskDto createUpdateTaskDto) {
        return new ResponseEntity<>(taskService.createTask(createUpdateTaskDto), HttpStatus.CREATED);
    }

    /**
     * Gets a task by ID.
     *
     * @param id the task ID
     * @return the task
     */
    @GetMapping("/{id}")
    public ResponseEntity<TaskDto> getTaskById(@PathVariable Long id) {
        return new ResponseEntity<>(taskService.getTaskById(id), HttpStatus.OK);
    }

    /**
     * Gets all tasks.
     *
     * @return the list of tasks
     */
    @GetMapping
    public ResponseEntity<List<TaskDto>> getAllTasks() {
        return new ResponseEntity<>(taskService.getAllTasks(), HttpStatus.OK);
    }

    /**
     * Gets tasks by user ID.
     *
     * @param id the user ID
     * @return the list of tasks
     */
    @GetMapping("user/{id}")
    public ResponseEntity<List<TaskDto>> getTasksByUserId(@PathVariable Long id) {
        return new ResponseEntity<>(taskService.getByUserId(id), HttpStatus.OK);
    }

    /**
     * Updates a task.
     *
     * @param id      the task ID
     * @param createUpdateTaskDto the task data transfer object
     * @return the updated task
     */
    @PreAuthorize("hasAnyRole('ADMIN', 'PROJECT_MANAGER')")
    @PutMapping("/{id}")
    public ResponseEntity<TaskDto> updateTask(@PathVariable Long id, @RequestBody CreateUpdateTaskDto createUpdateTaskDto) {
        return new ResponseEntity<>(taskService.updateTask(createUpdateTaskDto, id), HttpStatus.OK);
    }

    /**
     * Deletes a task by ID.
     *
     * @param id the task ID
     * @return the response
     */
    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTaskById(@PathVariable Long id) {
        taskService.deleteTaskById(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    /**
     * Assigns a task to a user.
     *
     * @param id     the task ID
     * @param userId the user ID
     * @return the updated task
     */
    @PatchMapping("/{id}/assign")
    public ResponseEntity<TaskDto> assignTask(@PathVariable Long id, @RequestBody Long userId) {
        return new ResponseEntity<>(taskService.assignTask(id, userId), HttpStatus.OK);
    }

    /**
     * Unassigns a task from a user.
     *
     * @param id the task ID
     * @return the updated task
     */
    @PatchMapping("/{id}/unassign")
    public ResponseEntity<TaskDto> unassignTask(@PathVariable Long id) {
        return new ResponseEntity<>(taskService.unassignTask(id), HttpStatus.OK);
    }

    /**
     * Updates a task's status.
     *
     * @param id     the task ID
     * @param status the new status
     * @return the updated task
     */
    @PatchMapping("/{id}/status")
    public ResponseEntity<TaskDto> updateTaskStatus(@PathVariable Long id, @RequestBody String status) {
        return new ResponseEntity<>(taskService.updateTaskStatus(id, status), HttpStatus.OK);
    }

    /**
     * Updates a task's priority.
     *
     * @param id      the task ID
     * @param priority the new priority
     * @return the updated task
     */
    @PatchMapping("/{id}/priority")
    public ResponseEntity<TaskDto> updateTaskPriority(@PathVariable Long id, @RequestBody String priority) {
        return new ResponseEntity<>(taskService.updateTaskPriority(id, priority), HttpStatus.OK);
    }

    /**
     * Updates a task's due date.
     *
     * @param id  the task ID
     * @param date the new due date
     * @return the updated task
     */
    @PatchMapping("/{id}/dueDate")
    public ResponseEntity<TaskDto> updateDueDate(@PathVariable Long id, @RequestBody String date) {
        return new ResponseEntity<>(taskService.updateDueDate(id, date),HttpStatus.OK);
    }
}
