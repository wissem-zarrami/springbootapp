package com.inpocket.vitaverse.workout.repository;
import com.inpocket.vitaverse.workout.entity.WorkoutRoutine;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface WorkoutRoutineRepository extends JpaRepository<WorkoutRoutine, Long>{
}
