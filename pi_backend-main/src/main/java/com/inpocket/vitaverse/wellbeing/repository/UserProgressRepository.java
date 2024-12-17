package com.inpocket.vitaverse.wellbeing.repository;

import com.inpocket.vitaverse.wellbeing.entity.RelaxationExercice;

import com.inpocket.vitaverse.wellbeing.entity.UserProgress;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserProgressRepository extends JpaRepository<UserProgress, Long>{
    @Query("SELECT up.relaxationExercice FROM UserProgress up WHERE up.ProgressUser.userId = :userId")
    List<RelaxationExercice> findRelaxationExercisesByUserId(Long userId);

    @Query("SELECT up FROM UserProgress up WHERE up.ProgressUser.userId = :userId AND up.relaxationExercice.relaxationExerciceId = :exerciseId")
    UserProgress findByUserIdAndExerciseId(@Param("userId") Long userId, @Param("exerciseId") Long exerciseId);

    @Query("SELECT COUNT(up) FROM UserProgress up WHERE up.ProgressUser.userId = :userId")
    Long countByProgressUserUserId(Long userId);

}


