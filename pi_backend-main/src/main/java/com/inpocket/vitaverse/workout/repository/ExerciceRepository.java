package com.inpocket.vitaverse.workout.repository;
import com.inpocket.vitaverse.workout.entity.Exercice;
import com.inpocket.vitaverse.workout.entity.ExerciceType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ExerciceRepository extends JpaRepository<Exercice, Long>{
    List findAllByMuscle(ExerciceType type);

    List<Exercice> findByUsersUserId(long userId);
}

