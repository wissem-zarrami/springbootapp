package com.inpocket.vitaverse.wellbeing.service;
import com.inpocket.vitaverse.wellbeing.entity.GratitudeEntry;
import com.inpocket.vitaverse.wellbeing.repository.GratitudeEntryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class GratitudeEntryServiceImpl implements GratitudeEntryService {
    @Autowired
    private GratitudeEntryRepository gratitudeEntryRepository;

    @Override
    public List<GratitudeEntry> getAllGratitudeEntries() {
        return gratitudeEntryRepository.findAll();
    }

    @Override
    public Optional<GratitudeEntry> getGratitudeEntryById(long id) {
        return gratitudeEntryRepository.findById(id);
    }

    @Override
    public GratitudeEntry saveGratitudeEntry(GratitudeEntry gratitudeEntry) {
        return gratitudeEntryRepository.save(gratitudeEntry);
    }

    @Override
    public void deleteGratitudeEntryById(long id) {
        gratitudeEntryRepository.deleteById(id);
    }
}
