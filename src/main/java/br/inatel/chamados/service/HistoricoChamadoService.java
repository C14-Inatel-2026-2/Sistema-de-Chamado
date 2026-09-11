package br.inatel.chamados.service;

import br.inatel.chamados.model.Chamado;
import br.inatel.chamados.model.HistoricoChamado;
import br.inatel.chamados.model.StatusChamado;
import br.inatel.chamados.repository.HistoricoChamadoRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class HistoricoChamadoService {

    private final HistoricoChamadoRepository historicoChamadoRepository;

    public HistoricoChamadoService(HistoricoChamadoRepository historicoChamadoRepository) {
        this.historicoChamadoRepository = historicoChamadoRepository;
    }

    public HistoricoChamado registrarHistorico(Chamado chamado,
                                               StatusChamado statusAnterior,
                                               StatusChamado statusNovo) {

        HistoricoChamado historico = new HistoricoChamado(
                chamado,
                statusAnterior,
                statusNovo,
                LocalDateTime.now()
        );

        return historicoChamadoRepository.save(historico);
    }
}