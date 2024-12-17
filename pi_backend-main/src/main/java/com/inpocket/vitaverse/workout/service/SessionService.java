package com.inpocket.vitaverse.workout.service;


import com.inpocket.vitaverse.workout.entity.Session;

import java.util.List;
import java.util.Optional;

public interface SessionService {

    List<Session> getAllSessions();

    Optional<Session> getSessionById(Long id);

    Session saveOrUpdateSession(Session session);

    void deleteSession(Long id);

    public Session addSessionWithUser(Session session, Long userId);

    public List<Session> getSessionsByUserId(Long userId);

}
