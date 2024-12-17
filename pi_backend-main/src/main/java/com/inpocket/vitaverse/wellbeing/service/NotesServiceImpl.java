package com.inpocket.vitaverse.wellbeing.service;

import com.inpocket.vitaverse.wellbeing.entity.Notes;
import com.inpocket.vitaverse.wellbeing.repository.NotesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.inpocket.vitaverse.user.entity.User;
import com.inpocket.vitaverse.user.repository.UserRepository;

import java.util.List;
import java.util.Optional;

@Service

public class NotesServiceImpl implements NotesService {


    @Autowired
    private NotesRepository noteRepository;

    @Autowired
    private UserRepository userRepository;

    @Override
    public List<Notes> getAllNotes() {
        return noteRepository.findAll();
    }

    @Override
    public Optional<Notes> getNoteById(Long id) {
        return noteRepository.findById(id);
    }

    @Override
    public Notes saveOrUpdateNotes(Notes notes) {
        return noteRepository.save(notes);
    }

    @Override
    public void deleteNotes(Long id) {
        noteRepository.deleteById(id);
    }

    @Override
    public Notes addNoteWithUser(Notes notes, Long userId) {
        Optional<User> userOptional = userRepository.findById(userId);
        if (userOptional.isPresent()) {
            User user = userOptional.get();
            notes.getUsers().add(user);
            return noteRepository.save(notes);
        } else {
            // Handle the case where the user with the given ID is not found
            throw new IllegalArgumentException("User not found with ID: " + userId);
        }
    }
    @Override
    public List<Notes> getNotesByUserId(Long userId) {
        return noteRepository.findByUsersUserId(userId);
    }

}
