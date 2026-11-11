package br.inatel.chamados.service;

import br.inatel.chamados.model.Usuario;
import br.inatel.chamados.repository.UsuarioRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Locale;

@Service
public class UsuarioService {

    private static final String EMAIL_JA_CADASTRADO = "E-mail já cadastrado.";
    private static final String PERFIL_PADRAO = "SOLICITANTE";

    private final UsuarioRepository usuarioRepository;
    private final PasswordEncoder passwordEncoder;

    public UsuarioService(UsuarioRepository usuarioRepository, PasswordEncoder passwordEncoder) {
        this.usuarioRepository = usuarioRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public Usuario cadastrar(Usuario usuario) {
        validarDados(usuario);

        String emailNormalizado = usuario.getEmail().trim().toLowerCase(Locale.ROOT);
        if (usuarioRepository.findByEmail(emailNormalizado) != null) {
            throw new IllegalArgumentException(EMAIL_JA_CADASTRADO);
        }

        usuario.setNome(usuario.getNome().trim());
        usuario.setEmail(emailNormalizado);
        usuario.setSenha(passwordEncoder.encode(usuario.getSenha()));
        // O cadastro público nunca pode conceder privilégios administrativos.
        usuario.setPerfil(PERFIL_PADRAO);

        return usuarioRepository.save(usuario);
    }

    public Usuario buscarPorEmail(String email) {
        if (email == null || email.isBlank()) {
            throw new IllegalArgumentException("E-mail é obrigatório.");
        }
        return usuarioRepository.findByEmail(email.trim().toLowerCase(Locale.ROOT));
    }

    public List<Usuario> listarTodos() {
        return usuarioRepository.findAllByOrderByNomeAsc();
    }

    public Usuario autenticar(String email, String senha) {
        if (email == null || email.isBlank() || senha == null || senha.isBlank()) {
            return null;
        }

        Usuario usuario = buscarPorEmail(email);
        if (usuario == null || !passwordEncoder.matches(senha, usuario.getSenha())) {
            return null;
        }
        return usuario;
    }

    private void validarDados(Usuario usuario) {
        if (usuario == null) {
            throw new IllegalArgumentException("Os dados do usuário são obrigatórios.");
        }
        if (usuario.getNome() == null || usuario.getNome().isBlank()) {
            throw new IllegalArgumentException("Nome é obrigatório.");
        }
        if (usuario.getEmail() == null || usuario.getEmail().isBlank()
                || !usuario.getEmail().trim().matches("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$")) {
            throw new IllegalArgumentException("Informe um e-mail válido.");
        }
        if (usuario.getSenha() == null || usuario.getSenha().isBlank()) {
            throw new IllegalArgumentException("Senha é obrigatória.");
        }
        if (usuario.getSenha().length() < 6) {
            throw new IllegalArgumentException("A senha deve possuir pelo menos 6 caracteres.");
        }
    }
}
