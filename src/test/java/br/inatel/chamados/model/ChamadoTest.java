package br.inatel.chamados.model;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class ChamadoTest {

    @Test
    void deveCriarChamadoComDadosInformados() { // TESTE SEM MOCK
        Chamado chamado = new Chamado(
                "Computador não liga",
                "Computador do laboratório não está funcionando",
                Prioridade.ALTA
        );

        chamado.setStatus(StatusChamado.ABERTO);

        assertEquals(
                "Computador não liga",
                chamado.getTitulo()
        );

        assertEquals(
                "Computador do laboratório não está funcionando",
                chamado.getDescricao()
        );

        assertEquals(
                Prioridade.ALTA,
                chamado.getPrioridade()
        );

        assertEquals(
                StatusChamado.ABERTO,
                chamado.getStatus()
        );
    }

    @Test
    void naoDeveConsiderarPrioridadeDiferenteComoIgual() { // TESTE SEM MOCK E NEGATIVO
        Chamado chamado = new Chamado(
                "Teclado com defeito",
                "Teclado do laboratório apresenta falhas",
                Prioridade.BAIXA
        );

        assertNotEquals(
                Prioridade.ALTA,
                chamado.getPrioridade()
        );
    }
}