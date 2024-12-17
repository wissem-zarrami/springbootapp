package com.inpocket.vitaverse.wellbeing.service;
import com.inpocket.vitaverse.wellbeing.entity.JournalEntry;
import java.util.List;
import java.util.Optional;

public interface JournalEntryService {
    List<JournalEntry> getAllJournalEntries();
    Optional<JournalEntry> getJournalEntryById(Long id);
    JournalEntry saveJournalEntry(JournalEntry journalEntry);
    void deleteJournalEntryById(Long id);
}
