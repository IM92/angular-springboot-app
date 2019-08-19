package com.springbackend.spring.mapper;

import com.springbackend.spring.dto.NoteDTO;
import com.springbackend.spring.dto.NotebookDTO;
import com.springbackend.spring.model.Note;
import com.springbackend.spring.model.Notebook;
import com.springbackend.spring.repository.NotebookRepository;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class MapperEntity {
    private NotebookRepository notebookRepository;

    public MapperEntity(NotebookRepository notebookRepository) {
        this.notebookRepository = notebookRepository;
    }

    public NoteDTO convertToNoteViewModel(Note entity) {
        NoteDTO noteDTO = new NoteDTO();
        noteDTO.setTitle(entity.getTitle());
        noteDTO.setId(entity.getId().toString());
        noteDTO.setLastModifiedOn(entity.getLastModifiedOn());
        noteDTO.setText(entity.getText());
        noteDTO.setNotebookId(entity.getNotebook().getId().toString());

        return noteDTO;
    }

    public Note convertToNoteEntity(NoteDTO viewModel) {
        Notebook notebook = this.notebookRepository.findById(UUID.fromString(viewModel.getNotebookId())).get();
        Note entity = new Note(viewModel.getId(), viewModel.getTitle(), viewModel.getText(), notebook);

        return entity;
    }

    public NotebookDTO convertToNotebookViewModel(Notebook entity) {
        NotebookDTO notebookDTO = new NotebookDTO();
        notebookDTO.setId(entity.getId().toString());
        notebookDTO.setName(entity.getName());
        notebookDTO.setNbNotes(entity.getNotes().size());

        return notebookDTO;
    }

    public Notebook convertToNotebookEntity(NotebookDTO viewModel) {
        Notebook entity = new Notebook(viewModel.getId(), viewModel.getName());
        return entity;
    }
}
