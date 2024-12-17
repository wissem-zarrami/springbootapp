package com.inpocket.vitaverse.wellbeing.entity;

import com.inpocket.vitaverse.user.entity.User;
import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Entity
@Data
public class Tip {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    long tipId;
    private String tipTitle;
    private String Content;
    private TipType tipType;

    @ManyToMany
    private List<User> usertip;
}
