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
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

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

    @Test
    void testFindByAtivo() {
        Tarefa tarefa1 = new Tarefa();
        tarefa1.setAtivo(true);

        List<Tarefa> tarefas = Stream.of(tarefa1)
                .collect(Collectors.toList());

        when(tarefaRepository.findByAtivo(true))
                .thenReturn(tarefas);

        List<Tarefa> resultado = tarefaService.findByAtivo(true);

        assertNotNull(resultado);
        assertEquals(1, resultado.size());
        assertSame(tarefa1, resultado.get(0));
        assertTrue(resultado.get(0).isAtivo());

        verify(tarefaRepository).findByAtivo(true);
    }

    @Test
    void testCadastrarTarefa(){

        when(tarefaRepository.save(tarefa)).thenReturn(tarefa);

        assertDoesNotThrow(() -> tarefaService.cadastrar(tarefa));

        verify(tarefaRepository, times(1)).save(tarefa);
    }

}
