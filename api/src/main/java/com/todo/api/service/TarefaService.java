package com.todo.api.service;

import com.todo.api.entity.Tarefa;
import com.todo.api.repository.TarefaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
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

    @Transactional(readOnly = true)
    public List<Tarefa> findAll(){
        return tarefaRepository.findAll();
    }

    @Transactional(readOnly = true)
    public Tarefa findByNomeTarefa(String nome){
        return tarefaRepository.findByNomeTarefa(nome);
    }
    @Transactional(readOnly = true)
    public List<Tarefa> findByAtivo(boolean ativo){
        return tarefaRepository.findByAtivo(ativo);
    }

    @Transactional(readOnly = true)
    public List<Tarefa> findByDiaRegistro(LocalDate registro){
        return tarefaRepository.findByDiaRegistro(registro);
    }

    @Transactional(readOnly = true)
    public List<Tarefa> findByDiaAtualizar(LocalDate atualizar){
        return tarefaRepository.findByDiaAtualizar(atualizar);
    }

    @Transactional(rollbackFor = Exception.class)
    public void cadastrar(Tarefa tarefa){
        tarefaRepository.save(tarefa);
    }
}
