package com.inpocket.vitaverse.goal.repository;
import com.inpocket.vitaverse.goal.entity.Goal;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface GoalRepository extends JpaRepository<Goal, Long>{
    List<Goal> findByUserUserId(Long user_userId);
    List<Goal> findByUserUserIdAndCompletedIsTrue(Long user_userId);
    List<Goal> findByUserUserIdAndCompletedIsFalse(Long user_userId);

    List<Goal> findByUserUserIdOrderByProgressAsc(Long userUserId);
    List<Goal> findByUserUserIdOrderByProgressDesc(Long userUserId);
    List<Goal> findByUserUserIdOrderByDeadlineAsc(Long userUserId);
    List<Goal> findByUserUserIdOrderByDeadlineDesc(Long userUserId);
    List<Goal> findByUserUserIdAndTitleContainingIgnoreCaseOrUserUserIdAndDescriptionContainingIgnoreCase(Long user_userId, String title, Long user_userId2, String description);

}
