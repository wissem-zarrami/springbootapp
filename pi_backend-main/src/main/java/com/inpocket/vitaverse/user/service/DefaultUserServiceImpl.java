package com.inpocket.vitaverse.user.service;

import com.inpocket.vitaverse.user.entity.User;
import com.inpocket.vitaverse.user.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class DefaultUserServiceImpl implements DefaultUserService {

    private final UserRepository userRepository;

    @Autowired
    public DefaultUserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    @Override
    public User getUserById(Long userId) {
        return userRepository.findById(userId).orElse(null);
    }

    @Override
    public User saveUser(User user) {
        return userRepository.save(user);
    }

    @Override
    public void deleteUser(Long userId) {
        userRepository.deleteById(userId);
    }

    // Implement other methods from UserService interface as needed
    public Optional<User> findById(Long id) {
        return userRepository.findById(id);
    }


}

