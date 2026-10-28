package com.todo.api.service;

import com.todo.api.entity.Tarefa;
import com.todo.api.repository.TarefaRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class TarefaServiceTest {

    @Mock
    private TarefaRepository tarefaRepository;

    private TarefaService tarefaService;

    private Tarefa tarefa;

    @BeforeEach
    void setUp(){
        MockitoAnnotations.initMocks(this);
        tarefaService = new TarefaService(tarefaRepository);
        tarefa = new Tarefa();
        tarefa.setNome("Tarefa teste");
        tarefa.setDia(LocalDate.of(2026,10,10));
        tarefa.setDescricao("Alguma descricao");
    }

    @Test
    void testFindById(){
        Long id = 1L;
        when(tarefaRepository.findById(id)).thenReturn(Optional.empty());

        Optional<Tarefa> tarefaOptional = tarefaService.findbyId(id);

        assertFalse(tarefaOptional.isPresent());
    }
}
