package com.inpocket.vitaverse.user.entity;


import jakarta.persistence.Lob;
import lombok.Data;

import java.time.LocalDate;
import java.util.Set;
import java.util.stream.Collectors;
@Data
public class UserDTO {
    private String username;
    private String firstName;
    private String lastName;
    private String email;
    private String phoneNumber;
    private String dateOfBirth;
    private String gender;
    private Long weight;
    private Long height;

    private Set<ERole> roles;


    private String photo;



    private String diploma;

    // Constructeur
    public UserDTO(User user) {
        this.username = user.getUsername();
        this.firstName = user.getFirstName();
        this.lastName = user.getLastName();
        this.email = user.getEmail();
        this.phoneNumber = user.getPhoneNumber();
        this.dateOfBirth = user.getDateOfBirth();
        this.gender = user.getGender();
        this.weight = user.getWeight();
        this.height = user.getHeight();
        this.diploma = user.getDiploma();
        this.photo=user.getProfileImageUrl();
        this.roles = user.getRoles().stream()
                .map(Role::getName) // Utilisation de la méthode getName de la classe Role
                .collect(Collectors.toSet());
    }

    // Getters et Setters
}

