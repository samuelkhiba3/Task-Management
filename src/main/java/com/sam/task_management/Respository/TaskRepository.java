package com.sam.task_management.Respository;

import com.sam.task_management.Model.Task;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * TaskRepository interface that provides data access to Task entities.
 *
 * @author LS Khiba
 * @version 1.0
 */
@Repository
public interface TaskRepository extends JpaRepository<Task,Long> {

    /**
     * Finds tasks by user ID.
     *
     * @param userId the user ID
     * @return a list of tasks
     */
    List<Task> findByUserId(Long userId);
}
