package com.inpocket.vitaverse.wellbeing.service;

import com.inpocket.vitaverse.wellbeing.entity.Feedback;
import com.inpocket.vitaverse.wellbeing.repository.FeedbackRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
@Service
public class FeedbackServiceImpl implements FeddbackService {
    @Autowired
    private FeedbackRepository feedbackRepository;

    @Override
    public List<Feedback> getAllFeedbacks() {
        return feedbackRepository.findAll();
    }

    @Override
    public Optional<Feedback> getFeedbackById(long id) {
        return feedbackRepository.findById(id);
    }

    @Override
    public Feedback saveFeedback(Feedback feedback) {
        return feedbackRepository.save(feedback);
    }

    @Override
    public void deleteFeedbackById(long id) {
        feedbackRepository.deleteById(id);
    }
}