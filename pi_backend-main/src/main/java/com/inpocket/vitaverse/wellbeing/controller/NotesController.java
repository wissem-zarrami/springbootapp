package com.inpocket.vitaverse.wellbeing.controller;
import com.inpocket.vitaverse.wellbeing.entity.Notes;
import com.inpocket.vitaverse.wellbeing.service.NotesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping("api/notes")


public class NotesController {


    private final NotesService notesService;

    @Autowired
    public NotesController(NotesService notesService) {
        this.notesService = notesService;
    }

    @PostMapping("/add")
    @ResponseStatus(HttpStatus.CREATED)
    public Notes createNote(@RequestBody Notes note) {
        return notesService.saveOrUpdateNotes(note);
    }

    @PutMapping("/update")
    @ResponseStatus(HttpStatus.OK)
    public Notes updateNote(@RequestBody Notes note) {
        return notesService.saveOrUpdateNotes(note);
    }

    @GetMapping("/all")
    public List<Notes> getAllNotes() {
        return notesService.getAllNotes();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Notes> getNoteById(@PathVariable Long id) {
        Optional<Notes> note = notesService.getNoteById(id);
        return note.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @DeleteMapping("/delete/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteNotes(@PathVariable("id") long id) {
        notesService.deleteNotes(id);
    }

    @PostMapping("/{userId}")
    public ResponseEntity<Notes> addNoteWithUser(@RequestBody Notes note, @PathVariable Long userId) {
        try {
            Notes notes = notesService.addNoteWithUser(note, userId);
            return new ResponseEntity<>(notes, HttpStatus.CREATED);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/usernotes/{userId}")
    public ResponseEntity<List<Notes>> getNotessByUserId(@PathVariable Long userId) {
        List<Notes> notes = notesService.getNotesByUserId(userId);
        if (!notes.isEmpty()) {
            return new ResponseEntity<>(notes, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }


}
