package com.inpocket.vitaverse.user.responce;

import com.inpocket.vitaverse.user.entity.AccountStatus;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Lob;
import lombok.Data;

import java.time.LocalDate;
import java.util.List;

@Data
public class JwtResponse {

    private String token;
    private String type = "Bearer";
    private String username;
    private String firstName;
    private String lastName;
    private String email;
    private String phoneNumber;
    private String dateOfBirth;
    private String gender;
    private Long weight;
    private Long height;

    private String diploma;
    private String profileImageUrl;
    private List<String> roles;
    @Enumerated(EnumType.STRING)
    private AccountStatus accountStatus;
    public JwtResponse(String token,  String username, String firstName, String lastName, String email,
                       String phoneNumber, String dateOfBirth, String gender, Long weight, Long height,
                      String diploma, String profileImageUrl, List<String> roles,AccountStatus accountStatus) {

        this.token = token;

        this.username = username;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.dateOfBirth = dateOfBirth;
        this.gender = gender;
        this.weight = weight;
        this.height = height;
        this.diploma = diploma;
        this.profileImageUrl = profileImageUrl;
        this.roles = roles;
    }

    // Getter pour l'access token
    public String getAccessToken() {
        return token;
    }
    public void setAccessToken(String accessToken) {
        this.token = accessToken;
    }

    // Getter pour le type de token
    public String getTokenType() {
        return type;
    }
    public void setTokenType(String tokenType) {
        this.type = tokenType;
    }
}
