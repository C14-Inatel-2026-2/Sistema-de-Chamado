package br.inatel.chamados.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import org.junit.jupiter.api.Test;

class LocalTest {

    // TESTE SEM MOCK
    @Test
    void deveCriarLocalComNomeInformado() {
        Local local = new Local("Laboratório 3");

        assertEquals("Laboratório 3", local.getNome());
        assertNull(local.getId());
    }
}