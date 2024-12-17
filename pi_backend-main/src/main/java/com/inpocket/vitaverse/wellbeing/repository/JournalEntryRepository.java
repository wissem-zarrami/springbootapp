package com.inpocket.vitaverse.wellbeing.repository;

import com.inpocket.vitaverse.wellbeing.entity.JournalEntry;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JournalEntryRepository extends JpaRepository<JournalEntry, Long > {
}
