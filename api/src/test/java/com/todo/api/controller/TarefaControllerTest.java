package com.todo.api.controller;

import com.todo.api.entity.Tarefa;
import com.todo.api.service.TarefaService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import tools.jackson.databind.ObjectMapper;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;

@WebMvcTest(TarefaController.class)
public class TarefaControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockitoBean
    private TarefaService tarefaService;

    @Test
    void testFindByIdRetornoTarefa() throws Exception {
        Tarefa tarefa = new Tarefa();
        Long id = 1L;

        when(tarefaService.findbyId(id)).thenReturn(Optional.of(tarefa));

        mockMvc.perform(get("/api/tarefa/{id}", id))
                .andExpect(status().isOk());
    }

    @Test
    void testFindByIdNotFound() throws Exception {
        Long id = 1L;

        when(tarefaService.findbyId(id)).thenReturn(Optional.empty());

        mockMvc.perform(get("/api/tarefa/{id}", id))
                .andExpect(status().isNotFound());
    }

    @Test
    void testFindAllRetornoListaDeTarefas() throws Exception {
        Tarefa tarefa = new Tarefa();
        Tarefa tarefa1 = new Tarefa();
        Tarefa tarefa2 = new Tarefa();

        when(tarefaService.findAll()).thenReturn(List.of(tarefa, tarefa1, tarefa2));

        mockMvc.perform(get("/api/tarefa"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$.length()").value(3));
    }

    @Test
    void testFindAllRetornoListaVazia() throws Exception {
        when(tarefaService.findAll()).thenReturn(List.of());

        mockMvc.perform(get("/api/tarefa")
                .contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isOk())
        .andExpect(content().contentTypeCompatibleWith(
                MediaType.APPLICATION_JSON))
        .andExpect(jsonPath("$").isArray())
        .andExpect(jsonPath("$.length()").value(0));
    }

    @Test
    void testFindByNomeTarefaRetornoTarefa() throws Exception {
        Tarefa tarefa = new Tarefa();
        String nome = "Tarefa-01";

        when(tarefaService.findByNomeTarefa(nome)).thenReturn(tarefa);

        mockMvc.perform(get("/api/tarefa/nome/{nome}", nome))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$").exists());
    }

    @Test
    void testFindByNomeTarefaNotFound() throws Exception {

        String nome = "Tarefa-01";

        when(tarefaService.findByNomeTarefa(nome)).thenReturn(null);

        mockMvc.perform(get("/api/tarefa/nome/{nome}", nome))
                .andExpect(status().isNotFound());
    }

    @Test
    void testFindByNomeTarefaInternalServerError() throws Exception {

        String nome = "Tarefa-01";

        when(tarefaService.findByNomeTarefa(nome)).thenThrow(new RuntimeException("Erro interno"));

        mockMvc.perform(get("/api/tarefa/nome/{nome}", nome))
                .andExpect(status().isInternalServerError());
    }

    @Test
    void testFindByAtivoRetornoListaTarefas() throws Exception {
        Tarefa tarefa = new Tarefa();

        boolean ativo = true;

        when(tarefaService.findByAtivo(ativo)).thenReturn(List.of(tarefa));

        mockMvc.perform(get("/api/tarefa/ativo/{ativo}", ativo))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$").exists());
    }

    @Test
    void testFindByAtivoNotFound() throws Exception {
        Tarefa tarefa = new Tarefa();

        boolean ativo = true;

        when(tarefaService.findByAtivo(ativo)).thenReturn(List.of());

        mockMvc.perform(get("/api/tarefa/ativo/{ativo}", ativo))
                .andExpect(status().isNotFound());
    }

    @Test
    void testFindByAtivoInternalServerError() throws Exception {
        Tarefa tarefa = new Tarefa();

        boolean ativo = true;

        when(tarefaService.findByAtivo(ativo)).thenThrow(new RuntimeException("Erro interno"));

        mockMvc.perform(get("/api/tarefa/ativo/{ativo}", ativo))
                .andExpect(status().isInternalServerError());
    }

    @Test
    void testFindByDiaRegistroRetornoListaTarefas() throws Exception{
        Tarefa tarefa = new Tarefa();
        LocalDate registro = LocalDate.of(2026,10,10);

        when(tarefaService.findByDiaRegistro(registro)).thenReturn(List.of(tarefa));

        mockMvc.perform(get("/api/tarefa/registro/{registro}",registro))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$").exists());
    }

    @Test
    void testFindByDiaRegistroNotFound()  throws Exception {
        Tarefa tarefa = new Tarefa();
        LocalDate registro = LocalDate.of(2026,10,10);

        when(tarefaService.findByDiaRegistro(registro)).thenReturn(List.of());

        mockMvc.perform(get("/api/tarefa/registro/{registro}", registro))
                .andExpect(status().isNotFound());
    }

    @Test
    void testFindByDiaRegistroInternalServerError() throws Exception {
        Tarefa tarefa = new Tarefa();
        LocalDate registro = LocalDate.of(2026,10,10);

        when(tarefaService.findByDiaRegistro(registro)).thenThrow(new RuntimeException("Erro interno"));

        mockMvc.perform(get("/api/tarefa/registro/{registro}",registro))
                .andExpect(status().isInternalServerError());
    }

    @Test
    void testFindByDiaAtualizarRetornoListaTarefa() throws Exception {
        Tarefa tarefa = new Tarefa();
        LocalDate atualizar = LocalDate.of(2026,10,10);

        when(tarefaService.findByDiaAtualizar(atualizar)).thenReturn(List.of(tarefa));

        mockMvc.perform(get("/api/tarefa/atualizar/{atualizar}", atualizar))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$").exists());
    }

    @Test
    void testFindByDiaAtualizarNotFound() throws Exception {
        Tarefa tarefa = new Tarefa();
        LocalDate atualizar = LocalDate.of(2026,10,10);

        when(tarefaService.findByDiaAtualizar(atualizar)).thenReturn(List.of());

        mockMvc.perform(get("/api/tarefa/atualizar/{atualizar}", atualizar))
                .andExpect(status().isNotFound());
    }

    @Test
    void testFindByDiaAtualizarInternalServerError() throws Exception {
        Tarefa tarefa = new Tarefa();
        LocalDate atualizar = LocalDate.of(2026,10,10);

        when(tarefaService.findByDiaAtualizar(atualizar)).thenThrow(new RuntimeException("Erro interno"));

        mockMvc.perform(get("/api/tarefa/atualizar/{atualizar}",atualizar))
                .andExpect(status().isInternalServerError());
    }

    @Test
    void testCadastrarRetornoSucesso() throws Exception {
        Tarefa tarefa = new Tarefa();
        tarefa.setNome("Tarefa-01");

        doNothing().when(tarefaService).cadastrar(any(Tarefa.class));

        mockMvc.perform(post("/api/tarefa")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(tarefa)))
                .andExpect(status().isOk())
                .andExpect(content().string("Registro cadastrado com sucesso!"));

        verify(tarefaService, times(1)).cadastrar(any(Tarefa.class));
    }

    @Test
    void testCadastrarBadRequest() throws Exception {
        Tarefa tarefa = new Tarefa();
        tarefa.setNome("Tarefa-01");

        doThrow(new IllegalArgumentException("Nome da tarefa inválido!"))
                .when(tarefaService).cadastrar(any(Tarefa.class));

        mockMvc.perform(post("/api/tarefa")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(tarefa)))
                .andExpect(status().isBadRequest())
                .andExpect(content().string("Nome da tarefa inválido!"));
    }

    @Test
    void testAtualizarRetornoSucesso() throws Exception {
        Long id = 1L;

        Tarefa tarefa = new Tarefa();
        tarefa.setNome("Tarefa-01");

        doNothing().when(tarefaService).atualizar(eq(id), any(Tarefa.class));

        mockMvc.perform(put("/api/tarefa/atualizar/{id}", id)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(tarefa)))
                .andExpect(status().isOk())
                .andExpect(content().string("Registro atualizado com sucesso!"));

        verify(tarefaService, times(1))
                .atualizar(eq(id), any(Tarefa.class));
    }
}
