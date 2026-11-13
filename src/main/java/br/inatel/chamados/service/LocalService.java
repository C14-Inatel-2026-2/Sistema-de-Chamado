package br.inatel.chamados.service;

import java.util.List;

import org.springframework.stereotype.Service;

import br.inatel.chamados.model.Local;
import br.inatel.chamados.repository.LocalRepository;

@Service
public class LocalService {

    private final LocalRepository localRepository;

    public LocalService(LocalRepository localRepository) {
        this.localRepository = localRepository;
    }

    public List<Local> listar() {
        return localRepository.findAll();
    }

    public Local criar(Local local) {
        return localRepository.save(local);
    }
}
