package com.inpocket.vitaverse.workout.controller;

import com.inpocket.vitaverse.workout.entity.WorkoutRoutine;
import com.inpocket.vitaverse.workout.service.WorkoutRoutineService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("api/workout-routines")
public class WorkoutRoutineController {

    private final WorkoutRoutineService workoutRoutineService;

    @Autowired
    public WorkoutRoutineController(WorkoutRoutineService workoutRoutineService) {
        this.workoutRoutineService = workoutRoutineService;
    }

    @PostMapping("/add")
    @ResponseStatus(HttpStatus.CREATED)
    public WorkoutRoutine createWorkoutRoutine(@RequestBody WorkoutRoutine workoutRoutine) {
        return workoutRoutineService.saveOrUpdateWorkoutRoutine(workoutRoutine);
    }

    @PutMapping("/update")
    @ResponseStatus(HttpStatus.OK)
    public WorkoutRoutine updateWorkoutRoutine(@RequestBody WorkoutRoutine workoutRoutine) {
        return workoutRoutineService.saveOrUpdateWorkoutRoutine(workoutRoutine);
    }

    @GetMapping("/all")
    public List<WorkoutRoutine> getAllWorkoutRoutines() {
        return workoutRoutineService.getAllWorkoutRoutines();
    }

    @GetMapping("/{id}")
    public ResponseEntity<WorkoutRoutine> getWorkoutRoutineById(@PathVariable Long id) {
        Optional<WorkoutRoutine> workoutRoutine = workoutRoutineService.getWorkoutRoutineById(id);
        return workoutRoutine.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());

    }


    @DeleteMapping("/delete/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteWorkoutRoutine(@PathVariable("id") long id) {
        workoutRoutineService.deleteWorkoutRoutine(id);
    }
}
