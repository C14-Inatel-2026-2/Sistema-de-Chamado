package br.inatel.chamados.service;

import br.inatel.chamados.model.Usuario;
import br.inatel.chamados.repository.UsuarioRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UsuarioServiceTest {          // testes com mock

    @Mock
    private UsuarioRepository usuarioRepository;

    @InjectMocks
    private UsuarioService usuarioService;

    @Test
    void deveCadastrarUsuarioComSucesso() {
        Usuario usuario = new Usuario(
                null,
                "Eduardo",
                "eduardo@email.com",
                "123456",
                "ADMINISTRADOR"
        );

        when(usuarioRepository.findByEmail("eduardo@email.com"))
                .thenReturn(null);

        when(usuarioRepository.save(usuario))
                .thenReturn(usuario);

        Usuario resultado = usuarioService.cadastrar(usuario);

        assertEquals(usuario, resultado);

        verify(usuarioRepository).findByEmail("eduardo@email.com");
        verify(usuarioRepository).save(usuario);
    }

    @Test
    void naoDeveCadastrarUsuarioComEmailExistente() {   // teste negativo
        Usuario usuario = new Usuario(
                null,
                "Eduardo",
                "eduardo@email.com",
                "123456",
                "ADMINISTRADOR"
        );

        when(usuarioRepository.findByEmail("eduardo@email.com"))
                .thenReturn(usuario);

        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> usuarioService.cadastrar(usuario)
        );

        assertEquals("E-mail já cadastrado.", exception.getMessage());

        verify(usuarioRepository).findByEmail("eduardo@email.com");
        verify(usuarioRepository, never()).save(any());
    }
}