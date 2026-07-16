package com.taskflow.service;

import com.taskflow.dto.TaskDTO;
import com.taskflow.exception.ResourceNotFoundException;
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


    public List<Task> listar() {

        return repository.findAll();

    }


    public Task guardar(TaskDTO dto) {

        Task tarefa = converter(dto);

        return repository.save(tarefa);

    }


    public Task procurar(Long id) {

        return repository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Tarefa não encontrada")
                );
    }


    public Task atualizar(Long id, Task dados) {

        Task tarefa = procurar(id);

        tarefa.setTitulo(dados.getTitulo());
        tarefa.setDescricao(dados.getDescricao());
        tarefa.setConcluida(dados.isConcluida());
        tarefa.setDataCriacao(dados.getDataCriacao());

        return repository.save(tarefa);

    }


    public void apagar(Long id) {

        Task tarefa = procurar(id);

        repository.delete(tarefa);

    }


    private Task converter(TaskDTO dto) {

        Task tarefa = new Task();

        tarefa.setTitulo(dto.getTitulo());
        tarefa.setDescricao(dto.getDescricao());
        tarefa.setConcluida(dto.isConcluida());
        tarefa.setDataCriacao(dto.getDataCriacao());

        return tarefa;
    }

}