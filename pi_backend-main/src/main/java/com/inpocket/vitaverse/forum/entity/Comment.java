package com.inpocket.vitaverse.forum.entity;

import com.inpocket.vitaverse.user.entity.User;
import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Data

public class Comment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long commentId;
    private String content;
    private Date createdAt;
    private Date updatedAt;
    private  boolean reply;

    @ManyToOne()
    @JoinColumn(name = "postId", referencedColumnName = "postId")
    Post post;
    @ManyToOne()
    @JoinColumn(name = "userId", referencedColumnName = "userId")
    private User user;
    @OneToMany(cascade = CascadeType.ALL, mappedBy="comment",orphanRemoval = true)
    private Set<CommentLike> commentLikes;
    @OneToMany(cascade = CascadeType.ALL)
    private Set<Comment> replies ;






}
