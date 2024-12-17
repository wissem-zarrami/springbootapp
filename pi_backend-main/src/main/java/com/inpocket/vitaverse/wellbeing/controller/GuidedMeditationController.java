package com.inpocket.vitaverse.wellbeing.controller;
import com.inpocket.vitaverse.wellbeing.entity.GuidedMeditation;
import com.inpocket.vitaverse.wellbeing.service.GuidedMeditationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/guided-meditations")
public class GuidedMeditationController {
    @Autowired
    private GuidedMeditationService guidedMeditationService;

    @GetMapping
    public List<GuidedMeditation> getAllGuidedMeditations() {
        return guidedMeditationService.getAllGuidedMeditations();
    }

    @GetMapping("/{id}")
    public GuidedMeditation getGuidedMeditationById(@PathVariable long id) {
        Optional<GuidedMeditation> guidedMeditation = guidedMeditationService.getGuidedMeditationById(id);
        return guidedMeditation.orElse(null);
    }

    @PostMapping
    public GuidedMeditation saveGuidedMeditation(@RequestBody GuidedMeditation guidedMeditation) {
        return guidedMeditationService.saveGuidedMeditation(guidedMeditation);
    }

    @DeleteMapping("/{id}")
    public void deleteGuidedMeditationById(@PathVariable long id) {
        guidedMeditationService.deleteGuidedMeditationById(id);
    }
}
