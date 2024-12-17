package com.inpocket.vitaverse.workout.service;

import com.inpocket.vitaverse.workout.entity.WorkoutRoutine;
import com.inpocket.vitaverse.workout.repository.WorkoutRoutineRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class WorkoutRoutineServiceImpl implements WorkoutRoutineService {

    @Autowired
    private WorkoutRoutineRepository workoutRoutineRepository;

    @Override
    public List<WorkoutRoutine> getAllWorkoutRoutines() {
        return workoutRoutineRepository.findAll();
    }

    @Override
    public Optional<WorkoutRoutine> getWorkoutRoutineById(Long id) {
        return workoutRoutineRepository.findById(id);
    }

    @Override
    public WorkoutRoutine saveOrUpdateWorkoutRoutine(WorkoutRoutine workoutRoutine) {
        return workoutRoutineRepository.save(workoutRoutine);
    }

    @Override
    public void deleteWorkoutRoutine(Long id) {
        workoutRoutineRepository.deleteById(id);
    }
}
