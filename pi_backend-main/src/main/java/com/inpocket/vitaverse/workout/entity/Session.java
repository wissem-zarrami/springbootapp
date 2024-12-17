package com.inpocket.vitaverse.workout.entity;

import com.inpocket.vitaverse.goal.entity.Goal;
import com.inpocket.vitaverse.user.entity.User;
import jakarta.persistence.*;
import lombok.Data;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;
@Entity
@Data
public class Session {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long sessionId;
    private String SessionName ;
    Date date;
    @ManyToOne
    WorkoutRoutine workoutRoutine;

    @OneToMany(cascade = CascadeType.ALL, mappedBy="session")
    private Set<Exercice> Exercices;


    @ManyToMany
    @JoinTable(
            name = "user_session",
            joinColumns = @JoinColumn(name = "session_id"),
            inverseJoinColumns = @JoinColumn(name = "user_id")
    )
    private Set<User> users = new HashSet<>();






}
