package com.inpocket.vitaverse.goal.service;

import com.inpocket.vitaverse.goal.entity.Goal;
import com.inpocket.vitaverse.goal.entity.Task;
import com.inpocket.vitaverse.goal.repository.GoalRepository;
import com.inpocket.vitaverse.goal.repository.TaskRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class TaskServiceImp implements TaskService{
    TaskRepository taskRepository;
    GoalRepository goalRepository;
    @Override
    public Task createTask(Task task) {
        return taskRepository.save(task);
    }

    @Override
    public Task updateTask(Task task) {
        return taskRepository.save(task);
    }

    @Override
    public Optional<Task> getTaskById(long taskId) {
        return taskRepository.findById(taskId);
    }

    @Override
    public void deleteTask(long taskId) {
        taskRepository.deleteById(taskId);
    }

    @Override
    public List<Task> getAllTasks() {
        return taskRepository.findAll();
    }

    @Override
    public List<Task> getTasksByGoalId(long goalId) {
        return taskRepository.findByGoal_GoalId(goalId);
    }

    @Override
    public Task createTaskAndSetGoal(Task task, long goalId) {
        try {
            Goal goal = goalRepository.findById(goalId).orElseThrow(() -> new RuntimeException("Goal not found"));
            task.setGoal(goal);
            return taskRepository.save(task);
        } catch (Exception e) {
            throw new RuntimeException("Error creating task and setting goal", e);
        }
    }
    @Override
    public List<Task> findByUserUserId(Long user_userId) {
        return taskRepository.findByGoalUserUserId(user_userId);
    }
    @Override
    public List<Task> findByUserUserIdAndCompletedBeforeDeadline(Long user_userId) {
        List<Task> completedBeforeDeadline = new ArrayList<>();
        List<Task> completedTasks = taskRepository.findByGoalUserUserIdAndCompletedIsTrue(user_userId);
        for (Task task : completedTasks) {
            Date completionDate = task.getCompletionDate();
            if (completionDate != null && completionDate.before(task.getDeadline())){
                completedBeforeDeadline.add(task);
            }
        }
        return completedBeforeDeadline;
    }

    @Override
    public List<Task> findByUserUserIdAndIncompleteAfterDeadline(Long userUserId) {
        List<Task> incompleteTasksAfterDeadline = new ArrayList<>();

        List<Task> incompleteTasks = taskRepository.findByGoalUserUserIdAndCompletedIsFalse(userUserId);

        Date currentDate = new Date();

        for (Task task : incompleteTasks) {
            if (currentDate.after(task.getDeadline())) {
                incompleteTasksAfterDeadline.add(task);
            }
        }
        return incompleteTasksAfterDeadline;
    }

    @Override
    public List<Task> findByUserUserIdAndIncomplete(Long user_userId) {
        return taskRepository.findByGoalUserUserIdAndCompletedIsFalse(user_userId);
    }


}
