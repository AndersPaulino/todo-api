package com.todo.api.controller;

import com.todo.api.entity.Tarefa;
import com.todo.api.service.TarefaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/tarefa")
public class TarefaController {
    private TarefaService tarefaService

    @Autowired
    public TarefaController (TarefaService tarefaService){
        this.tarefaService = tarefaService;
    }

    @PostMapping
    public ResponseEntity<String> cadastrar (@RequestBody Tarefa tarefa){
        try {
            tarefaService.cadastrar(tarefa);
            return ResponseEntity.ok().body("Registro cadastrado com sucesso!");
        } catch (IllegalArgumentException e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
