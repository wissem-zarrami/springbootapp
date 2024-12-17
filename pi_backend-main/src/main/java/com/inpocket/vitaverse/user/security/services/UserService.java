package com.inpocket.vitaverse.user.security.services;

import com.inpocket.vitaverse.user.entity.*;

import java.util.List;
import java.util.Optional;
import java.util.Set;

public interface UserService {
    List<User> getAllUsers();
    List<User>getUsersByRole(ERole roleName);
    Optional<User> getUserById(Long id);

    Optional<UserDTO> getUserByUsername(String username);

    User getUserIdByUsername(String username);
    User createUser(User user);

    User updateUser(String username, User userDetails);
    User updateUserAccountStatus(long id, AccountStatus accountStatus);
     User updatesUserAccountStatus(String usename, AccountStatus accountStatus);
    AccountStatus checkAccountStatus(long id);
    void banUser( String username);

    void deleteUser(Long id);

    String forgotPassword(String email);




    String setPasswordWithVerification( String verificationCode, String newPassword);
    int updateUserBadWordsCount(long userId);

    Long getUserIdByUsernames(String username);


    ERole getUserRoleName(Long userId);
}
