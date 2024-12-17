package com.inpocket.vitaverse.wellbeing.entity;

import com.inpocket.vitaverse.user.entity.User;
import jakarta.persistence.*;
import lombok.Data;

import java.util.Date;

@Entity
@Data
public class Feedback {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long feedbackId;
    private Date feedbackDate;
    private String feedbackContent;

    @ManyToOne
    private User FeedClient;

}
