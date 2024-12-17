package com.inpocket.vitaverse.wellbeing.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Entity
@Data
public class RelaxationExercice {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    long relaxationExerciceId;
    private String exerciseRelaxationName;
    private int duration;
    private String instructions;
    @Enumerated(EnumType.STRING)
    private RelaxationExerciceType relaxationExerciceType;
    private String videoUrl;

    @JsonIgnore
    @OneToMany(mappedBy = "relaxationExercice")
    private List<UserProgress> userProgressLists;


}
