package com.todo.api.repository;

import com.todo.api.entity.Tarefa;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface TarefaRepository extends JpaRepository<Tarefa, Long> {
    @Query("SELECT e FROM Tarefa e WHERE e.nome = :nome")
    public Tarefa findByNomeTarefa(@Param("nome") String nome);

}
