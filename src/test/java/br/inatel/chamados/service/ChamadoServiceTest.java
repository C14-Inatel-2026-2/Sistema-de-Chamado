package br.inatel.chamados.service;

import br.inatel.chamados.model.Chamado;
import br.inatel.chamados.model.Prioridade;
import br.inatel.chamados.model.StatusChamado;
import br.inatel.chamados.model.Usuario;
import br.inatel.chamados.repository.ChamadoRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.EnumSource;
import org.junit.jupiter.params.provider.NullAndEmptySource;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
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

    @ParameterizedTest
    @CsvSource({
            "ABERTO, ABERTO, false",
            "ABERTO, EM_ANDAMENTO, true",
            "ABERTO, RESOLVIDO, false",
            "ABERTO, FECHADO, false",
            "EM_ANDAMENTO, ABERTO, false",
            "EM_ANDAMENTO, EM_ANDAMENTO, false",
            "EM_ANDAMENTO, RESOLVIDO, true",
            "EM_ANDAMENTO, FECHADO, false",
            "RESOLVIDO, ABERTO, false",
            "RESOLVIDO, EM_ANDAMENTO, false",
            "RESOLVIDO, RESOLVIDO, false",
            "RESOLVIDO, FECHADO, true",
            "FECHADO, ABERTO, false",
            "FECHADO, EM_ANDAMENTO, false",
            "FECHADO, RESOLVIDO, false",
            "FECHADO, FECHADO, false"
    })
    void deveRespeitarTransicoesDeStatus(StatusChamado statusAtual,
                                        StatusChamado novoStatus,
                                        boolean permitida) {
        chamado.setStatus(statusAtual);
        when(chamadoRepository.findById(1L)).thenReturn(Optional.of(chamado));

        if (permitida) {
            when(chamadoRepository.save(chamado)).thenReturn(chamado);

            Chamado resultado = chamadoService.atualizarStatus(1L, novoStatus);

            assertSame(chamado, resultado);
            assertEquals(novoStatus, resultado.getStatus());
            verify(chamadoRepository).save(chamado);
        } else {
            IllegalArgumentException exception = assertThrows(
                    IllegalArgumentException.class,
                    () -> chamadoService.atualizarStatus(1L, novoStatus)
            );

            assertEquals("Transição de status não permitida: " + statusAtual + " → " + novoStatus,
                    exception.getMessage());
            assertEquals(statusAtual, chamado.getStatus());
            verify(chamadoRepository, never()).save(any());
        }
    }

    @ParameterizedTest
    @EnumSource(StatusChamado.class)
    void naoDeveAlterarStatusDeChamadoComStatusAtualNulo(StatusChamado novoStatus) {
        chamado.setStatus(null);
        when(chamadoRepository.findById(1L)).thenReturn(Optional.of(chamado));

        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> chamadoService.atualizarStatus(1L, novoStatus)
        );

        assertEquals("Transição de status não permitida: null → " + novoStatus,
                exception.getMessage());
        assertNull(chamado.getStatus());
        verify(chamadoRepository, never()).save(any());
    }

    @ParameterizedTest
    @EnumSource(StatusChamado.class)
    void naoDeveAceitarNovoStatusNulo(StatusChamado statusAtual) {
        chamado.setStatus(statusAtual);
        when(chamadoRepository.findById(1L)).thenReturn(Optional.of(chamado));

        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> chamadoService.atualizarStatus(1L, null)
        );

        assertEquals("Novo status é obrigatório", exception.getMessage());
        assertEquals(statusAtual, chamado.getStatus());
        verify(chamadoRepository, never()).save(any());
    }

    @Test
    void deveValidarNovoStatusQuandoAmbosOsStatusSaoNulos() {
        chamado.setStatus(null);
        when(chamadoRepository.findById(1L)).thenReturn(Optional.of(chamado));

        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> chamadoService.atualizarStatus(1L, null)
        );

        assertEquals("Novo status é obrigatório", exception.getMessage());
        assertNull(chamado.getStatus());
        verify(chamadoRepository, never()).save(any());
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

    @ParameterizedTest
    @NullAndEmptySource
    @ValueSource(strings = {" ", "\t\n"})
    void naoDeveAbrirChamadoComTituloInvalido(String titulo) {
        chamado.setTitulo(titulo);

        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> chamadoService.abrirChamado(chamado)
        );

        assertEquals("Título é obrigatório", exception.getMessage());
        assertEquals(StatusChamado.ABERTO, chamado.getStatus());
        assertNull(chamado.getDataAbertura());
        verifyNoInteractions(chamadoRepository);
    }

    @ParameterizedTest
    @NullAndEmptySource
    @ValueSource(strings = {" ", "\t\n"})
    void naoDeveAbrirChamadoComDescricaoInvalida(String descricao) {
        chamado.setDescricao(descricao);

        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> chamadoService.abrirChamado(chamado)
        );

        assertEquals("Descrição é obrigatória", exception.getMessage());
        assertEquals(StatusChamado.ABERTO, chamado.getStatus());
        assertNull(chamado.getDataAbertura());
        verifyNoInteractions(chamadoRepository);
    }

    @Test
    void deveValidarTituloAntesDaDescricao() {
        chamado.setTitulo(" ");
        chamado.setDescricao(null);

        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> chamadoService.abrirChamado(chamado)
        );

        assertEquals("Título é obrigatório", exception.getMessage());
        verifyNoInteractions(chamadoRepository);
    }

    @Test
    void devePreservarDadosERegistrarDataAtualAoAbrirChamado() {
        chamado.setTitulo("  Computador não liga  ");
        chamado.setDescricao("  Computador do laboratório não está ligando  ");
        chamado.setStatus(StatusChamado.FECHADO);
        chamado.setDataAbertura(LocalDateTime.of(2026, 1, 1, 8, 0));
        when(chamadoRepository.save(chamado)).thenReturn(chamado);
        LocalDateTime antesDaAbertura = LocalDateTime.now();

        Chamado resultado = chamadoService.abrirChamado(chamado);

        LocalDateTime depoisDaAbertura = LocalDateTime.now();
        assertSame(chamado, resultado);
        assertEquals("  Computador não liga  ", resultado.getTitulo());
        assertEquals("  Computador do laboratório não está ligando  ", resultado.getDescricao());
        assertEquals(Prioridade.ALTA, resultado.getPrioridade());
        assertEquals(StatusChamado.ABERTO, resultado.getStatus());
        assertFalse(resultado.getDataAbertura().isBefore(antesDaAbertura));
        assertFalse(resultado.getDataAbertura().isAfter(depoisDaAbertura));
        verify(chamadoRepository).save(chamado);
    }

    @Test
    void naoDeveAtribuirTecnicoAChamadoInexistente() {
        when(chamadoRepository.findById(99L))
                .thenReturn(Optional.empty());

        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> chamadoService.atribuirTecnico(99L, tecnico)
        );

        assertEquals(
                "Chamado não encontrado",
                exception.getMessage()
        );

        verify(chamadoRepository, never()).save(any());
    }
}
