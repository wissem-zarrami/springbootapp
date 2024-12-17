package com.inpocket.vitaverse.wellbeing.repository;

import com.inpocket.vitaverse.wellbeing.entity.Notes;
import com.inpocket.vitaverse.wellbeing.entity.PsychologistCustomerRelation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository

public interface NotesRepository  extends JpaRepository<Notes, Long> {


    List<Notes> findByUsersUserId(Long userId);
}
