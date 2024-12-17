package com.inpocket.vitaverse.goal.repository;
import com.inpocket.vitaverse.goal.entity.Task;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TaskRepository extends JpaRepository<Task, Long>{
    List<Task> findByGoal_GoalId(long goal_goalId);
    List<Task> findByGoalUserUserId(Long user_userId);
    List<Task> findByGoalUserUserIdAndCompletedIsTrue(Long user_userId);
    List<Task> findByGoalUserUserIdAndCompletedIsFalse(Long user_userId);


}
