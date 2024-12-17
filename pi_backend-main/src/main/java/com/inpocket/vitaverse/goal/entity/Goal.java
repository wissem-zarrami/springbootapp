package com.inpocket.vitaverse.goal.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.inpocket.vitaverse.user.entity.User;
import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;
import java.util.List;
import java.util.Set;

@Entity
@Data
public class Goal {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long goalId;
    @ManyToOne
    User user;
    @JsonIgnore
    @OneToMany(mappedBy = "goal", cascade = CascadeType.ALL)
    private List<Task> tasks;
    private String title;
    private String description;
    private double progress;
    private boolean completed;
    private Date startDate;
    private Date deadline;
    private Date completionDate;






}
