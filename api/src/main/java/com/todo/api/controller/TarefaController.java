package com.todo.api.controller;

import com.todo.api.entity.Tarefa;
import com.todo.api.service.TarefaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
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

    @GetMapping("/nome/{nome}")
    public ResponseEntity<Tarefa> findByNomeTarefa(@PathVariable String nome){
        try {
            Tarefa tarefa = tarefaService.findByNomeTarefa(nome);

            if (tarefa != null) {
                return ResponseEntity.ok(tarefa);
            } else {
                return ResponseEntity.notFound().build();
            }
        } catch (Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @GetMapping("/ativo/{ativo}")
    public ResponseEntity<List<Tarefa>> findByAtivo(@PathVariable boolean ativo){
        try {
            List<Tarefa> tarefas = tarefaService.findByAtivo(ativo);
            if (!tarefas.isEmpty()){
                return ResponseEntity.ok(tarefas);
            } else {
                return ResponseEntity.notFound().build();
            }
        } catch (Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @GetMapping("/registro/{registro}")
    public ResponseEntity<List<Tarefa>> findByDiaRegistro(@PathVariable("registro") LocalDate registro) {
        try {
            List<Tarefa> tarefas = tarefaService.findByDiaRegistro(registro);

            if (!tarefas.isEmpty()) {
                return ResponseEntity.ok(tarefas);
            } else {
                return ResponseEntity.notFound().build();
            }
        } catch (Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @GetMapping("/atualizar/{atualizar}")
    public ResponseEntity<List<Tarefa>> findByDiaAtualizar(@PathVariable("atualizar") LocalDate atualizar){
        try {
            List<Tarefa> tarefas = tarefaService.findByDiaAtualizar(atualizar);
            if (!tarefas.isEmpty()) {
                return ResponseEntity.ok(tarefas);
            } else {
                return ResponseEntity.notFound().build();
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
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

    @PutMapping("/atualizar/{id}")
    public ResponseEntity<String> atualizar(@PathVariable Long id, @RequestBody Tarefa tarefa) {
        try {
            tarefaService.atualizar(id, tarefa);
            return ResponseEntity.ok().body("Registro atualizado com sucesso!");
        } catch (IllegalArgumentException e){
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Erro ao atualizar o registro!");
        }
    }

}
