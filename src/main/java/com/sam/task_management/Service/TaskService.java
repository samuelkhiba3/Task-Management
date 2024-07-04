package com.sam.task_management.Service;

import com.sam.task_management.Dto.CreateUpdateTaskDto;
import com.sam.task_management.Dto.TaskDto;
import com.sam.task_management.Exception.ResourceNotFoundException;
import com.sam.task_management.Mapper.TaskMapper;
import com.sam.task_management.Model.Priority;
import com.sam.task_management.Model.Status;
import com.sam.task_management.Model.Task;
import com.sam.task_management.Model.User;
import com.sam.task_management.Respository.TaskRepository;
import com.sam.task_management.Respository.UserRepository;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

/**
 * TaskService class that provides CRUD operations for tasks.
 * This class uses the TaskRepository and UserRepository to perform database operations.
 * It also uses the TaskMapper to map between DTOs and entities.
 *
 * @author LS Khiba
 * @version 1.0
 */
@Service
public class TaskService {
    private final TaskRepository taskRepository;
    private final UserRepository userRepository;

    /**
     * Constructor that injects the task repository and user repository.
     *
     * @param taskRepository the task repository
     * @param userRepository the user repository
     */
    public TaskService(TaskRepository taskRepository, UserRepository userRepository) {
        this.taskRepository = taskRepository;
        this.userRepository = userRepository;
    }

    /**
     * Creates a new task.
     *
     * @param createUpdateTaskDto the task DTO
     * @return the created taskDto
     */
    @Transactional
    public TaskDto createTask(CreateUpdateTaskDto createUpdateTaskDto){
        Long userId = createUpdateTaskDto.getUserId();
        User user=null;
        if(userId!=null){
            user = userRepository.findById(userId).orElse(null);
        }
        Task newTask = TaskMapper.dtoToEntity(createUpdateTaskDto, new Task(), user);
        taskRepository.save(newTask);
        return TaskMapper.entityToDto(newTask);
    }

    /**
     * Gets a task by ID.
     *
     * @param taskId the task ID
     * @return the taskDto
     */
    public TaskDto getTaskById(Long taskId){
        Task task =taskRepository.findById(taskId).orElseThrow(
                ()->new ResourceNotFoundException("Task Not Found with id: "+taskId)
        );
        return TaskMapper.entityToDto(task);
    }

    /**
     * Gets all tasks.
     *
     * @return list of taskDto
     */
    public List<TaskDto> getAllTasks(){
        List<Task> tasks = taskRepository.findAll();
        return tasks.stream().map(TaskMapper::entityToDto).toList();
    }

    /**
     * Gets tasks by user ID.
     *
     * @param userId the user ID
     * @return list of taskDto
     */
    public List<TaskDto> getByUserId(Long userId){
         List<Task> tasks = taskRepository.findByUserId(userId);
        return tasks.stream().map(TaskMapper::entityToDto).toList();
    }

    /**
     * Updates a task.
     *
     * @param createUpdateTaskDto the task DTO
     * @param taskId  the task ID
     * @return the updated taskDto
     */
    @Transactional
    public TaskDto updateTask(CreateUpdateTaskDto createUpdateTaskDto, Long taskId){
        Task task = taskRepository.findById(taskId).orElseThrow(
                ()->new ResourceNotFoundException("Task Not Found with id: "+taskId)
        );
        User user = userRepository.findById(createUpdateTaskDto.getUserId()).orElseThrow(
                ()->new ResourceNotFoundException("User Not Found with id: "+createUpdateTaskDto.getUserId())
        );
        Task updatedTask = TaskMapper.dtoToEntity(createUpdateTaskDto, task, user);
        taskRepository.save(updatedTask);
        return TaskMapper.entityToDto(updatedTask);
    }

    /**
     * Deletes a task by ID.
     *
     * @param taskId the task ID
     */
    @Transactional
    public void deleteTaskById(Long taskId){
        taskRepository.deleteById(taskId);
    }

    /**
     * Assigns a task to a user.
     *
     * @param taskId the task ID
     * @param userId the user ID
     * @return the assigned taskDto
     */
    @Transactional
    public TaskDto assignTask(Long taskId, Long userId){
        Task task = taskRepository.findById(taskId).orElseThrow(
                ()->new ResourceNotFoundException("Task Not Found with id: "+taskId)
        );
        User user = userRepository.findById(userId).orElseThrow(
                ()->new ResourceNotFoundException("User Not Found with id: "+userId)
        );
        task.setUser(user);
        taskRepository.save(task);
        return TaskMapper.entityToDto(task);
    }

    /**
     * Unassigns a task from a user.
     *
     * @param taskId the task ID
     * @return the unassigned taskDto
     */
    @Transactional
    public TaskDto unassignTask(Long taskId){
        Task task = taskRepository.findById(taskId).orElseThrow(
                ()->new ResourceNotFoundException("Task Not Found with id: "+taskId)
        );
        task.setUser(null);
        taskRepository.save(task);
        return TaskMapper.entityToDto(task);
    }

    /**
     * Updates a task's status.
     *
     * @param taskId  the task ID
     * @param status the status
     * @return the updated taskDto
     */
    @Transactional
    public TaskDto updateTaskStatus(Long taskId, String status){
        Task task = taskRepository.findById(taskId).orElseThrow(
                ()->new ResourceNotFoundException("Task Not Found with id: "+taskId)
        );
        task.setStatus(Status.valueOf(status));
        taskRepository.save(task);
        return TaskMapper.entityToDto(task);
    }

    /**
     * Updates a task's priority.
     *
     * @param taskId   the task ID
     * @param priority the priority
     * @return the updated taskDto
     */
    @Transactional
    public TaskDto updateTaskPriority(Long taskId, String priority){
        Task task = taskRepository.findById(taskId).orElseThrow(
                ()->new ResourceNotFoundException("Task Not Found with id: "+taskId)
        );
        task.setPriority(Priority.valueOf(priority));
        taskRepository.save(task);
        return TaskMapper.entityToDto(task);
    }

    /**
     * Updates a task's due date.
     *
     * @param taskId the task ID
     * @param date   the due date
     * @return the updated taskDto
     */
    @Transactional
    public TaskDto updateDueDate(Long taskId, String date){
        Task task = taskRepository.findById(taskId).orElseThrow(
                ()->new ResourceNotFoundException("Task Not Found with id: "+taskId)
        );
        task.setDueDate(LocalDate.parse(date));
        taskRepository.save(task);
        return TaskMapper.entityToDto(task);
    }
}