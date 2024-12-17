package com.inpocket.vitaverse.wellbeing.service;
import com.inpocket.vitaverse.user.entity.User;
import com.inpocket.vitaverse.wellbeing.entity.RelaxationExercice;
import com.inpocket.vitaverse.wellbeing.entity.UserProgress;
import java.util.List;
import java.util.Optional;

public interface UserProgressService {
    List<UserProgress> getAllUserProgress();

    Optional<UserProgress> getUserProgressById(long id);

    UserProgress saveUserProgress(UserProgress userProgress);

    void deleteUserProgressById(long id);

    List<RelaxationExercice> getRelaxationExercisesForUser(Long userId);
    void markExerciseAsWatched(Long userId, Long exerciseId);

    Long countExercisesForUser(Long userId);

    boolean isRelaxationExerciseCompletedForUser(User user, long exerciseId);
}