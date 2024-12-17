package com.inpocket.vitaverse.goal.controller;

import com.inpocket.vitaverse.goal.entity.Task;
import com.inpocket.vitaverse.goal.service.TaskService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@AllArgsConstructor
@RequestMapping("api/tasks")
public class TaskController {
    TaskService taskService;

    @PostMapping("/add")
    @ResponseStatus(HttpStatus.CREATED)
    public Task createTask(@RequestBody Task task){
        return taskService.createTask(task);
    }

    @PutMapping("/update")
    @ResponseStatus(HttpStatus.OK)
    public Task updateTask(@RequestBody Task task){
        return taskService.updateTask(task);
    }

    @GetMapping("/{taskId}")
    @ResponseStatus(HttpStatus.OK)
    public Optional<Task> getTaskById(@PathVariable("taskId") long taskId){
        return taskService.getTaskById(taskId);
    }

    @DeleteMapping("/delete/{taskId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteTask(@PathVariable("taskId") long taskId){
        taskService.deleteTask(taskId);
    }

    @GetMapping("/getAll")
    @ResponseStatus(HttpStatus.OK)
    public List<Task> getAllTasks(){
        return taskService.getAllTasks();
    }

    @GetMapping("/byGoal/{goalId}")
    @ResponseStatus(HttpStatus.OK)
    public List<Task> getTasksByGoalId(@PathVariable("goalId") long goalId) {
        return taskService.getTasksByGoalId(goalId);
    }

    @PostMapping("/add/{goalId}")
    @ResponseStatus(HttpStatus.CREATED)
    public Task createTaskAnsSetGoal(@RequestBody Task task, @PathVariable("goalId") long goalId) {
     return taskService.createTaskAndSetGoal(task,goalId);
    }

    @GetMapping("/byUser/{userId}")
    @ResponseStatus(HttpStatus.OK)
    public List<Task> findByUserUserId(@PathVariable("userId") long userId) {
        return taskService.findByUserUserId(userId);
    }
    @GetMapping("/beforeDeadline/{userId}")
    @ResponseStatus(HttpStatus.OK)
    public List<Task> findByUserUserIdAndCompletedBeforeDeadline(@PathVariable("userId") long userId) {
        return taskService.findByUserUserIdAndCompletedBeforeDeadline(userId);
    }

    @GetMapping("/afterDeadline/{userId}")
    @ResponseStatus(HttpStatus.OK)
    public List<Task> findByUserUserIdAndIncompleteAfterDeadline(@PathVariable("userId") long userId) {
        return taskService.findByUserUserIdAndIncompleteAfterDeadline(userId);
    }
}
