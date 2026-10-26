package com.todo.api.controller;

import com.todo.api.entity.Tarefa;
import com.todo.api.service.TarefaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/tarefa")
public class TarefaController {
    private TarefaService tarefaService;

    @Autowired
    public TarefaController (TarefaService tarefaService){
        this.tarefaService = tarefaService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<Tarefa> findByID(@PathVariable Long id){
        return tarefaService.findbyId(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping
    public ResponseEntity<List<Tarefa>> findAll() {
        List<Tarefa> tarefas = tarefaService.findAll();
        return ResponseEntity.ok(tarefas);
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
