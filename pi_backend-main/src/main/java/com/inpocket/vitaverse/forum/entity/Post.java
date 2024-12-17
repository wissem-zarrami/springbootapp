package com.inpocket.vitaverse.forum.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.inpocket.vitaverse.user.entity.User;
import jakarta.persistence.*;
import lombok.Data;

import java.util.Date;
import java.util.List;
import java.util.Set;

@Entity
@Data
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})

public class Post {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long postId;
    private String textContent;
    @ManyToOne()
    @JoinColumn(name = "userId", referencedColumnName = "userId")
    private User user;
    private String attachment;
    private Date createdAt;
    private Date updatedAt;

   /* @OneToMany(cascade = CascadeType.ALL, mappedBy="post")
    private Set<Comment> Comments;*/

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "post", orphanRemoval = true)
    private Set<Vote> votes;
    private PostType postType ;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "post")
    private Set<Comment> comments;
    @ManyToOne()
    @JoinColumn(name = "communityId", referencedColumnName = "communityId")
    private Community community; // One community can have many posts




}
