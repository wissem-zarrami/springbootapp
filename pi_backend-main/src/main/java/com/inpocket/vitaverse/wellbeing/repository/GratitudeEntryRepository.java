package com.inpocket.vitaverse.wellbeing.repository;
import com.inpocket.vitaverse.wellbeing.entity.GratitudeEntry;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GratitudeEntryRepository extends JpaRepository<GratitudeEntry, Long>{
}
