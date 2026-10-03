package br.inatel.chamados.service;

import br.inatel.chamados.model.Chamado;
import br.inatel.chamados.repository.ChamadoRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
public class ChamadoConsultaService {

    private final ChamadoRepository chamadoRepository;

    public ChamadoConsultaService(ChamadoRepository chamadoRepository) {
        this.chamadoRepository = chamadoRepository;
    }

    public List<Chamado> listarTodos() {
        return chamadoRepository.findAll();
    }

    public Chamado buscarPorId(Long id) {
        Optional<Chamado> chamado = chamadoRepository.findById(id);

        if (chamado.isEmpty()) {
            throw new NoSuchElementException("Chamado não encontrado com id: " + id);
        }

        return chamado.get();
    }
}
