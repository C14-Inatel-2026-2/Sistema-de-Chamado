package br.inatel.chamados.service;

import br.inatel.chamados.model.Chamado;
import br.inatel.chamados.model.Prioridade;
import br.inatel.chamados.repository.ChamadoRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.NoSuchElementException;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ChamadoConsultaServiceTest {

    @Mock
    private ChamadoRepository chamadoRepository;

    @Test
    void deveRetornarChamadoQuandoIdExiste() {
        ChamadoConsultaService service = new ChamadoConsultaService(chamadoRepository);
        Chamado chamado = new Chamado("Rede Wi-Fi instável", "Laboratório 3", Prioridade.ALTA);

        when(chamadoRepository.findById(1L)).thenReturn(Optional.of(chamado));

        Chamado resultado = service.buscarPorId(1L);

        assertEquals("Rede Wi-Fi instável", resultado.getTitulo());
    }

    @Test
    void deveLancarExcecaoQuandoIdNaoExiste() {
        ChamadoConsultaService service = new ChamadoConsultaService(chamadoRepository);

        when(chamadoRepository.findById(99L)).thenReturn(Optional.empty());

        assertThrows(NoSuchElementException.class, () -> service.buscarPorId(99L));
    }
}
