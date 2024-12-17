package com.inpocket.vitaverse.wellbeing.controller;
import com.inpocket.vitaverse.wellbeing.entity.GratitudeEntry;
import com.inpocket.vitaverse.wellbeing.repository.GratitudeEntryRepository;
import com.inpocket.vitaverse.wellbeing.service.GratitudeEntryService;
import com.inpocket.vitaverse.wellbeing.entity.GratitudeEntry;
import com.inpocket.vitaverse.wellbeing.service.GratitudeEntryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/gratitude-entries")
public class GratitudeEntryController {
    @Autowired
    private GratitudeEntryService gratitudeEntryService;

    @GetMapping
    public List<GratitudeEntry> getAllGratitudeEntries() {
        return gratitudeEntryService.getAllGratitudeEntries();
    }

    @GetMapping("/{id}")
    public GratitudeEntry getGratitudeEntryById(@PathVariable long id) {
        Optional<GratitudeEntry> gratitudeEntry = gratitudeEntryService.getGratitudeEntryById(id);
        return gratitudeEntry.orElse(null);
    }

    @PostMapping
    public GratitudeEntry saveGratitudeEntry(@RequestBody GratitudeEntry gratitudeEntry) {
        return gratitudeEntryService.saveGratitudeEntry(gratitudeEntry);
    }

    @DeleteMapping("/{id}")
    public void deleteGratitudeEntryById(@PathVariable long id) {
        gratitudeEntryService.deleteGratitudeEntryById(id);
    }
}
