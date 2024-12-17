package com.inpocket.vitaverse.user.security.services;

import com.inpocket.vitaverse.goal.service.MailService;
import com.inpocket.vitaverse.user.Util.EmailUtil;
import com.inpocket.vitaverse.user.entity.*;
import com.inpocket.vitaverse.user.repository.UserRepository;
import jakarta.mail.MessagingException;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityNotFoundException;
import jakarta.persistence.PersistenceContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.*;

@Component

public class UserServiceImpl implements UserService{
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;
    private static final int MAX_VERIFICATION_ATTEMPTS = 3;
    private final Map<String, Integer> failedVerificationAttempts = new HashMap<>();

    @Autowired
    private MailService mailService;

    private String forgotPasswordEmail;
    @Autowired
    private EmailUtil emailUtil;
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    public Optional<User> getUserById(Long id) {
        return userRepository.findById(id);
    }
    public Optional<UserDTO> getUserByUsername(String username) {
        Optional<User> userOptional = userRepository.findByUsername(username);
        return userOptional.map(UserDTO::new);
    }

    @Override
    public User getUserIdByUsername(String username) {
       return  userRepository.findByUsername(username).get();

    }

    public User createUser(User user) {
        return userRepository.save(user);
    }

    public User updateUser(String username, User userDetails) {
        Optional<User> optionalUser = userRepository.findByUsername(username);
        if (optionalUser.isPresent()) {
            User existingUser = optionalUser.get();
            // Mettre à jour les détails de l'utilisateur existant
            existingUser.setFirstName(userDetails.getFirstName());
            existingUser.setLastName(userDetails.getLastName());
            existingUser.setEmail(userDetails.getEmail());
            existingUser.setPhoneNumber(userDetails.getPhoneNumber());
            existingUser.setDateOfBirth(userDetails.getDateOfBirth());
            existingUser.setGender(userDetails.getGender());
            existingUser.setWeight(userDetails.getWeight());
            existingUser.setHeight(userDetails.getHeight());
            existingUser.setProfileImageUrl(userDetails.getProfileImageUrl());


            // Vérifier si un nouveau mot de passe a été fourni
            String newPassword = userDetails.getPassword();
            if (newPassword != null && !newPassword.isEmpty()) {
                // Crypter le nouveau mot de passe
                String encodedPassword = passwordEncoder.encode(newPassword);
                existingUser.setPassword(encodedPassword);
            }

            return userRepository.save(existingUser);
        } else {
            throw new EntityNotFoundException("User not found with username: " + username);
        }
    }

    public void deleteUser(Long id) {
        userRepository.deleteById(id);
    }

    @Override
    public String forgotPassword(String email) {
        userRepository.findByEmail(email)
                .orElseThrow(
                        ()->new RuntimeException("user not found with this email"+email)
                );
        try {
            this.forgotPasswordEmail = email;
            emailUtil.sendVerificationCodeEmail(email);
        } catch (MessagingException e) {
            throw new RuntimeException("enable to send set password email");
        }
        return "please check you email to set password";
    }



    @Override
    public String setPasswordWithVerification( String verificationCode, String newPassword) {
        User user = userRepository.findByEmail( forgotPasswordEmail)
                .orElseThrow(() -> new RuntimeException("User not found with this email: " + forgotPasswordEmail));

        // Vérification du code de vérification (vous devez implémenter cette logique)
        boolean isVerificationCodeValid = verifyVerificationCode(forgotPasswordEmail, verificationCode);

        if (isVerificationCodeValid) {
            // Hacher le nouveau mot de passe
            String hashedPassword = passwordEncoder.encode(newPassword);
            user.setPassword(hashedPassword);

            userRepository.save(user);

            return "New password set successfully. Login with the new password.";
        } else {
            // Enregistrer une tentative de vérification échouée
            recordFailedVerificationAttempt(forgotPasswordEmail);
            if (hasExceededMaxVerificationAttempts(forgotPasswordEmail)) {
                return "You have exceeded the maximum verification attempts. Please contact support.";
            } else {
                return "Invalid verification code. You have " + (MAX_VERIFICATION_ATTEMPTS - getFailedVerificationAttempts(forgotPasswordEmail)) +
                        " attempts remaining.";
            }
        }
    }

    public boolean verifyVerificationCode(String email, String verificationCode) {
        String savedVerificationCode = emailUtil.getSavedVerificationCode(email);
        return savedVerificationCode != null && savedVerificationCode.equals(verificationCode);
    }








    private void recordFailedVerificationAttempt(String email) {
        failedVerificationAttempts.put(email, failedVerificationAttempts.getOrDefault(email, 0) + 1);
    }

    private boolean hasExceededMaxVerificationAttempts(String email) {
        return failedVerificationAttempts.getOrDefault(email, 0) >= MAX_VERIFICATION_ATTEMPTS;
    }

    private int getFailedVerificationAttempts(String email) {
        return failedVerificationAttempts.getOrDefault(email, 0);
    }



    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User Not Found with username: " + username));

        return UserDetailsImpl.build(user);
    }


    public User updateUserAccountStatus(long userId, AccountStatus accountStatus) {
        Optional<User> userOptional = userRepository.findById(userId);
        if (userOptional.isPresent()) {
            User user = userOptional.get();
            user.setAccountStatus(accountStatus);
            return userRepository.save(user);
        } else {
            throw new EntityNotFoundException("User with ID " + userId + " not found.");
        }
    }

    public AccountStatus checkAccountStatus(long userId) {
        Optional<User> userOptional = userRepository.findById(userId);
        if (userOptional.isPresent()) {
            User user = userOptional.get();
            return user.getAccountStatus();
        } else {
            throw new EntityNotFoundException("User with ID " + userId + " not found.");
        }
    }

    public void banUser(String username) {
        updatesUserAccountStatus(username, AccountStatus.BANNED);
    }
    public User updatesUserAccountStatus(String usename, AccountStatus accountStatus) {
        Optional<User> userOptional = userRepository.findByUsername( usename);
        if (userOptional.isPresent()) {
            User user = userOptional.get();
            user.setAccountStatus(accountStatus);
            return userRepository.save(user);
        } else {
            throw new EntityNotFoundException("User with ID " +  usename + " not found.");
        }
    }
    @PersistenceContext
    private EntityManager entityManager;
    public List<User> getUsersByRole(ERole roleName) {
        return entityManager.createQuery(
                        "SELECT DISTINCT u FROM User u JOIN u.roles r WHERE r.name = :roleName", User.class)
                .setParameter("roleName", roleName)
                .getResultList();
    }

    @Override
    public Long getUserIdByUsernames(String username) {
        Optional<User> userOptional = userRepository.findByUsername(username);
        return userOptional.map(User::getUserId).orElse(null); // Retourne l'ID de l'utilisateur s'il est trouvé, sinon null
    }
    public ERole getUserRoleName(Long userId) {
        User user = userRepository.findById(userId).orElse(null);
        if (user != null && user.getRoles() != null && !user.getRoles().isEmpty()) {
            return user.getRoles().iterator().next().getName();
        }
        return null; // or throw exception if needed
    }
    @Override
    public int updateUserBadWordsCount(long userId) {

        User user = userRepository.findById(userId).get();
        user.setBadWordsCount(user.getBadWordsCount()+1);
        if(user.getBadWordsCount()>=5){
            banUser(user.getUsername());
            mailService.sendEmail(user.getEmail(),"Account banned" ,"Your account got banned for typing bad words ");
            userRepository.save(user);
            return 1;
        }
        userRepository.save(user);
        return 2;
    }

}