package br.inatel.chamados.service;

import br.inatel.chamados.model.Chamado;
import br.inatel.chamados.model.Equipamento;
import br.inatel.chamados.model.Local;
import br.inatel.chamados.model.Prioridade;
import br.inatel.chamados.model.StatusChamado;
import br.inatel.chamados.model.Usuario;
import br.inatel.chamados.repository.ChamadoRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ChamadoVinculosTest {

    @Mock
    private ChamadoRepository chamadoRepository;

    @InjectMocks
    private ChamadoService chamadoService;

    @Test
    void deveManterSolicitanteLocalEEquipamentoAoAbrirChamado() {
        Usuario solicitante = new Usuario(1L, "Ana", "ana@inatel.br", "123", "SOLICITANTE");
        Local local = new Local("Laboratório 3");
        Equipamento equipamento = new Equipamento();
        equipamento.setNome("Projetor");
        equipamento.setLocal(local);

        Chamado chamado = new Chamado("Projetor não liga", "Sem imagem", Prioridade.ALTA);
        chamado.setSolicitante(solicitante);
        chamado.setLocal(local);
        chamado.setEquipamento(equipamento);

        when(chamadoRepository.save(any(Chamado.class))).thenAnswer(i -> i.getArgument(0));

        Chamado aberto = chamadoService.abrirChamado(chamado);

        assertEquals(StatusChamado.ABERTO, aberto.getStatus());
        assertSame(solicitante, aberto.getSolicitante());
        assertSame(local, aberto.getLocal());
        assertSame(equipamento, aberto.getEquipamento());
    }

    @Test
    void deveAbrirChamadoSemVinculos() {
        Chamado chamado = new Chamado("Lâmpada queimada", "Corredor B", Prioridade.BAIXA);

        when(chamadoRepository.save(any(Chamado.class))).thenAnswer(i -> i.getArgument(0));

        Chamado aberto = chamadoService.abrirChamado(chamado);

        assertNull(aberto.getSolicitante());
        assertNull(aberto.getLocal());
        assertNull(aberto.getEquipamento());
    }
}