package br.com.fiap.taskapi.controller;

import br.com.fiap.taskapi.dto.CreateTaskDto;
import br.com.fiap.taskapi.dto.UpdateTaskDto;
import br.com.fiap.taskapi.model.Task;
import br.com.fiap.taskapi.repository.TaskRepository;
import br.com.fiap.taskapi.service.TaskService;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriBuilder;
import org.springframework.web.util.UriComponentsBuilder;

import java.awt.*;

@RestController
@RequestMapping("task")
public class TaskController {

    @Autowired
    private TaskService service;

    @Autowired
    private TaskRepository repository;

    @GetMapping
    public ResponseEntity<Page<Task>> index(Pageable pageable) {
        return ResponseEntity.ok(service.listAll(pageable));
    }

    @PostMapping
    public ResponseEntity create(@RequestBody CreateTaskDto data, UriComponentsBuilder builder) {
        Task task = new Task(data);
        service.registration(task);
        var uri = builder.path("/task/{id}").buildAndExpand(task.getId()).toUri();
        return ResponseEntity.created(uri).body(task);
    }

    @PutMapping
    public ResponseEntity update(@RequestBody @Valid UpdateTaskDto data) {
        Task task = service.update(data);
        return ResponseEntity.ok(task);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity delete(@PathVariable Long id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
}
