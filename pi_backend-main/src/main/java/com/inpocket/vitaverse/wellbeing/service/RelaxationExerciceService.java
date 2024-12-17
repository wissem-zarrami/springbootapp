package com.inpocket.vitaverse.wellbeing.service;
import com.inpocket.vitaverse.user.entity.User;
import com.inpocket.vitaverse.wellbeing.entity.RelaxationExercice;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Optional;

public interface RelaxationExerciceService {
    List<RelaxationExercice> getAllRelaxationExercices();
    Optional<RelaxationExercice> getRelaxationExerciceById(long id);
    RelaxationExercice saveRelaxationExercice(RelaxationExercice relaxationExercice);
    void deleteRelaxationExerciceById(long id);
    void setExerciseVideoUrl(String ExerciseName, MultipartFile file);
    public void updateRelaxationExercice(long id, RelaxationExercice updatedExercice);

    public void deleteExerciceFromUserProgress(long relaxationExerciceId);

}
