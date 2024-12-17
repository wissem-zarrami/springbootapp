package com.inpocket.vitaverse.store.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.inpocket.vitaverse.user.entity.User;
import jakarta.persistence.*;
import lombok.Data;

import java.util.Date;

@Entity
@Data
public class ProductReview {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    long productReviewId;
    @JsonIgnore
    @ManyToOne
    Product product;
    @ManyToOne
    User user;
    int rating;
    String comment;
    Date reviewDate;

    // these attributes used for contact with client/not for DB.
    @Transient
    private Long productId;
}
