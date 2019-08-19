package com.springbackend.spring.controller;

import com.springbackend.spring.dto.NotebookDTO;
import com.springbackend.spring.mapper.MapperEntity;
import com.springbackend.spring.model.Notebook;
import com.springbackend.spring.repository.NotebookRepository;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.ValidationException;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/notebooks")
@CrossOrigin
public class NotebookController {

    private NotebookRepository notebookRepository;
    private MapperEntity mapper;

    public NotebookController(NotebookRepository notebookRepository, MapperEntity mapper){
        this.notebookRepository = notebookRepository;
        this.mapper = mapper;
    }

    @GetMapping("/all")
    public List<Notebook> all(){
        List<Notebook> allCategories = this.notebookRepository.findAll();
        return allCategories;
    }
    @PostMapping
    public Notebook save(@RequestBody NotebookDTO notebookViewModel, BindingResult result){
        if (result.hasErrors()) {
            throw new ValidationException();
        }

        Notebook notebookEntity = this.mapper.convertToNotebookEntity(notebookViewModel);

        // save notebookEntity instance to db
        this.notebookRepository.save(notebookEntity);

        return notebookEntity;
    }
    @DeleteMapping("/{id}")
    public void delete(@PathVariable String id) {
        this.notebookRepository.deleteById(UUID.fromString(id));
    }
}
