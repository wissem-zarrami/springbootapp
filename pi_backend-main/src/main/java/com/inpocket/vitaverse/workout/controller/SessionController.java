package com.inpocket.vitaverse.workout.controller;



import com.inpocket.vitaverse.workout.entity.Session;
import com.inpocket.vitaverse.workout.service.SessionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping("api/sessions")
public class SessionController {

    private final SessionService sessionService;

    @Autowired
    public SessionController(SessionService sessionService) {
        this.sessionService = sessionService;
    }

    @PostMapping("/add")
    @ResponseStatus(HttpStatus.CREATED)
    public Session createSession(@RequestBody Session session) {
        return sessionService.saveOrUpdateSession(session);
    }

    @PutMapping("/update")
    @ResponseStatus(HttpStatus.OK)
    public Session updateSession(@RequestBody Session session) {
        return sessionService.saveOrUpdateSession(session);
    }

    @GetMapping("/all")
    public List<Session> getAllSessions() {
        return sessionService.getAllSessions();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Session> getSessionById(@PathVariable Long id) {
        Optional<Session> session = sessionService.getSessionById(id);
        return session.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @DeleteMapping("/delete/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteSession(@PathVariable("id") long id) {
        sessionService.deleteSession(id);
    }

    // POST endpoint to add a session and associate it with a user
    @PostMapping("/{userId}")
    public ResponseEntity<Session> addSessionWithUser(@RequestBody Session session, @PathVariable Long userId) {
        try {
            Session savedSession = sessionService.addSessionWithUser(session, userId);
            return new ResponseEntity<>(savedSession, HttpStatus.CREATED);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/usersession/{userId}")
    public ResponseEntity<List<Session>> getSessionsByUserId(@PathVariable Long userId) {
        List<Session> sessions = sessionService.getSessionsByUserId(userId);
        if (!sessions.isEmpty()) {
            return new ResponseEntity<>(sessions, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}
