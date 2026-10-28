package com.todo.api.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Entity
@Table(name = "tb_tarefa", schema = "public")
@Getter @Setter
public class Tarefa extends AbstractEntity{

    @Column(name = "cl_nome_tarefa")
    private String nome;

    @Column(name = "cl_dia_tarefa")
    private LocalDate dia;

    @Column(name = "cl_descricao_tarefa", nullable = false, length = 255)
    private String descricao;
}
