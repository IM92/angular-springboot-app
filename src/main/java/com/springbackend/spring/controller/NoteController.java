package com.springbackend.spring.controller;
import com.springbackend.spring.dto.NoteDTO;
import com.springbackend.spring.mapper.MapperEntity;
import com.springbackend.spring.model.Note;
import com.springbackend.spring.model.Notebook;
import com.springbackend.spring.repository.NoteRepository;
import com.springbackend.spring.repository.NotebookRepository;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import javax.validation.ValidationException;
import javax.persistence.EntityNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/notes")
@CrossOrigin
public class NoteController  {

    private NoteRepository noteRepository;
    private NotebookRepository notebookRepository;
    private MapperEntity mapper;

    public NoteController(NoteRepository noteRepository, NotebookRepository notebookRepository, MapperEntity mapper) {
        this.noteRepository = noteRepository;
        this.notebookRepository = notebookRepository;
        this.mapper = mapper;
    }

    @GetMapping("/all")
    public List<NoteDTO> all() {
        List<Note> notes = this.noteRepository.findAll();

        // map from entity to view model
        List<NoteDTO> notesDTO = notes.stream()
                .map(note -> this.mapper.convertToNoteViewModel(note))
                .collect(Collectors.toList());

        return notesDTO;
    }

    @GetMapping("/byId/{id}")
    public NoteDTO byId(@PathVariable String id) {
        Note note = this.noteRepository.findById(UUID.fromString(id)).orElse(null);
        if (note == null) {
            throw new EntityNotFoundException();
        }
        NoteDTO noteDTO = this.mapper.convertToNoteViewModel(note);

        return noteDTO;
    }

    @GetMapping("/byNotebook/{notebookId}")
    public List<NoteDTO> byNotebook(@PathVariable String notebookId) {
        List<Note> notes = new ArrayList<>();

        Optional<Notebook> notebook = this.notebookRepository.findById(UUID.fromString(notebookId));
        if (notebook.isPresent()) {
            notes = this.noteRepository.findAllByNotebook(notebook.get());
        }
        // map to note view model
        List<NoteDTO> notesDTO = notes.stream()
                .map(note -> this.mapper.convertToNoteViewModel(note))
                .collect(Collectors.toList());
        return notesDTO;
    }

    @PostMapping
    public Note save(@RequestBody NoteDTO noteCreateViewModel, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            throw new ValidationException();
        }
        Note note = this.mapper.convertToNoteEntity(noteCreateViewModel);
        // save note instance to db
        this.noteRepository.save(note);

        return note;
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable String id) {
        this.noteRepository.deleteById(UUID.fromString(id));
    }

}
