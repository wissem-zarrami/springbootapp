package com.inpocket.vitaverse.wellbeing.controller;
import com.inpocket.vitaverse.wellbeing.entity.JournalEntry;
import com.inpocket.vitaverse.wellbeing.service.JournalEntryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/journal-entries")
public class JournalEntryController {
    @Autowired
    private JournalEntryService journalEntryService;

    @GetMapping
    public List<JournalEntry> getAllJournalEntries() {
        return journalEntryService.getAllJournalEntries();
    }

    @GetMapping("/{id}")
    public JournalEntry getJournalEntryById(@PathVariable Long id) {
        Optional<JournalEntry> journalEntry = journalEntryService.getJournalEntryById(id);
        return journalEntry.orElse(null);
    }

    @PostMapping
    public JournalEntry saveJournalEntry(@RequestBody JournalEntry journalEntry) {
        return journalEntryService.saveJournalEntry(journalEntry);
    }

    @DeleteMapping("/{id}")
    public void deleteJournalEntryById(@PathVariable Long id) {
        journalEntryService.deleteJournalEntryById(id);
    }
}
