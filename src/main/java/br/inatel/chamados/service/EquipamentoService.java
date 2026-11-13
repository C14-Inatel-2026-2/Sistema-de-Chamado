package br.inatel.chamados.service;

import java.util.List;

import org.springframework.stereotype.Service;

import br.inatel.chamados.model.Equipamento;
import br.inatel.chamados.repository.EquipamentoRepository;

@Service
public class EquipamentoService {

    private final EquipamentoRepository equipamentoRepository;

    public EquipamentoService(EquipamentoRepository equipamentoRepository) {
        this.equipamentoRepository = equipamentoRepository;
    }

    public List<Equipamento> listar() {
        return equipamentoRepository.findAll();
    }

    public Equipamento criar(Equipamento equipamento) {
        return equipamentoRepository.save(equipamento);
    }
}