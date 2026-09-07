package br.com.inatel.chamados.chamado.repository;

import br.com.inatel.chamados.chamado.entity.Chamado;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ChamadoRepository extends JpaRepository<Chamado, Long> {
}