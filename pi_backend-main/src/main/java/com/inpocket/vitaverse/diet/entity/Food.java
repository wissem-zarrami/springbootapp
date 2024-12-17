package com.inpocket.vitaverse.diet.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;

import java.util.Objects;
import java.util.Set;

@Entity
@Data
public class Food {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    long foodId;

    private String foodName;
    private int Cals;
    private int Carbs;
    private int prots;

    @ManyToMany(mappedBy = "foods", cascade = CascadeType.ALL)
    @JsonIgnore
    private Set<Meal> meals;

    @Override
    public int hashCode() {
        return Objects.hash(foodId);
    }
}
