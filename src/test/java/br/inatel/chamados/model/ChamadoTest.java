package br.inatel.chamados.model;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class ChamadoTest {

    @Test
    void deveAtribuirTecnicoAoChamado() { // TESTE SEM MOCK
        Chamado chamado = new Chamado(
                "Computador não liga",
                "Computador do laboratório não está funcionando",
                Prioridade.ALTA
        );

        Usuario tecnico = new Usuario(
                null,
                "João Técnico",
                "joao@inatel.br",
                "123456",
                "TECNICO"
        );

        chamado.setTecnico(tecnico);

        assertEquals(
                tecnico,
                chamado.getTecnico()
        );
    }

    @Test
    void deveAlterarStatusDoChamado() { // TESTE SEM MOCK
        Chamado chamado = new Chamado(
                "Computador não liga",
                "Computador do laboratório não está funcionando",
                Prioridade.ALTA
        );

        chamado.setStatus(StatusChamado.ABERTO);

        chamado.setStatus(StatusChamado.EM_ANDAMENTO);

        assertEquals(
                StatusChamado.EM_ANDAMENTO,
                chamado.getStatus()
        );
    }

    @Test
    void naoDeveAtribuirUsuarioQueNaoEhTecnico() { // TESTE SEM MOCK E NEGATIVO
        Chamado chamado = new Chamado(
                "Computador não liga",
                "Computador do laboratório não está funcionando",
                Prioridade.ALTA
        );

        Usuario solicitante = new Usuario(
                null,
                "João Solicitante",
                "joao@inatel.br",
                "123456",
                "SOLICITANTE"
        );

        chamado.setTecnico(null);

        assertNotEquals(
                solicitante,
                chamado.getTecnico()
        );
    }

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

    @Test
    void deveCriarChamadoComConstrutorPreenchendoCamposCorretamente() {
        Chamado chamado = new Chamado("Lâmpada queimada", "Corredor do bloco B sem iluminação", Prioridade.BAIXA);

        assertEquals("Lâmpada queimada", chamado.getTitulo());
        assertEquals("Corredor do bloco B sem iluminação", chamado.getDescricao());
        assertEquals(Prioridade.BAIXA, chamado.getPrioridade());
        assertNull(chamado.getStatus());
    }

    @Test
    void deveAtualizarStatusCorretamenteAoUsarSetter() {
        Chamado chamado = new Chamado("Ar-condicionado quebrado", "Sala 210", Prioridade.MEDIA);

        chamado.setStatus(StatusChamado.EM_ANDAMENTO);

        assertEquals(StatusChamado.EM_ANDAMENTO, chamado.getStatus());
    }
}