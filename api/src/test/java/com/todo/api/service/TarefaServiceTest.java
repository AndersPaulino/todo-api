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
    void testFindAll() {
        Tarefa tarefa1 = new Tarefa();
        tarefa1.setNome("Tarefa01");

        Tarefa tarefa2 = new Tarefa();
        tarefa2.setNome("Tarefa02");

        List<Tarefa> tarefas = List.of(tarefa1, tarefa2);

        when(tarefaRepository.findAll()).thenReturn(tarefas);

        List<Tarefa> resultado = tarefaService.findAll();

        assertNotNull(resultado);
        assertEquals(2, resultado.size());
        assertEquals("Tarefa01", resultado.get(0).getNome());
        assertEquals("Tarefa02", resultado.get(1).getNome());

        verify(tarefaRepository, times(1)).findAll();
    }

    @Test
    void testFindByNomeTarefa() {
        Tarefa tarefa = new Tarefa();
        tarefa.setNome("Tarefa01");

        when(tarefaRepository.findByNomeTarefa("Tarefa01"))
                .thenReturn(tarefa);

        Tarefa resultado = tarefaService.findByNomeTarefa("Tarefa01");

        assertNotNull(resultado);
        assertEquals("Tarefa01", resultado.getNome());

        verify(tarefaRepository, times(1))
                .findByNomeTarefa("Tarefa01");
    }

    @Test
    void testFindByDiaRegistro() {
        LocalDate registro = LocalDate.of(2026, 10, 28);

        Tarefa tarefa = new Tarefa();
        tarefa.setNome("Tarefa01");
        tarefa.setRegistro(registro);

        List<Tarefa> tarefas = List.of(tarefa);

        when(tarefaRepository.findByDiaRegistro(registro))
                .thenReturn(tarefas);

        List<Tarefa> resultado =
                tarefaService.findByDiaRegistro(registro);

        assertNotNull(resultado);
        assertEquals(1, resultado.size());
        assertEquals(registro, resultado.get(0).getRegistro());

        verify(tarefaRepository, times(1))
                .findByDiaRegistro(registro);
    }

    @Test
    void testFindByDiaAtualizar() {
        LocalDate atualizar = LocalDate.of(2026, 10, 28);

        Tarefa tarefa = new Tarefa();
        tarefa.setNome("Tarefa01");
        tarefa.setAtualizar(atualizar);

        List<Tarefa> tarefas = List.of(tarefa);

        when(tarefaRepository.findByDiaAtualizar(atualizar))
                .thenReturn(tarefas);

        List<Tarefa> resultado =
                tarefaService.findByDiaAtualizar(atualizar);

        assertNotNull(resultado);
        assertEquals(1, resultado.size());
        assertEquals(atualizar, resultado.get(0).getAtualizar());

        verify(tarefaRepository, times(1))
                .findByDiaAtualizar(atualizar);
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

    @Test
    void testAtualizarTarefa() {
        Tarefa tarefaExistente = new Tarefa();
        tarefaExistente.setNome("Tarefa original");
        tarefaExistente.setDia(LocalDate.of(2026,10,10));
        tarefaExistente.setDescricao("Descricao origial");

        Tarefa tarefaAtualizada = new Tarefa();
        tarefaAtualizada.setNome("Tarefa atualizada");
        tarefaAtualizada.setDia(LocalDate.of(2026,10,11));
        tarefaAtualizada.setDescricao("Descricao atualizada");

        when(tarefaRepository.findById(1L))
                .thenReturn(Optional.of(tarefaExistente));

        when(tarefaRepository.save(any(Tarefa.class)))
                .thenAnswer(invocation -> invocation.getArgument(0));

        assertDoesNotThrow(
                () -> tarefaService.atualizar(1L, tarefaAtualizada)
        );

        assertEquals("Tarefa atualizada", tarefaExistente.getNome());
        assertEquals("Descricao atualizada", tarefaExistente.getDescricao());
        assertEquals(LocalDate.of(2026,10,11), tarefaExistente.getDia());

        verify(tarefaRepository, times(1)).findById(1L);
        verify(tarefaRepository, times(1)).save(tarefaExistente);
    }

    @Test
    void testAtualizarTarefaIdInvalido() {
        Long idInvalido = 0L;

        Tarefa tarefa = new Tarefa();
        tarefa.setNome("Tarefa atualizada");

        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> tarefaService.atualizar(idInvalido, tarefa)
        );

        assertEquals(
                "ID de tarefa inválido!",
                exception.getMessage()
        );

        verify(tarefaRepository, never()).findById(anyLong());
        verify(tarefaRepository, never()).save(any(Tarefa.class));
    }

    @Test
    void testDeletar(){
        when(tarefaRepository.findById(1L)).thenReturn(Optional.of(tarefa));

        assertDoesNotThrow(()-> tarefaService.deletar(1L));

        assertFalse(tarefa.isAtivo());
    }

    @Test
    void testDeletarThrowExeceptionIdDoesNotExist(){
        when(tarefaRepository.findById(2L)).thenReturn(Optional.empty());

        IllegalArgumentException e = assertThrows(IllegalArgumentException.class, ()-> tarefaService.deletar(2L));

        assertEquals("ID de tarefa inválido!", e.getMessage());
    }
}
