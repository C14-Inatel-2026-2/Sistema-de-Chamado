package br.inatel.chamados.service;

import br.inatel.chamados.model.Chamado;
import br.inatel.chamados.model.Prioridade;
import br.inatel.chamados.model.StatusChamado;
import br.inatel.chamados.model.Usuario;
import br.inatel.chamados.repository.ChamadoRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ChamadoServiceTest {

    @Mock
    private ChamadoRepository chamadoRepository;

    private ChamadoService chamadoService;

    private Chamado chamado;
    private Usuario tecnico;

    @BeforeEach
    void setUp() {
        chamadoService = new ChamadoService(chamadoRepository);

        chamado = new Chamado(
                "Computador não liga",
                "Computador do laboratório não está ligando",
                Prioridade.ALTA
        );

        chamado.setStatus(StatusChamado.ABERTO);

        tecnico = new Usuario(
                null,
                "João Técnico",
                "joao@inatel.br",
                "123456",
                "TECNICO"
        );
    }

    // TESTES COM MOCK
    @Test
    void deveAtribuirUsuarioComPerfilTecnico() {
        when(chamadoRepository.findById(1L))
                .thenReturn(Optional.of(chamado));

        when(chamadoRepository.save(chamado))
                .thenReturn(chamado);

        Chamado resultado = chamadoService.atribuirTecnico(1L, tecnico);

        assertEquals(tecnico, resultado.getTecnico());

        verify(chamadoRepository).save(chamado);
    }

    @Test
    void naoDeveAtribuirUsuarioQueNaoEhTecnico() {
        Usuario solicitante = new Usuario(
                null,
                "João Solicitante",
                "joao@inatel.br",
                "123456",
                "SOLICITANTE"
        );

        when(chamadoRepository.findById(1L))
                .thenReturn(Optional.of(chamado));

        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> chamadoService.atribuirTecnico(1L, solicitante)
        );

        assertEquals(
                "O usuário selecionado não possui perfil de técnico",
                exception.getMessage()
        );

        verify(chamadoRepository, never()).save(any());
    }

    @Test
    void deveAlterarStatusDeAbertoParaEmAndamento() {
        when(chamadoRepository.findById(1L))
                .thenReturn(Optional.of(chamado));

        when(chamadoRepository.save(chamado))
                .thenReturn(chamado);

        Chamado resultado = chamadoService.atualizarStatus(
                1L,
                StatusChamado.EM_ANDAMENTO
        );

        assertEquals(
                StatusChamado.EM_ANDAMENTO,
                resultado.getStatus()
        );

        verify(chamadoRepository).save(chamado);
    }

    @Test
    void deveAlterarStatusDeEmAndamentoParaResolvido() {
        chamado.setStatus(StatusChamado.EM_ANDAMENTO);

        when(chamadoRepository.findById(1L))
                .thenReturn(Optional.of(chamado));

        when(chamadoRepository.save(chamado))
                .thenReturn(chamado);

        Chamado resultado = chamadoService.atualizarStatus(
                1L,
                StatusChamado.RESOLVIDO
        );

        assertEquals(
                StatusChamado.RESOLVIDO,
                resultado.getStatus()
        );
    }

    @Test
    void naoDevePermitirPularStatus() {
        when(chamadoRepository.findById(1L))
                .thenReturn(Optional.of(chamado));

        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> chamadoService.atualizarStatus(
                        1L,
                        StatusChamado.FECHADO
                )
        );

        assertTrue(
                exception.getMessage()
                        .contains("Transição de status não permitida")
        );

        verify(chamadoRepository, never()).save(any());
    }

    @Test
    void deveRecusarAtualizacaoDeStatusDeChamadoInexistente() {
        when(chamadoRepository.findById(99L))
                .thenReturn(Optional.empty());

        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> chamadoService.atualizarStatus(
                        99L,
                        StatusChamado.EM_ANDAMENTO
                )
        );

        assertEquals(
                "Chamado não encontrado",
                exception.getMessage()
        );

        verify(chamadoRepository, never()).save(any());
    }

    @Test
    void deveAbrirChamadoComStatusAberto() {
        when(chamadoRepository.save(any(Chamado.class)))
                .thenAnswer(invocation -> invocation.getArgument(0));

        Chamado novoChamado = new Chamado(
                "Ar condicionado com problema",
                "O ar condicionado não está funcionando",
                Prioridade.MEDIA
        );

        Chamado resultado = chamadoService.abrirChamado(novoChamado);

        assertEquals(
                StatusChamado.ABERTO,
                resultado.getStatus()
        );

        assertNotNull(resultado.getDataAbertura());

        verify(chamadoRepository).save(novoChamado);
    }
}