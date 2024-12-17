package com.inpocket.vitaverse.user.Request;

import jakarta.persistence.Lob;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDate;
import java.util.Set;

@Data
public class SignupRequest {
    @NotBlank
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
    private String ProfileImageUrl;

    private Set<String> role;

}
