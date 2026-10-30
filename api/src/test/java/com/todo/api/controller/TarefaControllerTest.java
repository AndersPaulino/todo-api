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

}
