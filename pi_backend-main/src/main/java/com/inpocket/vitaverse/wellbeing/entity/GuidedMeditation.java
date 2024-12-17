package com.inpocket.vitaverse.wellbeing.entity;

import com.inpocket.vitaverse.user.entity.User;
import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Entity
@Data
public class GuidedMeditation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long guidedMeditationId;
    private String meditationName;
    private int duration;
    private String audiofile;
    private GuidedMeditationType guidedMeditationType;

    @OneToMany(mappedBy = "guidedMeditation")
    private List<UserProgress> userProgressList;


}
