package br.inatel.chamados.repository;

import br.inatel.chamados.model.HistoricoChamado;
import org.springframework.data.jpa.repository.JpaRepository;

public interface HistoricoChamadoRepository extends JpaRepository<HistoricoChamado, Long> {

}