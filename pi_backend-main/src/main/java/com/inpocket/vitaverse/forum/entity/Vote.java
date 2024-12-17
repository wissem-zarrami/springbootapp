package com.inpocket.vitaverse.forum.entity;

import com.inpocket.vitaverse.user.entity.User;
import jakarta.persistence.*;
import lombok.Data;

import java.util.Date;

@Entity
@Data
public class Vote {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long voteId;
    private VoteType voteType;
    @ManyToOne()
    @JoinColumn(name = "postId", referencedColumnName = "postId")
    Post post;
    @ManyToOne()
    @JoinColumn(name = "userId", referencedColumnName = "userId")
    User user;
    private Date createdAt;
}
