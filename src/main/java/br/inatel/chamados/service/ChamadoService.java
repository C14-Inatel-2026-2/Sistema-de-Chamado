package br.inatel.chamados.service;

import br.inatel.chamados.model.Chamado;
import br.inatel.chamados.model.StatusChamado;
import br.inatel.chamados.repository.ChamadoRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class ChamadoService {

    private final ChamadoRepository chamadoRepository;

    public ChamadoService(ChamadoRepository chamadoRepository) {
        this.chamadoRepository = chamadoRepository;
    }

    public Chamado abrirChamado(Chamado chamado) {
        if (chamado.getTitulo() == null || chamado.getTitulo().isBlank()) {
            throw new IllegalArgumentException("Título é obrigatório");
        }
        if (chamado.getDescricao() == null || chamado.getDescricao().isBlank()) {
            throw new IllegalArgumentException("Descrição é obrigatória");
        }

        chamado.setStatus(StatusChamado.ABERTO);
        chamado.setDataAbertura(LocalDateTime.now());

        return chamadoRepository.save(chamado);
    }
}