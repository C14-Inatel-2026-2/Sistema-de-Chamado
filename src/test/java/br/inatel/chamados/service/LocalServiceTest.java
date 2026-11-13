package br.inatel.chamados.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import org.mockito.junit.jupiter.MockitoExtension;

import br.inatel.chamados.model.Local;
import br.inatel.chamados.repository.LocalRepository;

@ExtendWith(MockitoExtension.class)
class LocalServiceTest {

    // TESTE COM MOCK
    @Mock
    private LocalRepository localRepository;

    @InjectMocks
    private LocalService localService;

    @Test
    void deveSalvarLocalERetornarComId() {
        Local entrada = new Local("Sala 101");
        Local salvo = new Local("Sala 101");
        salvo.setId(1L);

        when(localRepository.save(entrada)).thenReturn(salvo);

        Local resultado = localService.criar(entrada);

        assertEquals(1L, resultado.getId());
        assertEquals("Sala 101", resultado.getNome());
        verify(localRepository).save(entrada);
    }
}
