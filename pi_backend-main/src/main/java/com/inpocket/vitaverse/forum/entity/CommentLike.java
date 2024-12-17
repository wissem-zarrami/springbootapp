package com.inpocket.vitaverse.forum.entity;

import com.inpocket.vitaverse.user.entity.User;
import jakarta.persistence.*;
import lombok.Data;
@Entity
@Data
public class CommentLike {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long commentLikeId;
    @ManyToOne()
    User user;
    @ManyToOne
    Comment comment;


}
