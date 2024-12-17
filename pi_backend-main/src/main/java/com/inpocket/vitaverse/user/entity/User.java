package com.inpocket.vitaverse.user.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.inpocket.vitaverse.forum.entity.Community;
import com.inpocket.vitaverse.goal.entity.*;


import com.inpocket.vitaverse.wellbeing.entity.*;import com.inpocket.vitaverse.diet.entity.*;
import com.inpocket.vitaverse.workout.entity.*;


import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDate;


import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;

import java.util.Set;

@Entity
@Data
public class User{
    @Id

    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long userId;
    private String username;
    private String firstName;
    private String lastName;
    private String email;
    private String password;
    private String phoneNumber;
    private String dateOfBirth;
    private String gender;
    private Long weight;
    private Long height;
    private String diploma;
    private String profileImageUrl;
    @Enumerated(EnumType.STRING)
    private AccountStatus accountStatus;

    // Many-to-One relationship with Room entity

    private int badWordsCount = 0;


    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(  name = "user_roles",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id"))
    private Set<Role> roles ;



    @OneToMany(mappedBy = "following")
    private Set<Follow> followers = new HashSet<>();
    @OneToMany(mappedBy = "follower")
    private Set<Follow> following = new HashSet<>();
    // One-to-Many relation with messages (sender)

    // Many-to-Many relation with conversations (participants)

    // One-to-Many relation with notifications
    @OneToMany(mappedBy = "recipient", cascade = CascadeType.ALL)
    private Set<Notification> notifications = new HashSet<>();

    @OneToMany(cascade = CascadeType.ALL, mappedBy="user")
    private Set<Goal> Goals;


    @ManyToMany(mappedBy = "members")
    private Set<Community> communitiesJoined; // Many users can belong to many communities
    @OneToMany(mappedBy = "creator")
    private Set<Community> communitiesCreated; // One user can create many communities
    @ManyToMany(mappedBy="users", cascade = CascadeType.ALL)
    private Set<Diet> diets;
    @ManyToMany(mappedBy="users", cascade = CascadeType.ALL)
    private Set<WorkoutRoutine> workoutroutines;


    @OneToMany (mappedBy = "FeedClient")
    private List<Feedback> feedbackList;
    @OneToMany (mappedBy = "GratitudeClient")
    private List<GratitudeEntry> gratitudeEntryList;
    @OneToMany (mappedBy = "JournalClient")
    private List<JournalEntry> journalEntryList;
    @OneToMany (mappedBy = "ProgressUser")
    private List<UserProgress> userProgressList;
    @ManyToMany(mappedBy = "usertip")
    private List<Tip> tipList;
    @ManyToMany(mappedBy = "users")
    private Set<Notes> notes = new HashSet<>();
    @JsonIgnore
    @OneToMany(mappedBy = "ratedUser")
    private List<Rating> receivedRatings;
    @JsonIgnore
    @OneToMany(mappedBy = "ratingUser")
    private List<Rating> givenRatings;

    public User() {
        // Default constructor required by JPA
    }

    @ManyToMany(mappedBy = "users")
    private Set<Meal> meals = new HashSet<>();

    @ManyToMany(mappedBy = "users")
    private Set<Exercice> exercices;
    @ManyToMany(mappedBy = "users")
    private Set<Session> sessions;

}


