package com.inpocket.vitaverse.workout.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.inpocket.vitaverse.diet.entity.Image;
import com.inpocket.vitaverse.user.entity.User;
import jakarta.persistence.*;
import lombok.Data;

import java.util.*;

@Entity
@Data
public class Exercice {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    long exerciceId;
    String exerciceName;

    @Enumerated(EnumType.STRING)
    @Column(length = 254) // Adjust the length according to the longest enum value
    ExerciceType muscle;
    String exerciceImageUrl;

    @OneToMany(mappedBy = "exercice", cascade = CascadeType.ALL)
    private List<Image> images;
    public List<Image> getImages() {
        if (this.images == null) {
            this.images = new ArrayList<>();
        }
        return this.images;
    }
    @ManyToOne
    Session session;

    @Override
    public int hashCode() {
        return Objects.hash(exerciceId);
    }

    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(
            name = "user_exercice",
            joinColumns = @JoinColumn(name = "exercice_id"),
            inverseJoinColumns = @JoinColumn(name = "user_id")
    )
    @JsonIgnore
    private Set<User> users = new HashSet<>();

}