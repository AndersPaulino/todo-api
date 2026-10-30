package com.todo.api.controller;

import com.todo.api.entity.Tarefa;
import com.todo.api.service.TarefaService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(TarefaController.class)
public class TarefaControllerTest {

    @Autowired
    private MockMvc mockMvc;

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

}
