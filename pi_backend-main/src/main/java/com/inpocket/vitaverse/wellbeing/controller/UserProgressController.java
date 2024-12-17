package com.inpocket.vitaverse.wellbeing.controller;
import com.inpocket.vitaverse.wellbeing.entity.UserProgress;
import com.inpocket.vitaverse.wellbeing.service.UserProgressService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/user-progress")
public class UserProgressController {
    @Autowired
    private UserProgressService userProgressService;

    @GetMapping
    public List<UserProgress> getAllUserProgress() {
        return userProgressService.getAllUserProgress();
    }

    @GetMapping("/{id}")
    public UserProgress getUserProgressById(@PathVariable long id) {
        Optional<UserProgress> userProgress = userProgressService.getUserProgressById(id);
        return userProgress.orElse(null);
    }

    @PostMapping
    public UserProgress saveUserProgress(@RequestBody UserProgress userProgress) {
        return userProgressService.saveUserProgress(userProgress);
    }

    @DeleteMapping("/{id}")
    public void deleteUserProgressById(@PathVariable long id) {
        userProgressService.deleteUserProgressById(id);
    }



}
