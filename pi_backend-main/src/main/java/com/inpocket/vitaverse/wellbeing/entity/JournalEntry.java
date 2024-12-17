package com.inpocket.vitaverse.wellbeing.entity;

import com.inpocket.vitaverse.user.entity.User;
import jakarta.persistence.*;
import lombok.Data;

import java.util.Date;

@Entity
@Data
public class JournalEntry {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long JournalId;
    private Date JournalDate;
    private String JournalContent;
    private String JournalTitle;

    @ManyToOne
    private User JournalClient;

}
