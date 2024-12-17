package com.inpocket.vitaverse.goal.service;

import com.inpocket.vitaverse.goal.entity.Goal;
import com.inpocket.vitaverse.goal.entity.Task;
import com.inpocket.vitaverse.goal.repository.GoalRepository;
import com.inpocket.vitaverse.user.entity.User;
import com.inpocket.vitaverse.user.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class GoalServiceImp implements GoalService {
    GoalRepository goalRepository;
    UserRepository userRepository;
    @Override
    public Goal createGoal(Goal goal) {
        return goalRepository.save(goal);
    }

    @Override
    public Goal updateGoal(Goal goal) {
        return goalRepository.save(goal);
    }

    @Override
    public Optional<Goal> getGoalById(long goalId) {
        return goalRepository.findById(goalId);
    }

    @Override
    public void deleteGoal(long goalId) {
        goalRepository.deleteById(goalId);
    }

    @Override
    public List<Goal> getAllGoals() {
        return goalRepository.findAll();
    }

    @Override
    public List<Goal> findByUserUserId(Long user_userId) {
        return goalRepository.findByUserUserId(user_userId);
    }
    @Override
    public Goal createGoalAndSetUser(Goal goal, long userId) {
        try {
            User user = userRepository.findById(userId).orElseThrow(() -> new RuntimeException("user not found"));
            goal.setUser(user);
            return goalRepository.save(goal);
        } catch (Exception e) {
            throw new RuntimeException("Error creating goal and setting user", e);
        }
    }

    @Override
    public List<Goal> findByUserUserIdAndCompletedBeforeDeadline(long userId) {
        List<Goal> completedGoals = goalRepository.findByUserUserIdAndCompletedIsTrue(userId);
        List<Goal> completedBeforeDeadline = new ArrayList<>();

        for (Goal goal : completedGoals){
            Date completionDate = goal.getCompletionDate();
            if (completionDate != null && completionDate.before(goal.getDeadline())){
                completedBeforeDeadline.add(goal);
            }
        }
        return  completedBeforeDeadline;
    }

    @Override
    public List<Goal> findByUserUserIdAndIncompletedAfterDeadline(long userId) {
        List<Goal> IncompletedAfterDeadline = new ArrayList<>();
        List<Goal> IncompletedGoals = goalRepository.findByUserUserIdAndCompletedIsFalse(userId);

        Date currentDate = new Date();

        for (Goal goal : IncompletedGoals) {
            if (currentDate.after(goal.getDeadline())) {
                IncompletedGoals.add(goal);
            }
        }
        return IncompletedGoals;

    }

    @Override
    public List<Goal> findByUserUserIdOrderByProgressAsc(long userId) {
        return goalRepository.findByUserUserIdOrderByProgressAsc(userId);
    }

    @Override
    public List<Goal> findByUserUserIdOrderByProgressDesc(Long userUserId) {
        return goalRepository.findByUserUserIdOrderByProgressDesc(userUserId);
    }

    @Override
    public List<Goal> findByUserUserIdOrderByDeadlineAsc(Long userUserId) {
        return goalRepository.findByUserUserIdOrderByDeadlineAsc(userUserId);
    }

    @Override
    public List<Goal> findByUserUserIdOrderByDeadlineDesc(Long userUserId) {
        return goalRepository.findByUserUserIdOrderByDeadlineDesc(userUserId);
    }

    @Override
    public List<Goal> search(Long user_userId, String text) {
        return goalRepository.findByUserUserIdAndTitleContainingIgnoreCaseOrUserUserIdAndDescriptionContainingIgnoreCase(user_userId, text, user_userId,text);
    }



}
