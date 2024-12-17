package com.inpocket.vitaverse.wellbeing.entity;

import com.inpocket.vitaverse.user.entity.User;
import jakarta.persistence.*;
import lombok.Data;

import java.util.Date;

@Entity
@Data
public class GratitudeEntry {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long gratitudeEntryId;
    private Date GratitudeDate;
    private String GratitudeContent;

    @ManyToOne
    private User GratitudeClient;

}
