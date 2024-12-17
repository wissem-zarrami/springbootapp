package com.inpocket.vitaverse.goal.service;


import com.inpocket.vitaverse.goal.entity.Goal;

import java.util.List;
import java.util.Optional;

public interface GoalService {
    Goal createGoal(Goal goal);
    Goal updateGoal(Goal goal);
    Optional<Goal> getGoalById(long goalId);
    void deleteGoal(long goalId);
    List<Goal> getAllGoals();
    List<Goal> findByUserUserId(Long user_userId);
    Goal createGoalAndSetUser(Goal goal,long userId);
    List<Goal> findByUserUserIdAndCompletedBeforeDeadline (long userId);

    List<Goal> findByUserUserIdAndIncompletedAfterDeadline (long userId);

    List<Goal> findByUserUserIdOrderByProgressAsc(long userId);
    List<Goal> findByUserUserIdOrderByProgressDesc(Long userUserId);
    List<Goal> findByUserUserIdOrderByDeadlineAsc(Long userUserId);
    List<Goal> findByUserUserIdOrderByDeadlineDesc(Long userUserId);
    List<Goal> search(Long user_userId, String text);
}
