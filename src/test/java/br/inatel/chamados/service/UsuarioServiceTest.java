package br.inatel.chamados.service;

import br.inatel.chamados.model.Usuario;
import br.inatel.chamados.repository.UsuarioRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UsuarioServiceTest {

    @Mock
    private UsuarioRepository usuarioRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    private UsuarioService usuarioService;

    @Test
    void deveCadastrarUsuarioComSenhaCriptografadaEPerfilPadrao() {
        Usuario usuario = novoUsuario("Eduardo", "Eduardo@Email.com", "123456");
        usuario.setPerfil("ADMIN"); // O cadastro público não pode conceder esse perfil.

        when(usuarioRepository.findByEmail("eduardo@email.com")).thenReturn(null);
        when(passwordEncoder.encode("123456")).thenReturn("senha-criptografada");
        when(usuarioRepository.save(usuario)).thenReturn(usuario);

        Usuario resultado = usuarioService.cadastrar(usuario);

        assertSame(usuario, resultado);
        assertEquals("Eduardo", resultado.getNome());
        assertEquals("eduardo@email.com", resultado.getEmail());
        assertEquals("senha-criptografada", resultado.getSenha());
        assertEquals("SOLICITANTE", resultado.getPerfil());
        verify(passwordEncoder).encode("123456");
        verify(usuarioRepository).save(usuario);
    }

    @Test
    void deveArmazenarSenhaComHashBcryptReal() {
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        UsuarioService serviceComEncoderReal = new UsuarioService(usuarioRepository, encoder);
        Usuario usuario = novoUsuario("Eduardo", "eduardo@email.com", "123456");
        when(usuarioRepository.findByEmail("eduardo@email.com")).thenReturn(null);
        when(usuarioRepository.save(usuario)).thenReturn(usuario);

        Usuario resultado = serviceComEncoderReal.cadastrar(usuario);

        assertNotEquals("123456", resultado.getSenha());
        assertTrue(encoder.matches("123456", resultado.getSenha()));
    }

    @Test
    void naoDeveCadastrarUsuarioComEmailExistente() {
        Usuario usuario = novoUsuario("Eduardo", "eduardo@email.com", "123456");
        when(usuarioRepository.findByEmail("eduardo@email.com")).thenReturn(usuario);

        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> usuarioService.cadastrar(usuario)
        );

        assertEquals("E-mail já cadastrado.", exception.getMessage());
        verify(usuarioRepository, never()).save(any());
        verify(passwordEncoder, never()).encode(anyString());
    }

    @Test
    void naoDeveCadastrarUsuarioSemNome() {
        Usuario usuario = novoUsuario(" ", "eduardo@email.com", "123456");
        assertEquals("Nome é obrigatório.", assertThrows(IllegalArgumentException.class,
                () -> usuarioService.cadastrar(usuario)).getMessage());
        verifyNoInteractions(usuarioRepository, passwordEncoder);
    }

    @Test
    void naoDeveCadastrarUsuarioComEmailInvalido() {
        Usuario usuario = novoUsuario("Eduardo", "email-invalido", "123456");
        assertEquals("Informe um e-mail válido.", assertThrows(IllegalArgumentException.class,
                () -> usuarioService.cadastrar(usuario)).getMessage());
        verifyNoInteractions(usuarioRepository, passwordEncoder);
    }

    @Test
    void naoDeveCadastrarUsuarioSemSenha() {
        Usuario usuario = novoUsuario("Eduardo", "eduardo@email.com", " ");
        assertEquals("Senha é obrigatória.", assertThrows(IllegalArgumentException.class,
                () -> usuarioService.cadastrar(usuario)).getMessage());
        verifyNoInteractions(usuarioRepository, passwordEncoder);
    }

    @Test
    void deveAutenticarUsuarioComSenhaCorreta() {
        Usuario usuario = novoUsuario("Eduardo", "eduardo@email.com", "hash");
        when(usuarioRepository.findByEmail("eduardo@email.com")).thenReturn(usuario);
        when(passwordEncoder.matches("123456", "hash")).thenReturn(true);

        assertSame(usuario, usuarioService.autenticar(" EDUARDO@EMAIL.COM ", "123456"));
    }

    @Test
    void naoDeveAutenticarUsuarioComSenhaIncorreta() {
        Usuario usuario = novoUsuario("Eduardo", "eduardo@email.com", "hash");
        when(usuarioRepository.findByEmail("eduardo@email.com")).thenReturn(usuario);
        when(passwordEncoder.matches("senha-errada", "hash")).thenReturn(false);

        assertNull(usuarioService.autenticar("eduardo@email.com", "senha-errada"));
    }

    @Test
    void deveListarUsuariosEmOrdemAlfabetica() {
        List<Usuario> usuarios = List.of(
                novoUsuario("Ana", "ana@email.com", "hash"),
                novoUsuario("Bruno", "bruno@email.com", "hash")
        );
        when(usuarioRepository.findAllByOrderByNomeAsc()).thenReturn(usuarios);

        assertEquals(usuarios, usuarioService.listarTodos());
    }

    private Usuario novoUsuario(String nome, String email, String senha) {
        return new Usuario(null, nome, email, senha, "SOLICITANTE");
    }
}
