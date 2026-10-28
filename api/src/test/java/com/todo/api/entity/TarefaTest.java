package com.todo.api.entity;

import net.bytebuddy.asm.Advice;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;


public class TarefaTest {

    private Tarefa tarefa;

    @BeforeEach
    void setUp(){
        tarefa = new Tarefa();

        tarefa.setNome("Tarefa01");
        tarefa.setDia(LocalDate.of(2026,10,10));
        tarefa.setDescricao("Uma descricao");
    }

    @Test
    void testPrePersist(){
        tarefa.prePersist();
        assertNotNull(tarefa.getRegistro(),"Registro não deve ser nulo após o prePersist");
        assertEquals(LocalDate.now(), tarefa.getRegistro(), "Registro deve corresponder à data atual");
        assertTrue(tarefa.isAtivo(), "Ativo deve ser true após prePersist");
    }

    @Test
    void testPreUpdate(){
        LocalDate predate = tarefa.getAtualizar();
        tarefa.preUpdate();
        assertNotNull(tarefa.getAtualizar(),  "Atualizar mão deve ser nulo após o preUpdate");
        assertNotEquals(predate, tarefa.getAtualizar(), "O valor de 'atualziar' deve ser diferente após preUpdate");
    }

    @Test
    void testSettersAndGetters(){
        tarefa.setAtivo(false);
        tarefa.setNome("tarefa-02");
        tarefa.setDia(LocalDate.of(2026,11,11));
        tarefa.setDescricao("Alguma descricao");

        assertFalse(tarefa.isAtivo(), "Ativo deve ser false após o setter");
        assertEquals(tarefa.getNome(), "tarefa-02");
        assertEquals(tarefa.getDia(), LocalDate.of(2026, 11 ,11));
        assertEquals(tarefa.getDescricao(), "Alguma descricao");
    }
}
