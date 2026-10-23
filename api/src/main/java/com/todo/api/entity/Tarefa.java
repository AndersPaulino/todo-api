package com.todo.api.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import java.time.LocalDateTime;

@Entity
@Table(name = "tb_tarefa", schema = "public")
public class Tarefa {

    @Column(name = "cl_nome_tarefa")
    private String nome;

    @Column(name = "cl_data")
    private LocalDateTime data;

    @Column(name = "cl_descricao_tarefa", nullable = false, length = 255)
    private String descricao;
}
