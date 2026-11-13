package br.inatel.chamados.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;
import org.junit.jupiter.api.Test;

class EquipamentoTest {

    // TESTE SEM MOCK
    @Test
    void deveAssociarEquipamentoAUmLocal() {
        Local local = new Local("Sala 101");
        Equipamento equipamento = new Equipamento();

        equipamento.setNome("Projetor");
        equipamento.setMarca("Epson");
        equipamento.setNumeroSerie("SN123");
        equipamento.setLocal(local);

        assertEquals("Projetor", equipamento.getNome());
        assertEquals("Epson", equipamento.getMarca());
        assertEquals("SN123", equipamento.getNumeroSerie());
        assertSame(local, equipamento.getLocal());
        assertEquals("Sala 101", equipamento.getLocal().getNome());
    }
}