package br.inatel.chamados.controller;
import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.inatel.chamados.model.Equipamento;
import br.inatel.chamados.repository.EquipamentoRepository;

@RestController
@RequestMapping("/equipamentos")
public class EquipamentoController {

    private final EquipamentoRepository equipamentoRepository;

    public EquipamentoController(EquipamentoRepository equipamentoRepository) {
        this.equipamentoRepository = equipamentoRepository;
    }

    @GetMapping
    public List<Equipamento> listar() {
        return equipamentoRepository.findAll();
    }

    @PostMapping
    public Equipamento criar(@RequestBody Equipamento equipamento) {
        return equipamentoRepository.save(equipamento);
    }
}

