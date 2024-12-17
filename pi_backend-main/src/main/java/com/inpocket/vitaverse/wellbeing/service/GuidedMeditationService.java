package com.inpocket.vitaverse.wellbeing.service;
import com.inpocket.vitaverse.wellbeing.entity.GuidedMeditation;
import java.util.List;
import java.util.Optional;

public interface GuidedMeditationService {
    List<GuidedMeditation> getAllGuidedMeditations();
    Optional<GuidedMeditation> getGuidedMeditationById(long id);
    GuidedMeditation saveGuidedMeditation(GuidedMeditation guidedMeditation);
    void deleteGuidedMeditationById(long id);
}

