package br.inatel.chamados.repository;

import br.inatel.chamados.model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UsuarioRepository extends JpaRepository<Usuario, Long> {

    Usuario findByEmail(String email);

    List<Usuario> findAllByOrderByNomeAsc();
}
