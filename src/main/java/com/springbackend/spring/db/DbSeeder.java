package com.springbackend.spring.db;

import com.springbackend.spring.model.Note;
import com.springbackend.spring.model.Notebook;
import com.springbackend.spring.repository.NoteRepository;
import com.springbackend.spring.repository.NotebookRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;

@Component
@ConditionalOnProperty(name ="springBackend.db.recreate", havingValue = "true")
public class DbSeeder implements CommandLineRunner {

   private NotebookRepository notebookRepository;
   private NoteRepository noteRepository;

   public DbSeeder(NoteRepository noteRepository,
                    NotebookRepository notebookRepository){
       this.notebookRepository = notebookRepository;
       this.noteRepository = noteRepository;
   }

    @Override
    public void run(String... args) throws Exception {
        //Remove all existing entities
        this.notebookRepository.deleteAll();
        this.noteRepository.deleteAll();

        // Save a default notebook
        Notebook defaultNotebook = new Notebook("Default");
        this.notebookRepository.save(defaultNotebook);

        Notebook quatesNotebook = new Notebook("Quotes");
        this.notebookRepository.save(quatesNotebook);


        //Save the welcome note
        Note note = new Note("Hello","Welcome to Note it", defaultNotebook);
        this.noteRepository.save(note);

        System.out.println("Initialized database!");
    }


}
