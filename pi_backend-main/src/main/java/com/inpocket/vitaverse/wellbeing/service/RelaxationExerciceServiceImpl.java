package com.inpocket.vitaverse.wellbeing.service;
import com.inpocket.vitaverse.wellbeing.entity.RelaxationExercice;
import com.inpocket.vitaverse.wellbeing.entity.UserProgress;
import com.inpocket.vitaverse.wellbeing.repository.RelaxationExerciceRepository;
import com.inpocket.vitaverse.wellbeing.repository.UserProgressRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

@Service
public class RelaxationExerciceServiceImpl implements RelaxationExerciceService {
    @Autowired
    private RelaxationExerciceRepository relaxationExerciceRepository;
    @Autowired
    private  UserProgressRepository  userProgressRepository;



    @Override
    public List<RelaxationExercice> getAllRelaxationExercices() {
        return relaxationExerciceRepository.findAll();
    }

    @Override
    public Optional<RelaxationExercice> getRelaxationExerciceById(long id) {
        return relaxationExerciceRepository.findById(id);
    }

    @Override
    public RelaxationExercice saveRelaxationExercice(RelaxationExercice relaxationExercice) {
        return relaxationExerciceRepository.save(relaxationExercice);
    }

    @Override
    public void deleteRelaxationExerciceById(long id) {
        relaxationExerciceRepository.deleteById(id);
    }

    @Autowired
    private WCloudinaryService wCloudinaryService;
    @Override
    public void setExerciseVideoUrl(String ExerciseName, MultipartFile file) {
        try {
            String exerciseVideoUrl = wCloudinaryService.upload(file).toString(); // Upload the video and get the URL

            RelaxationExercice relaxationExercice = relaxationExerciceRepository.findRelaxationExerciceByExerciseRelaxationName(ExerciseName);
            if (relaxationExercice != null) {
                relaxationExercice.setVideoUrl(exerciseVideoUrl); // Set the image URL for the meal
                relaxationExerciceRepository.save(relaxationExercice);
            }
        } catch (IOException e) {
            // Handle the IOException (e.g., log the error, perform fallback action, etc.)
            e.printStackTrace(); // Example of handling the exception
        }
    }


    @Override
    public void updateRelaxationExercice(long id, RelaxationExercice updatedExercice) {
        Optional<RelaxationExercice> optionalExercice = relaxationExerciceRepository.findById(id);
        if (optionalExercice.isPresent()) {
            RelaxationExercice existingExercice = optionalExercice.get();
            existingExercice.setExerciseRelaxationName(updatedExercice.getExerciseRelaxationName());
            existingExercice.setDuration(updatedExercice.getDuration());
            existingExercice.setInstructions(updatedExercice.getInstructions());
            existingExercice.setRelaxationExerciceType(updatedExercice.getRelaxationExerciceType());

            // Si une nouvelle URL de vidéo est fournie, mettez à jour l'URL de la vidéo
            if (updatedExercice.getVideoUrl() != null) {
                existingExercice.setVideoUrl(updatedExercice.getVideoUrl());
            }

            relaxationExerciceRepository.save(existingExercice);
        } else {
            throw new RuntimeException("RelaxationExercice not found with id: " + id);
        }
    }

    @Override
    @Transactional
    public void deleteExerciceFromUserProgress(long relaxationExerciceId) {
        RelaxationExercice relaxationExercice = relaxationExerciceRepository.findById(relaxationExerciceId)
                .orElseThrow(() -> new IllegalArgumentException("RelaxationExercice not found"));

        // Récupérer toutes les entrées de la table UserProgress liées à cet exercice de relaxation
        List<UserProgress> userProgressList = relaxationExercice.getUserProgressLists();

        // Supprimer chaque entrée de la table UserProgress
        for (UserProgress userProgress : userProgressList) {
            userProgressRepository.delete(userProgress);
        }

        // Ensuite, supprimer l'exercice de relaxation lui-même
        relaxationExerciceRepository.delete(relaxationExercice);
    }

}
