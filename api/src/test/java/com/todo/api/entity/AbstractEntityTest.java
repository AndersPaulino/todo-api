package com.todo.api.entity;

import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class AbstractEntityTest {

    private static class AbstractEntityTestImpl extends AbstractEntity {

        @PrePersist
        void customPrePersist() {
            super.prePersist();
        }

        @PreUpdate
        void customPreUpdate() {
            super.preUpdate();
        }
    }

    private AbstractEntityTestImpl entity;

    @BeforeEach
    void setUp() {
        entity = new AbstractEntityTestImpl();
    }

    @Test
    void prePersistShouldSetRegistroAndAtivo() {
        entity.prePersist();

        assertNotNull(entity.getRegistro());
        assertTrue(entity.isAtivo());
    }

    @Test
    void preUpdateShouldSetAtualizar() {
        entity.preUpdate();

        assertNotNull(entity.getAtualizar());
    }

    @Test
    void getIdDeveRetornarNuloInicialmente() {
        assertNull(entity.getId());
    }

    @Test
    void setRegistroDeveDefinirDataCorretamente() {
        LocalDate registro = LocalDate.of(2026, 11, 11);

        entity.setRegistro(registro);

        assertEquals(registro, entity.getRegistro());
    }

    @Test
    void setAtualizarDeveDefinirDataCorretamente() {
        LocalDate atualizar = LocalDate.of(2026, 11, 12);

        entity.setAtualizar(atualizar);

        assertEquals(atualizar, entity.getAtualizar());
    }

    @Test
    void prePersistDeveDefinirRegistroEAtivo() {
        entity.prePersist();

        assertNotNull(entity.getRegistro());
        assertEquals(LocalDate.now(), entity.getRegistro());
        assertTrue(entity.isAtivo());
    }

    @Test
    void preUpdateDeveDefinirDataDeAtualizacao() {
        entity.preUpdate();

        assertNotNull(entity.getAtualizar());
        assertEquals(LocalDate.now(), entity.getAtualizar());
    }
}
