package com.inpocket.vitaverse.workout.service;

import com.inpocket.vitaverse.workout.entity.WorkoutRoutine;

import java.util.List;
import java.util.Optional;

public interface WorkoutRoutineService {


    List<WorkoutRoutine> getAllWorkoutRoutines();

    Optional<WorkoutRoutine> getWorkoutRoutineById(Long id);

    WorkoutRoutine saveOrUpdateWorkoutRoutine(WorkoutRoutine workoutRoutine);

    void deleteWorkoutRoutine(Long id);
}
