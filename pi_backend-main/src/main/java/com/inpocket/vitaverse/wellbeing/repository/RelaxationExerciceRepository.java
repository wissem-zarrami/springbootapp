package com.inpocket.vitaverse.wellbeing.repository;
import com.inpocket.vitaverse.wellbeing.entity.RelaxationExercice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RelaxationExerciceRepository extends JpaRepository<RelaxationExercice, Long>{
    RelaxationExercice findRelaxationExerciceByExerciseRelaxationName(String exerciseName);



}
