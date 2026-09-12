package br.inatel.chamados.service;

import br.inatel.chamados.model.Chamado;
import br.inatel.chamados.model.HistoricoChamado;
import br.inatel.chamados.model.Prioridade;
import br.inatel.chamados.model.StatusChamado;
import br.inatel.chamados.repository.HistoricoChamadoRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

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
}