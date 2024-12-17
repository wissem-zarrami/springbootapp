package com.inpocket.vitaverse.wellbeing.service;
import com.inpocket.vitaverse.wellbeing.entity.GratitudeEntry;
import java.util.List;
import java.util.Optional;

public interface GratitudeEntryService {
    List<GratitudeEntry> getAllGratitudeEntries();
    Optional<GratitudeEntry> getGratitudeEntryById(long id);
    GratitudeEntry saveGratitudeEntry(GratitudeEntry gratitudeEntry);
    void deleteGratitudeEntryById(long id);
}
