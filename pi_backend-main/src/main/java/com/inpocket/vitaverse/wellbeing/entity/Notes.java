package com.inpocket.vitaverse.wellbeing.entity;


import com.inpocket.vitaverse.user.entity.User;
import jakarta.persistence.*;
import lombok.Data;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Entity
@Data
public class Notes {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long noteId;
    private String notename ;
    private Date notedate;



    @ManyToMany
    @JoinTable(
            name = "user_note",
            joinColumns = @JoinColumn(name = "note_id"),
            inverseJoinColumns = @JoinColumn(name = "user_id")
    )
    private Set<User> users = new HashSet<>();




}
