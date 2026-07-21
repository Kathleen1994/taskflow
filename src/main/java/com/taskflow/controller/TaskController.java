package com.taskflow.controller;

import com.taskflow.dto.TaskDTO;
import com.taskflow.model.Task;
import com.taskflow.service.TaskService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/tasks")
@CrossOrigin("*")
public class TaskController {

    private final TaskService service;

    public TaskController(TaskService service) {
        this.service = service;
    }


    @PostMapping
    public TaskDTO criar(@Valid @RequestBody Task task) {
        return service.guardar(task);
    }

    @GetMapping
    public List<TaskDTO> listar() {
        return service.listar();
    }


    @GetMapping("/{id}")
    public TaskDTO buscar(@PathVariable("id") Long id) {
        return service.procurar(id);
    }


    @PutMapping("/{id}")
    public TaskDTO atualizar(
            @PathVariable("id") Long id,
            @Valid @RequestBody Task task) {

        return service.atualizar(id, task);
    }


    @DeleteMapping("/{id}")
    public void apagar(@PathVariable("id") Long id) {
        service.apagar(id);
    }
}