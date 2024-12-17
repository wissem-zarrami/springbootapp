package com.inpocket.vitaverse.wellbeing.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.inpocket.vitaverse.user.entity.User;
import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

@Entity
@Data
public class Rating {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "rated_user_id") // assuming this is the foreign key to the user who is being rated
    private User ratedUser;
    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "rating_user") // assuming this is the foreign key to the user who is rating
    private User ratingUser;

    private int ratingValue;
}