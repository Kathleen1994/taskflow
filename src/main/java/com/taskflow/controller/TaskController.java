package com.taskflow.controller;

import com.taskflow.model.Task;
import com.taskflow.service.TaskService;
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


    @GetMapping
    public List<Task> listar() {
        return service.listar();
    }


    @PostMapping
    public Task criar(@RequestBody Task task) {
        return service.guardar(task);
    }


    @GetMapping("/{id}")
    public Task buscar(@PathVariable("id") Long id) {

        return service.procurar(id);
    }


    @PutMapping("/{id}")
    public Task atualizar(@PathVariable("id") Long id,
                          @RequestBody Task dados) {

        return service.atualizar(id, dados);
    }


    @DeleteMapping("/{id}")
    public void apagar(@PathVariable("id") Long id) {

        service.apagar(id);

    }

}