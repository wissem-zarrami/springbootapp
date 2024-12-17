package com.inpocket.vitaverse.wellbeing.service;
import com.inpocket.vitaverse.wellbeing.entity.GuidedMeditation;
import com.inpocket.vitaverse.wellbeing.repository.GuidedMeditationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class GuidedMeditationServiceImpl implements GuidedMeditationService {
    @Autowired
    private GuidedMeditationRepository guidedMeditationRepository;

    @Override
    public List<GuidedMeditation> getAllGuidedMeditations() {
        return guidedMeditationRepository.findAll();
    }

    @Override
    public Optional<GuidedMeditation> getGuidedMeditationById(long id) {
        return guidedMeditationRepository.findById(id);
    }

    @Override
    public GuidedMeditation saveGuidedMeditation(GuidedMeditation guidedMeditation) {
        return guidedMeditationRepository.save(guidedMeditation);
    }

    @Override
    public void deleteGuidedMeditationById(long id) {
        guidedMeditationRepository.deleteById(id);
    }
}
