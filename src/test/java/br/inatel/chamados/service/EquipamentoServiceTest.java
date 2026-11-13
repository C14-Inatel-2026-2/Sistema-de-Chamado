package br.inatel.chamados.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import org.mockito.junit.jupiter.MockitoExtension;

import br.inatel.chamados.model.Equipamento;
import br.inatel.chamados.model.Local;
import br.inatel.chamados.repository.EquipamentoRepository;

@ExtendWith(MockitoExtension.class)
class EquipamentoServiceTest {

    // TESTE COM MOCK
    @Mock
    private EquipamentoRepository equipamentoRepository;

    @InjectMocks
    private EquipamentoService equipamentoService;

    @Test
    void deveSalvarEquipamentoMantendoLocalAssociado() {
        Local local = new Local("Sala 101");
        Equipamento equipamento = new Equipamento();
        equipamento.setNome("Projetor");
        equipamento.setMarca("Epson");
        equipamento.setNumeroSerie("SN123");
        equipamento.setLocal(local);

        when(equipamentoRepository.save(equipamento)).thenAnswer(invocation -> {
            Equipamento salvo = invocation.getArgument(0);
            salvo.setId(10L);
            return salvo;
        });

        Equipamento resultado = equipamentoService.criar(equipamento);

        assertEquals(10L, resultado.getId());
        assertEquals("SN123", resultado.getNumeroSerie());
        assertSame(local, resultado.getLocal());
        verify(equipamentoRepository).save(equipamento);
    }
}