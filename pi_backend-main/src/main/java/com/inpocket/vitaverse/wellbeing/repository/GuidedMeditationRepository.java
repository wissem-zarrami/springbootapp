package com.inpocket.vitaverse.wellbeing.repository;
import com.inpocket.vitaverse.wellbeing.entity.GuidedMeditation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GuidedMeditationRepository extends JpaRepository<GuidedMeditation, Long>{
}
