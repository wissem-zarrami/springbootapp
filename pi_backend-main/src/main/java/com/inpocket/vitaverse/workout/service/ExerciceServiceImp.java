package com.inpocket.vitaverse.workout.service;

import com.inpocket.vitaverse.user.entity.User;
import com.inpocket.vitaverse.user.repository.UserRepository;
import com.inpocket.vitaverse.workout.entity.Exercice;
import com.inpocket.vitaverse.workout.entity.ExerciceType;
import com.inpocket.vitaverse.workout.repository.ExerciceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ExerciceServiceImp implements ExerciceService{


    @Autowired
    private ExerciceRepository exerciceRepository;
    @Autowired
    private UserRepository userRepository;

    @Override
    public List<Exercice> getAllExercices() {
        return exerciceRepository.findAll();
    }

    @Override
    public Optional<Exercice> getExerciceById(Long id) {
        return exerciceRepository.findById(id);
    }

    @Override
    public Exercice saveOrUpdateExercice(Exercice exercice) {
        return exerciceRepository.save(exercice);
    }

    @Override
    public void deleteExercice(Long id) {
        Exercice exercise = exerciceRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Exercise not found"));

        // Remove the exercise from all users who have it associated
        for (User user : exercise.getUsers()) {
            user.getExercices().remove(exercise);
        }

        // Clear the list of associated users from the exercise
        exercise.getUsers().clear();

        // Now you can safely delete the exercise
        exerciceRepository.deleteById(id);
    }
    @Override
    public List getExercicesByType(ExerciceType type) {
        return exerciceRepository.findAllByMuscle(type);
    }
    @Override
    public void associateExerciseWithUser(long exerciseId, long userId) {
        Exercice exercise = exerciceRepository.findById(exerciseId)
                .orElseThrow(() -> new RuntimeException("Exercise not found"));

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        // Associate the exercise with the user
        exercise.getUsers().add(user);

        // Save the updated exercise to persist the association
        exerciceRepository.save(exercise);
    }
    @Override
    public List<Exercice> getExercisesByUserId(long userId) {
        return exerciceRepository.findByUsersUserId(userId);
    }
}

