package com.inpocket.vitaverse.diet.entity;

import jakarta.persistence.*;
import lombok.Data;
import com.inpocket.vitaverse.user.entity.*;

import java.util.Set;

@Entity
@Data
public class Diet {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    long dietId;
    String dietName;

    DietType dietType;

    @OneToMany(cascade = CascadeType.ALL, mappedBy="diet")
    private Set<Meal> Meals;

    @ManyToMany(cascade = CascadeType.ALL)
    private Set<User> users;


}

