package com.todo.api.service;

import com.todo.api.entity.Tarefa;
import com.todo.api.repository.TarefaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
public class TarefaService {

    private TarefaRepository tarefaRepository;

    @Autowired
    public TarefaService (TarefaRepository tarefaRepository){
        this.tarefaRepository = tarefaRepository;
    }
    @Transactional(readOnly = true)
    public Optional<Tarefa> findbyId(Long id){
        return tarefaRepository.findById(id);
    }
}
