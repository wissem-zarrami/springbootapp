package com.inpocket.vitaverse.workout.service;

import com.inpocket.vitaverse.user.entity.User;
import com.inpocket.vitaverse.user.repository.UserRepository;
import com.inpocket.vitaverse.workout.entity.Session;
import com.inpocket.vitaverse.workout.repository.SessionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class SessionServiceImpl implements SessionService {

    @Autowired
    private SessionRepository sessionRepository;

    @Autowired
    private UserRepository userRepository;

    @Override
    public List<Session> getAllSessions() {
        return sessionRepository.findAll();
    }

    @Override
    public Optional<Session> getSessionById(Long id) {
        return sessionRepository.findById(id);
    }

    @Override
    public Session saveOrUpdateSession(Session session) {
        return sessionRepository.save(session);
    }

    @Override
    public void deleteSession(Long id) {
        sessionRepository.deleteById(id);
    }

    @Override
    public Session addSessionWithUser(Session session, Long userId) {
        Optional<User> userOptional = userRepository.findById(userId);
        if (userOptional.isPresent()) {
            User user = userOptional.get();
            session.getUsers().add(user); // Assuming you have a Set<User> users in the Session entity
            return sessionRepository.save(session);
        } else {
            // Handle the case where the user with the given ID is not found
            throw new IllegalArgumentException("User not found with ID: " + userId);
        }
    }
    @Override
    public List<Session> getSessionsByUserId(Long userId) {
        return sessionRepository.findByUsersUserId(userId);
    }
}
