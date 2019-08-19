package com.springbackend.spring.repository;

import com.springbackend.spring.model.Notebook;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface NotebookRepository extends JpaRepository<Notebook, UUID> {
}
