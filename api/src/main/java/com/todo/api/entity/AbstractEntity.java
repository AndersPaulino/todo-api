package com.todo.api.entity;

import jakarta.persistence.*;

import java.time.LocalDate;

@MappedSuperclass
public abstract class AbstractEntity {

    @Id
    @GeneratedValue(strategy =  GenerationType.IDENTITY)
    @Column(name = "cl_id", nullable = false)
    private Long id;

    @Column(name = "cl_ativo", nullable = false)
    private boolean ativo;

    @Column(name = "cl_registro", nullable = false)
    private LocalDate registro;

    @Column(name = "cl_atualizar")
    private LocalDate atualizar;

    @PrePersist
    void prePersist(){
        this.ativo = true;
        this.registro = LocalDate.now();
    }

    @PreUpdate
    void preUpdate(){
        this.atualizar = LocalDate.now();
    }

    public Long getId(){
        return id;
    }

    public boolean isAtivo(){
        return ativo;
    }

    public LocalDate getRegistro(){
        return registro;
    }

    public LocalDate getAtualizar(){
        return atualizar;
    }

    public void setAtivo(boolean ativo){
        this.ativo = ativo;
    }

    public void setRegistro(LocalDate registro){
        this.registro = registro;
    }

    public void setAtualizar(LocalDate atualizar){
        this.atualizar = atualizar;
    }

}
