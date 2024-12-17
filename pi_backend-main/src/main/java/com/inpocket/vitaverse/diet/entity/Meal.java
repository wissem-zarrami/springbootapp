package com.inpocket.vitaverse.diet.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.inpocket.vitaverse.user.entity.User;
import jakarta.persistence.*;
import lombok.Data;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Entity
@Data
public class Meal {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    int mealId;
    String mealName;
    int totalCals;
    int totalCarbs;
    int totalprots;
    String mealImageUrl;

    @OneToMany(mappedBy = "meal", cascade = CascadeType.ALL)
    private Set<Image> images = new HashSet<>();

    @ManyToMany(cascade = CascadeType.ALL)
    @JsonBackReference // Jackson annotation to manage the back part of reference
    private Set<Food> foods;

    @ManyToOne
    Diet diet;

    @Override
    public int hashCode() {
        return Objects.hash(mealId);
    }

    @ManyToMany
    @JoinTable(
            name = "UserMeal",
            joinColumns = @JoinColumn(name = "mealId"),
            inverseJoinColumns = @JoinColumn(name = "userId")
    )
    @JsonIgnore
    private Set<User> users = new HashSet<>();
}
