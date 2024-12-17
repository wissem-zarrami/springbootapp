package com.inpocket.vitaverse.user.controllers;

import com.inpocket.vitaverse.user.entity.*;
import com.inpocket.vitaverse.user.security.services.UserService;


import com.inpocket.vitaverse.wellbeing.service.PsychologistCustomerRelationService;
import jakarta.validation.groups.Default;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.Set;

@RestController
@RequestMapping("/api/auth")

public class UserController {

  @Autowired
  private UserService userService;
  @Autowired
  private PsychologistCustomerRelationService relationService;

  @GetMapping
  public List<User> getAllUsers() {
    return userService.getAllUsers();
  }



  @PostMapping
  public ResponseEntity<User> createUser(@RequestBody User user) {
    return new ResponseEntity<>(userService.createUser(user), HttpStatus.CREATED);
  }

  @PutMapping("/{username}")
  public ResponseEntity<User> updateUser(@PathVariable String username, @RequestBody User userDetails) {
    User updatedUser = userService.updateUser(username, userDetails);
    return ResponseEntity.ok(updatedUser);
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<Void> deleteUser(@PathVariable Long id) {
    userService.deleteUser(id);
    return new ResponseEntity<>(HttpStatus.NO_CONTENT);
  }
  @PutMapping("/users/{userId}/status")
  public ResponseEntity<User> updateUserAccountStatus(@PathVariable long userId, @RequestParam AccountStatus accountStatus) {
    User updatedUser = userService.updateUserAccountStatus(userId, accountStatus);
    return ResponseEntity.ok(updatedUser);
  }

  @GetMapping("/users/{userId}/status") // Utilisation de userId ici
  public ResponseEntity<AccountStatus> checkAccountStatus(@PathVariable long userId) {
    AccountStatus accountStatus = userService.checkAccountStatus(userId);
    return ResponseEntity.ok(accountStatus);
  }


  @PostMapping("/users/{username}/BANNED") // Utilisation de userId ici
  public ResponseEntity<Void> banUser(@PathVariable String username) {
    userService.banUser(username);
    return ResponseEntity.ok().build();
  }
  @GetMapping("/username/{username}")
  public ResponseEntity<?> getUserByUsername(@PathVariable String username) {
    try {
      Optional<UserDTO> userDTO = userService.getUserByUsername(username);
      if (userDTO.isPresent()) {
        return ResponseEntity.ok(userDTO.get());
      } else {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User Not Found with username: " + username);
      }
    } catch (Exception e) {
      return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Une erreur s'est produite lors de la récupération des détails de l'utilisateur.");
    }
  }

  @GetMapping("/getUserIdByUsername/{username}")
  public User getUserIdByUsername(@PathVariable String username) {

      return userService.getUserIdByUsername(username);
  }
@PutMapping("/forgot-password")
  public ResponseEntity<String>forgotPassword(@RequestParam String email){
    return new ResponseEntity<>(userService.forgotPassword(email),HttpStatus.OK);
}
  @PutMapping("/set-password-with-verification")
  public ResponseEntity<String> setPasswordWithVerification(

          @RequestParam String verificationCode,
          @RequestParam String newPassword) {

    String result = userService.setPasswordWithVerification( verificationCode, newPassword);

    // Vérification du résultat
    if (result.equals("New password set successfully. Login with the new password.")) {
      return ResponseEntity.ok(result);
    } else {
      return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(result);
    }
  }


  @PutMapping("/users/{userId}/bad-words")
  public int updateUserBadWordsCount(@PathVariable long userId) {

     return userService.updateUserBadWordsCount(userId);


  }




  @GetMapping("/coaches")
  public List<User> getCoaches() {
    return userService.getUsersByRole(ERole.COACH);
  }

  @GetMapping("/nutritionists")
  public List<User> getNutritionists() {
    return userService.getUsersByRole(ERole.NUTRITIONIST);
  }

  @GetMapping("/customers")
  public List<User> getCustomers() {
    return userService.getUsersByRole(ERole.CUSTOMER);
  }
  @GetMapping("/{userId}")
  public ResponseEntity<User> getUserById(@PathVariable Long userId) {
    Optional<User> userOptional = userService.getUserById(userId);
    return userOptional.map(user -> new ResponseEntity<>(user, HttpStatus.OK))
            .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
  }

  @GetMapping("/getUserIdByUsernames/{username}")
  public ResponseEntity<Long> getUserIdByUsernames(@PathVariable String username) {
    Long userId = userService.getUserIdByUsernames(username);
    if (userId != null) {
      return ResponseEntity.ok(userId);
    } else {
      return ResponseEntity.notFound().build();
    }
  }
  @GetMapping("/psychologists")
  public List<User> getPsychologists() {
    return userService.getUsersByRole(ERole.PSYCHOLOGIST);
  }
  @GetMapping("/psychologists/{customerId}")
  public ResponseEntity<List<User>> getAllPsychologistsForCustomer(@PathVariable Long customerId) {
    List<User> psychologists = relationService.getAllPsychologistsForCustomer(customerId);
    return ResponseEntity.ok(psychologists);
  }
  @GetMapping("/{userId}/role")
  public ResponseEntity<ERole> getUserRole(@PathVariable Long userId) {
    ERole role = userService.getUserRoleName(userId);
    if (role != null) {
      return new ResponseEntity<>(role, HttpStatus.OK);
    } else {
      return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
  }


}

