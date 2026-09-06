package br.inatel.chamados.service;

import br.inatel.chamados.model.Usuario;
import br.inatel.chamados.repository.UsuarioRepository;
import org.springframework.stereotype.Service;

@Service
public class UsuarioService {

    private final UsuarioRepository usuarioRepository;

    public UsuarioService(UsuarioRepository usuarioRepository) {
        this.usuarioRepository = usuarioRepository;
    }

    public Usuario cadastrar(Usuario usuario) {

        if (usuarioRepository.findByEmail(usuario.getEmail()) != null) {
            throw new IllegalArgumentException("E-mail já cadastrado.");
        }

        return usuarioRepository.save(usuario);
    }

    public Usuario buscarPorEmail(String email) {
        return usuarioRepository.findByEmail(email);
    }
}