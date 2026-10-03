package br.inatel.chamados.model;

import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;

class HistoricoChamadoTest {

    @Test
    void deveCriarHistoricoComDadosInformados() {
        Chamado chamado = new Chamado(
                "Problema no projetor",
                "Projetor não está funcionando",
                Prioridade.ALTA
        );
        LocalDateTime data = LocalDateTime.of(2026, 10, 2, 14, 30);

        HistoricoChamado historico = new HistoricoChamado(
                chamado,
                StatusChamado.ABERTO,
                StatusChamado.EM_ANDAMENTO,
                data
        );

        assertSame(chamado, historico.getChamado());
        assertEquals(StatusChamado.ABERTO, historico.getStatusAnterior());
        assertEquals(StatusChamado.EM_ANDAMENTO, historico.getStatusNovo());
        assertEquals(data, historico.getData());
    }

    @Test
    void deveAtualizarDadosDoHistoricoPelosSetters() {
        HistoricoChamado historico = new HistoricoChamado();
        Chamado chamado = new Chamado(
                "Problema no projetor",
                "Projetor não está funcionando",
                Prioridade.ALTA
        );
        historico.setChamado(chamado);
        historico.setStatusAnterior(StatusChamado.ABERTO);
        historico.setStatusNovo(StatusChamado.EM_ANDAMENTO);
        historico.setData(LocalDateTime.of(2026, 10, 2, 14, 30));

        Chamado outroChamado = new Chamado(
                "Computador não liga",
                "Computador do laboratório não inicia",
                Prioridade.MEDIA
        );
        LocalDateTime novaData = LocalDateTime.of(2026, 10, 2, 15, 0);

        historico.setChamado(outroChamado);
        historico.setStatusAnterior(StatusChamado.EM_ANDAMENTO);
        historico.setStatusNovo(StatusChamado.RESOLVIDO);
        historico.setData(novaData);

        assertSame(outroChamado, historico.getChamado());
        assertEquals(StatusChamado.EM_ANDAMENTO, historico.getStatusAnterior());
        assertEquals(StatusChamado.RESOLVIDO, historico.getStatusNovo());
        assertEquals(novaData, historico.getData());
    }
}
