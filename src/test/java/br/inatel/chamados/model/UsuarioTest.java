package br.inatel.chamados.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class UsuarioTest {         // testes sem mock

    @Test
    void deveCriarUsuarioComConstrutorCompleto() {
        Usuario usuario = new Usuario(
                1L,
                "Eduardo",
                "eduardo@email.com",
                "123456",
                "ADMINISTRADOR"
        );

        assertEquals(1L, usuario.getId());
        assertEquals("Eduardo", usuario.getNome());
        assertEquals("eduardo@email.com", usuario.getEmail());
        assertEquals("123456", usuario.getSenha());
        assertEquals("ADMINISTRADOR", usuario.getPerfil());
    }

    @Test
    void AlterarDadosDoUsuarioComSetters() {
        Usuario usuario = new Usuario();

        usuario.setId(2L);
        usuario.setNome("João");
        usuario.setEmail("joao@email.com");
        usuario.setSenha("abcdef");
        usuario.setPerfil("TECNICO");

        assertEquals(2L, usuario.getId());
        assertEquals("João", usuario.getNome());
        assertEquals("joao@email.com", usuario.getEmail());
        assertEquals("abcdef", usuario.getSenha());
        assertEquals("TECNICO", usuario.getPerfil());
    }
}