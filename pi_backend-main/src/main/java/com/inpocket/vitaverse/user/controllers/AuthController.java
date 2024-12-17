package com.inpocket.vitaverse.user.controllers;

import com.inpocket.vitaverse.user.Request.LoginRequest;
import com.inpocket.vitaverse.user.Request.SignupRequest;
import com.inpocket.vitaverse.user.entity.AccountStatus;
import com.inpocket.vitaverse.user.entity.ERole;
import com.inpocket.vitaverse.user.entity.Role;
import com.inpocket.vitaverse.user.entity.User;
import com.inpocket.vitaverse.user.repository.RoleRepository;
import com.inpocket.vitaverse.user.repository.UserRepository;
import com.inpocket.vitaverse.user.responce.JwtResponse;
import com.inpocket.vitaverse.user.responce.MessageResponse;

import com.inpocket.vitaverse.user.security.jwt.JwtUtils;
import com.inpocket.vitaverse.user.security.services.CloudinaryService;
import com.inpocket.vitaverse.user.security.services.UserDetailsImpl;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    UserRepository userRepository;

    @Autowired
    RoleRepository roleRepository;

    @Autowired
    PasswordEncoder encoder;
    @Autowired
    CloudinaryService cloudinaryService;
    @Autowired
    JwtUtils jwtUtils;

    @PostMapping("/signin")
    public ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginRequest loginRequest) {

        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));

        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = jwtUtils.generateJwtToken(authentication);

        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();

        // Vérifier si le compte de l'utilisateur est actif
        if (userDetails.getAccountStatus() != AccountStatus.ACTIVE) {
            return ResponseEntity
                    .badRequest()
                    .body(new MessageResponse("Votre compte est inactif."));
        }

        List<String> roles = userDetails.getAuthorities().stream()
                .map(item -> item.getAuthority())
                .collect(Collectors.toList());

        JwtResponse jwtResponse = new JwtResponse(jwt, userDetails.getUsername(),
                userDetails.getFirstName(), userDetails.getLastName(), userDetails.getEmail(),
                userDetails.getPhoneNumber(), userDetails.getDateOfBirth(), userDetails.getGender(),
                userDetails.getWeight(), userDetails.getHeight(), userDetails.getDiploma(),
                userDetails.getProfileImageUrl(), roles, userDetails.getAccountStatus());

        // Return ResponseEntity with JwtResponse object
        return ResponseEntity.ok(jwtResponse);
    }


    @PostMapping(value = "/signup", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<?> registerUser(@RequestParam("username") String username,
                                          @RequestParam("firstName") String firstName,
                                          @RequestParam("lastName") String lastName,
                                          @RequestParam("email") String email,
                                          @RequestParam("password") String password,
                                          @RequestParam("phoneNumber") String phoneNumber,
                                          @RequestParam("dateOfBirth") String dateOfBirth,
                                          @RequestParam("gender") String gender,
                                          @RequestParam(value = "weight", required = false) Long weight,
                                          @RequestParam(value = "height", required = false) Long height,
                                          @RequestParam(value = "diploma", required = false) MultipartFile diplomaFile,
                                          @RequestParam("photo") MultipartFile photoFile,
                                          @RequestParam("role") Set<String> strRoles) {
        if (userRepository.existsByUsername(username)) {
            return ResponseEntity.badRequest().body(new MessageResponse("Error: Username is already taken!"));
        }

        if (userRepository.existsByEmail(email)) {
            return ResponseEntity.badRequest().body(new MessageResponse("Error: Email is already in use!"));
        }

        // Create new user's account
        User user = new User();
        user.setUsername(username);
        user.setFirstName(firstName);
        user.setLastName(lastName);
        user.setEmail(email);
        user.setPassword(encoder.encode(password));
        user.setPhoneNumber(phoneNumber);
        user.setDateOfBirth(dateOfBirth);
        user.setGender(gender);
        if (strRoles.contains("CUSTOMER")) {
            user.setWeight(weight);
            user.setHeight(height);
        }else {
            // Set default values for weight and height if not a customer
            user.setWeight(0L);
            user.setHeight(0L);
        }

        // Upload diploma image if present
        if (diplomaFile != null && !diplomaFile.isEmpty()) {
            try {
                String diplomaImageUrl = cloudinaryService.uploadFile(diplomaFile);
                user.setDiploma(diplomaImageUrl);
            } catch (IOException e) {
                e.printStackTrace();
                return ResponseEntity.badRequest().body(new MessageResponse("Error uploading diploma image."));
            }
        }

        // Upload profile image
        try {
            String profileImageUrl = cloudinaryService.uploadFile(photoFile);
            user.setProfileImageUrl(profileImageUrl);
        } catch (IOException e) {
            e.printStackTrace();
            return ResponseEntity.badRequest().body(new MessageResponse("Error uploading profile image."));
        }

        // Set user roles
        Set<Role> roles = new HashSet<>();
        for (String roleName : strRoles) {
            ERole enumRole = ERole.valueOf(roleName.toUpperCase());
            Role userRole = roleRepository.findByName(enumRole)
                    .orElseThrow(() -> new RuntimeException("Error: Role not found: " + roleName));
            roles.add(userRole);
        }
        user.setRoles(roles);

        // Set account stgitatus based on roles
        if (roles.stream().anyMatch(role -> role.getName() == ERole.CUSTOMER)) {
            user.setAccountStatus(AccountStatus.ACTIVE);
        } else {
            user.setAccountStatus(AccountStatus.INACTIVE);
        }

        userRepository.save(user);

        return ResponseEntity.ok(new MessageResponse("User registered successfully!"));
    }
    @PostMapping("/logout")
    public ResponseEntity<?> logoutUser() {
        SecurityContextHolder.clearContext();
        return ResponseEntity.ok(new MessageResponse("Logged out successfully!"));
    }

}