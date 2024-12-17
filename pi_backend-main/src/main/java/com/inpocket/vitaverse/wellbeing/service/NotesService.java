package com.inpocket.vitaverse.wellbeing.service;

import com.inpocket.vitaverse.wellbeing.entity.Notes;

import java.util.List;
import java.util.Optional;

public interface NotesService {

    public List<Notes> getAllNotes();
    public Optional<Notes> getNoteById(Long id);

    public Notes saveOrUpdateNotes(Notes notes);
    public void deleteNotes(Long id);

    public Notes addNoteWithUser(Notes notes, Long userId);
    public List<Notes> getNotesByUserId(Long userId);
}
