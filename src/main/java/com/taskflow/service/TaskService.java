package com.taskflow.service;

import com.taskflow.dto.TaskDTO;
import com.taskflow.exception.TarefaNaoEncontradaException;
import com.taskflow.model.Task;
import com.taskflow.repository.TaskRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TaskService {

    private final TaskRepository repository;

    public TaskService(TaskRepository repository) {
        this.repository = repository;
    }


    public List<TaskDTO> listar() {

        return repository.findAll()
                .stream()
                .map(this::converterParaDTO)
                .toList();
    }


    public TaskDTO guardar(Task task) {

        Task salva = repository.save(task);

        return converterParaDTO(salva);
    }


    public TaskDTO procurar(Long id) {

        Task tarefa = repository.findById(id)
                .orElseThrow(() ->
                        new RuntimeException("Tarefa não encontrada"));

        return converterParaDTO(tarefa);
    }


    public TaskDTO atualizar(Long id, Task dados) {

        Task tarefa = repository.findById(id)
                .orElseThrow(() ->
                        new RuntimeException("Tarefa não encontrada"));


        tarefa.setTitulo(dados.getTitulo());
        tarefa.setDescricao(dados.getDescricao());
        tarefa.setConcluida(dados.isConcluida());
        tarefa.setDataCriacao(dados.getDataCriacao());


        Task atualizada = repository.save(tarefa);

        return converterParaDTO(atualizada);
    }


    public void apagar(Long id) {

        Task tarefa = repository.findById(id)
                .orElseThrow(() ->
                        new TarefaNaoEncontradaException("Tarefa não encontrada"));

        repository.delete(tarefa);
    }


    private TaskDTO converterParaDTO(Task task) {

        TaskDTO dto = new TaskDTO();

        dto.setId(task.getId());
        dto.setTitulo(task.getTitulo());
        dto.setDescricao(task.getDescricao());
        dto.setConcluida(task.isConcluida());
        dto.setDataCriacao(task.getDataCriacao());

        return dto;
    }
}