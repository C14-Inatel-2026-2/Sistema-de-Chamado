package br.inatel.chamados.config;

import br.inatel.chamados.model.Usuario;
import br.inatel.chamados.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.Locale;

/** Cria a conta administrativa inicial somente quando ela ainda não existe. */
@Component
public class AdministradorInicializador implements ApplicationRunner {

    private final UsuarioRepository usuarioRepository;
    private final PasswordEncoder passwordEncoder;
    private final String email;
    private final String senha;
    private final String nome;

    public AdministradorInicializador(
            UsuarioRepository usuarioRepository,
            PasswordEncoder passwordEncoder,
            @Value("${app.admin.email:admin@chamados.com}") String email,
            @Value("${app.admin.password:Admin@123}") String senha,
            @Value("${app.admin.name:Administrador}") String nome) {
        this.usuarioRepository = usuarioRepository;
        this.passwordEncoder = passwordEncoder;
        this.email = email;
        this.senha = senha;
        this.nome = nome;
    }

    @Override
    public void run(ApplicationArguments args) {
        String emailNormalizado = email.trim().toLowerCase(Locale.ROOT);
        if (usuarioRepository.findByEmail(emailNormalizado) != null) {
            return;
        }

        Usuario administrador = new Usuario();
        administrador.setNome(nome);
        administrador.setEmail(emailNormalizado);
        administrador.setSenha(passwordEncoder.encode(senha));
        administrador.setPerfil("ADMIN");
        usuarioRepository.save(administrador);
    }
}
