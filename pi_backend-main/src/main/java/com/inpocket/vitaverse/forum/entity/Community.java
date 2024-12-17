package com.inpocket.vitaverse.forum.entity;

import com.inpocket.vitaverse.user.entity.User;
import jakarta.persistence.*;
import lombok.Data;

import java.util.Date;
import java.util.Set;

@Entity
@Data
public class Community {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long communityId;
    private Date createdAt;
    private String communityName;
    @ManyToOne(fetch = FetchType.EAGER)
    private User creator; // Many communities can have one creator

    @ManyToMany
    private Set<User> members; // Many communities can have many members

    @OneToMany(mappedBy = "community", cascade = CascadeType.ALL , fetch = FetchType.LAZY)
    private Set<Post> posts; // One community can have many posts
}
