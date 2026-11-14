package br.inatel.chamados.controller;

import br.inatel.chamados.model.Chamado;
import br.inatel.chamados.model.Prioridade;
import br.inatel.chamados.service.ChamadoConsultaService;
import br.inatel.chamados.service.ChamadoService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.NoSuchElementException;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ChamadoControllerTest {

    @Mock
    private ChamadoService chamadoService;

    @Mock
    private ChamadoConsultaService consultaService;

    @InjectMocks
    private ChamadoController controller;

    @Test
    void deveRetornar201AoAbrirChamadoValido() {
        Chamado chamado = new Chamado("Ar-condicionado", "Não gela", Prioridade.ALTA);
        when(chamadoService.abrirChamado(chamado)).thenReturn(chamado);

        ResponseEntity<Chamado> resposta = controller.abrir(chamado);

        assertEquals(HttpStatus.CREATED, resposta.getStatusCode());
        assertSame(chamado, resposta.getBody());
    }

    @Test
    void naoDeveAbrirChamadoSemPrioridade() {
        Chamado chamado = new Chamado("Ar-condicionado", "Não gela", null);

        assertThrows(IllegalArgumentException.class, () -> controller.abrir(chamado));
        verify(chamadoService, never()).abrirChamado(any());
    }

    @Test
    void deveListarChamados() {
        List<Chamado> lista = List.of(new Chamado("A", "B", Prioridade.BAIXA));
        when(consultaService.listarTodos()).thenReturn(lista);

        assertEquals(1, controller.listar().size());
    }

    @Test
    void deveBuscarChamadoPorId() {
        Chamado chamado = new Chamado("A", "B", Prioridade.MEDIA);
        when(consultaService.buscarPorId(1L)).thenReturn(chamado);

        assertSame(chamado, controller.buscarPorId(1L));
    }

    @Test
    void deveRetornar404QuandoChamadoNaoExiste() {
        ResponseEntity<String> resposta =
                controller.tratarNaoEncontrado(new NoSuchElementException("Chamado não encontrado com id: 9"));

        assertEquals(HttpStatus.NOT_FOUND, resposta.getStatusCode());
        assertEquals("Chamado não encontrado com id: 9", resposta.getBody());
    }

    @Test
    void deveRetornar400ParaDadosInvalidos() {
        ResponseEntity<String> resposta =
                controller.tratarRequisicaoInvalida(new IllegalArgumentException("Título é obrigatório"));

        assertEquals(HttpStatus.BAD_REQUEST, resposta.getStatusCode());
    }

    @Test
    void deveRetornar400QuandoVinculoNaoExiste() {
        ResponseEntity<String> resposta =
                controller.tratarVinculoInvalido(new DataIntegrityViolationException("FK violada"));

        assertEquals(HttpStatus.BAD_REQUEST, resposta.getStatusCode());
        assertTrue(resposta.getBody().contains("solicitante"));
    }
}
