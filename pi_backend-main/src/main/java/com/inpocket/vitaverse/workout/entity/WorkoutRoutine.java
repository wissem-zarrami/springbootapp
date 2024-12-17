package com.inpocket.vitaverse.workout.entity;

import com.inpocket.vitaverse.goal.entity.Task;
import com.inpocket.vitaverse.user.entity.User;
import jakarta.persistence.*;
import lombok.Data;

import java.util.Set;

@Entity
@Data
public class WorkoutRoutine {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long workoutRoutineId;
    private String workoutRoutineName ;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "workoutRoutine")
    private Set<Session> sessions;

    @ManyToMany(cascade = CascadeType.ALL)
    private Set<User> users;
}
