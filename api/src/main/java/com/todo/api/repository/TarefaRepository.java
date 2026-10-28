package com.todo.api.repository;

import com.todo.api.entity.Tarefa;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface TarefaRepository extends JpaRepository<Tarefa, Long> {
    @Query("SELECT e FROM Tarefa e WHERE e.nome = :nome")
    public Tarefa findByNomeTarefa(@Param("nome") String nome);

    @Query("SELECT e FROM Tarefa e WHERE e.ativo = :ativo")
    public List<Tarefa> findByAtivo(@Param("ativo") boolean ativo);

    @Query("SELECT e From Tarefa e WHERE DATE(e.registro) = :registro")
    public List<Tarefa> findByDiaRegistro(@Param("registro")LocalDate registro);

    @Query("SELECT e FROM Tarefa e WHERE DATE(e.atualizar) = :atualizar")
    public List<Tarefa> findByDiaAtualizar(@Param("atualizar")LocalDate atualizar);
}
