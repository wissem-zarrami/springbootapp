package com.inpocket.vitaverse.wellbeing.service;
import com.inpocket.vitaverse.wellbeing.entity.Feedback;

import java.util.List;
import java.util.Optional;
public interface FeddbackService {

    List<Feedback> getAllFeedbacks();
    Optional<Feedback> getFeedbackById(long id);
    Feedback saveFeedback(Feedback feedback);
    void deleteFeedbackById(long id);
}
