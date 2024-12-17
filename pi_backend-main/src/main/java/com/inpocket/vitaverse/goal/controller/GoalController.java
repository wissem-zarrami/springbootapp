package com.inpocket.vitaverse.goal.controller;

import com.inpocket.vitaverse.goal.entity.Goal;
import com.inpocket.vitaverse.goal.entity.Task;
import com.inpocket.vitaverse.goal.service.GoalService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@AllArgsConstructor
@RequestMapping("api/Goals")

public class GoalController {
    GoalService goalService;

    @PostMapping("/add")
    @ResponseStatus(HttpStatus.CREATED)
    public Goal createGoal(@RequestBody Goal goal){
        return goalService.createGoal(goal);
    }

    @PutMapping("/update")
    @ResponseStatus(HttpStatus.OK)
    public Goal updateGoal(@RequestBody Goal goal){
        return goalService.updateGoal(goal);
    }

    @GetMapping("/{goalId}")
    @ResponseStatus(HttpStatus.OK)
    public Optional<Goal> getGoalById(@PathVariable("goalId") long goalId){
        return goalService.getGoalById(goalId);
    }

    @DeleteMapping("/delete/{goalId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteGoal(@PathVariable("goalId") long goalId){
         goalService.deleteGoal(goalId);
    }

    @GetMapping("/getAll")
    @ResponseStatus(HttpStatus.OK)
    public List<Goal> getAllGoals(){
        return goalService.getAllGoals();
    }

    @GetMapping("/byUser/{userId}")
    @ResponseStatus(HttpStatus.OK)
    public List<Goal> findByUserUserId(@PathVariable("userId") long userId) {
        return goalService.findByUserUserId(userId);
    }
    @PostMapping("/add/{userId}")
    @ResponseStatus(HttpStatus.CREATED)
    public Goal createGoalAndSetUser(@RequestBody Goal goal, @PathVariable("userId") long userId) {
        return goalService.createGoalAndSetUser(goal,userId);
    }

    @GetMapping("/beforeDeadline/{userId}")
    @ResponseStatus(HttpStatus.OK)
    public List<Goal> findByUserUserIdAndCompletedBeforeDeadline(@PathVariable("userId") long userId) {
        return goalService.findByUserUserIdAndCompletedBeforeDeadline(userId);
    }

    @GetMapping("/AfterDeadline/{userId}")
    @ResponseStatus(HttpStatus.OK)
    public List<Goal> findByUserUserIdAndIncompletedAfterDeadline(@PathVariable("userId") long userId) {
        return goalService.findByUserUserIdAndIncompletedAfterDeadline(userId);
    }

    @GetMapping("/OrderByProgressAsc/{userId}")
    @ResponseStatus(HttpStatus.OK)
    public List<Goal> findByUserUserIdOrderByProgressAsc(@PathVariable("userId") long userId) {
        return goalService.findByUserUserIdOrderByProgressAsc(userId);
    }


    @GetMapping("/OrderByProgressDesc/{userId}")
    @ResponseStatus(HttpStatus.OK)
    public List<Goal> findByUserUserIdOrderByProgressDesc(@PathVariable("userId") long userId) {
        return goalService.findByUserUserIdOrderByProgressDesc(userId);
    }

    @GetMapping("/OrderByDeadlineAsc/{userId}")
    @ResponseStatus(HttpStatus.OK)
    public List<Goal> findByUserUserIdOrderByDeadlineAsc(@PathVariable("userId") long userId) {
        return goalService.findByUserUserIdOrderByDeadlineAsc(userId);
    }

    @GetMapping("/OrderByDeadlineDesc/{userId}")
    @ResponseStatus(HttpStatus.OK)
    public List<Goal> findByUserUserIdOrderByDeadlineDesc(@PathVariable("userId") long userId) {
        return goalService.findByUserUserIdOrderByDeadlineDesc(userId);
    }

    @GetMapping("/Search/{userId}")
    @ResponseStatus(HttpStatus.OK)
    public List<Goal> search(@PathVariable("userId") long userId, @RequestParam String text) {
        return goalService.search(userId,text);
    }
}
