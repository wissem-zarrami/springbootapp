package com.inpocket.vitaverse.goal.service;


import com.inpocket.vitaverse.goal.entity.Task;

import java.util.List;
import java.util.Optional;

public interface TaskService {
    Task createTask(Task task);
    Task updateTask(Task task);
    Optional<Task> getTaskById(long taskId);
    void deleteTask(long taskId);
    List<Task> getAllTasks();
    List<Task> getTasksByGoalId(long goalId);
    Task createTaskAndSetGoal(Task task , long goalId);
    List<Task> findByUserUserId(Long user_userId);
    List<Task> findByUserUserIdAndCompletedBeforeDeadline(Long user_userId);

    List<Task> findByUserUserIdAndIncompleteAfterDeadline(Long user_userId);

    List<Task> findByUserUserIdAndIncomplete(Long user_userId);
}

