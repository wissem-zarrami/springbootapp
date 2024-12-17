package com.inpocket.vitaverse.wellbeing.service;
import com.inpocket.vitaverse.user.entity.User;
import com.inpocket.vitaverse.wellbeing.entity.RelaxationExercice;
import com.inpocket.vitaverse.wellbeing.entity.UserProgress;
import com.inpocket.vitaverse.wellbeing.repository.UserProgressRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;
import java.util.ArrayList;


@Service
public class UserProgressServiceImpl implements UserProgressService {
    @Autowired
    private UserProgressRepository userProgressRepository;

    @Override
    public List<UserProgress> getAllUserProgress() {
        return userProgressRepository.findAll();
    }

    @Override
    public Optional<UserProgress> getUserProgressById(long id) {
        return userProgressRepository.findById(id);
    }

    @Override
    public UserProgress saveUserProgress(UserProgress userProgress) {
        return userProgressRepository.save(userProgress);
    }

    @Override
    public void deleteUserProgressById(long id) {
        userProgressRepository.deleteById(id);
    }


    @Override
    public List<RelaxationExercice> getRelaxationExercisesForUser(Long userId) {
        return userProgressRepository.findRelaxationExercisesByUserId(userId);
    }


    @Override
    public void markExerciseAsWatched(Long userId, Long exerciseId) {
        UserProgress userProgress = userProgressRepository.findByUserIdAndExerciseId(userId, exerciseId);
        if (userProgress != null) {
            userProgress.setRelaxationProgressScore(userProgress.getRelaxationProgressScore() + 1);
            userProgressRepository.save(userProgress);
        }
    }

    @Override

    public Long countExercisesForUser(Long userId) {
        return userProgressRepository.countByProgressUserUserId(userId);
    }
@Override
    public boolean isRelaxationExerciseCompletedForUser(User user, long exerciseId) {
        UserProgress userProgress = userProgressRepository.findByUserIdAndExerciseId(user.getUserId(), exerciseId);

        return userProgress != null && userProgress.getRelaxationProgressScore() == 1;
    }
}