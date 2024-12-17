package com.inpocket.vitaverse.user.service;

import com.inpocket.vitaverse.user.entity.User;

import java.util.List;
import java.util.Optional;

public interface DefaultUserService {


    List<User> getAllUsers();
    User getUserById(Long userId);
    User saveUser(User user);
    void deleteUser(Long userId);


    public Optional<User> findById(Long id);
    // Add other methods as needed
}
