package com.inpocket.vitaverse.diet.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.inpocket.vitaverse.workout.entity.Exercice;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Image {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String name;
    private String imageUrl;
    private String imageId;

    @JsonIgnore // Add this annotation to prevent serialization of the meal field
    @ManyToOne
    @JoinColumn(name = "meal_id")
    private Meal meal;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name ="exerciceId")
    private Exercice exercice;

    public Image(String name, String imageUrl, String imageId) {
        this.name = name;
        this.imageUrl = imageUrl;
        this.imageId = imageId;
    }

}