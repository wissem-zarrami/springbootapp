package com.inpocket.vitaverse.workout.service;
import com.inpocket.vitaverse.workout.entity.Exercice;
import com.inpocket.vitaverse.workout.entity.ExerciceType;

import java.util.List;
import java.util.Optional;

public interface ExerciceService {

    public List<Exercice> getAllExercices();

    public  Optional<Exercice> getExerciceById(Long id);

    public Exercice saveOrUpdateExercice(Exercice exercice);

    public void deleteExercice(Long id);
    public List<Exercice> getExercicesByType(ExerciceType type);

    public void associateExerciseWithUser(long exerciseId, long userId);
    public List<Exercice> getExercisesByUserId(long userId);
}