package com.inpocket.vitaverse.wellbeing.controller;


import com.inpocket.vitaverse.wellbeing.entity.Feedback;
import com.inpocket.vitaverse.wellbeing.service.FeddbackService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/feedbacks")
public class FeedbackController {
    @Autowired
    private FeddbackService feedbackService;

    @GetMapping
    public List<Feedback> getAllFeedbacks() {
        return feedbackService.getAllFeedbacks();
    }

    @GetMapping("/{id}")
    public Feedback getFeedbackById(@PathVariable long id) {
        Optional<Feedback> feedback = feedbackService.getFeedbackById(id);
        return feedback.orElse(null);
    }

    @PostMapping
    public Feedback saveFeedback(@RequestBody Feedback feedback) {
        return feedbackService.saveFeedback(feedback);
    }

    @DeleteMapping("/{id}")
    public void deleteFeedbackById(@PathVariable long id) {
        feedbackService.deleteFeedbackById(id);
    }
}