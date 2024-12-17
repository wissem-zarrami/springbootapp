package com.inpocket.vitaverse.wellbeing.service;
import com.inpocket.vitaverse.wellbeing.entity.JournalEntry;
import com.inpocket.vitaverse.wellbeing.repository.JournalEntryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class JournalEntryServiceImpl implements JournalEntryService {
    @Autowired
    private JournalEntryRepository journalEntryRepository;

    @Override
    public List<JournalEntry> getAllJournalEntries() {
        return journalEntryRepository.findAll();
    }

    @Override
    public Optional<JournalEntry> getJournalEntryById(Long id) {
        return journalEntryRepository.findById(id);
    }

    @Override
    public JournalEntry saveJournalEntry(JournalEntry journalEntry) {
        return journalEntryRepository.save(journalEntry);
    }

    @Override
    public void deleteJournalEntryById(Long id) {
        journalEntryRepository.deleteById(id);
    }
}
