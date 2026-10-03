package br.inatel.chamados.service;

import br.inatel.chamados.model.Chamado;
import br.inatel.chamados.model.HistoricoChamado;
import br.inatel.chamados.model.Prioridade;
import br.inatel.chamados.model.StatusChamado;
import br.inatel.chamados.repository.HistoricoChamadoRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.dao.DataAccessResourceFailureException;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class HistoricoChamadoServiceTest {

    @Mock
    private HistoricoChamadoRepository historicoChamadoRepository;

    @InjectMocks
    private HistoricoChamadoService historicoChamadoService;

    @Test
    void deveRegistrarHistoricoDoChamado() {
        Chamado chamado = new Chamado(
                "Problema no projetor",
                "Projetor não está funcionando",
                Prioridade.ALTA
        );

        HistoricoChamado historico = new HistoricoChamado(
                chamado,
                StatusChamado.ABERTO,
                StatusChamado.EM_ANDAMENTO,
                java.time.LocalDateTime.now()
        );

        when(historicoChamadoRepository.save(any(HistoricoChamado.class)))
                .thenReturn(historico);

        HistoricoChamado resultado = historicoChamadoService.registrarHistorico(
                chamado,
                StatusChamado.ABERTO,
                StatusChamado.EM_ANDAMENTO
        );

        assertNotNull(resultado);
        assertEquals(chamado, resultado.getChamado());
        assertEquals(StatusChamado.ABERTO, resultado.getStatusAnterior());
        assertEquals(StatusChamado.EM_ANDAMENTO, resultado.getStatusNovo());

        verify(historicoChamadoRepository, times(1))
                .save(any(HistoricoChamado.class));
    }

    @Test
    void deveEnviarDadosCorretosParaSalvarHistorico() {
        Chamado chamado = new Chamado(
                "Problema no projetor",
                "Projetor não está funcionando",
                Prioridade.ALTA
        );
        LocalDateTime antesDoRegistro = LocalDateTime.now();

        historicoChamadoService.registrarHistorico(
                chamado,
                StatusChamado.ABERTO,
                StatusChamado.EM_ANDAMENTO
        );

        LocalDateTime depoisDoRegistro = LocalDateTime.now();
        ArgumentCaptor<HistoricoChamado> captor =
                ArgumentCaptor.forClass(HistoricoChamado.class);
        verify(historicoChamadoRepository).save(captor.capture());

        HistoricoChamado historicoEnviado = captor.getValue();
        assertSame(chamado, historicoEnviado.getChamado());
        assertEquals(StatusChamado.ABERTO, historicoEnviado.getStatusAnterior());
        assertEquals(StatusChamado.EM_ANDAMENTO, historicoEnviado.getStatusNovo());
        assertNotNull(historicoEnviado.getData());
        assertFalse(historicoEnviado.getData().isBefore(antesDoRegistro));
        assertFalse(historicoEnviado.getData().isAfter(depoisDoRegistro));
    }

    @Test
    void devePropagarExcecaoQuandoSalvarHistoricoFalhar() {
        Chamado chamado = new Chamado(
                "Problema no projetor",
                "Projetor não está funcionando",
                Prioridade.ALTA
        );
        DataAccessResourceFailureException falha =
                new DataAccessResourceFailureException("Falha ao salvar o histórico");
        when(historicoChamadoRepository.save(any(HistoricoChamado.class)))
                .thenThrow(falha);

        DataAccessResourceFailureException excecao = assertThrows(
                DataAccessResourceFailureException.class,
                () -> historicoChamadoService.registrarHistorico(
                        chamado,
                        StatusChamado.ABERTO,
                        StatusChamado.EM_ANDAMENTO
                )
        );

        assertSame(falha, excecao);
        verify(historicoChamadoRepository).save(any(HistoricoChamado.class));
    }
}
