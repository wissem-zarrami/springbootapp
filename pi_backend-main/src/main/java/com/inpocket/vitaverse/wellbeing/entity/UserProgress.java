package com.inpocket.vitaverse.wellbeing.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.inpocket.vitaverse.user.entity.User;
import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDate;
import java.util.List;

@Entity
@Data
public class UserProgress {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long userProgressId;
    private long MeditationProgressScore;
    private long RelaxationProgressScore;
    private LocalDate ProgressStartDate;
    private LocalDate ProgressEndDate;

    @JsonIgnore
    @ManyToOne
    private User ProgressUser;
    @JsonIgnore

    @ManyToOne
    private RelaxationExercice relaxationExercice;
    @JsonIgnore

    @ManyToOne
    private GuidedMeditation guidedMeditation;
}
