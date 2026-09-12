package br.inatel.chamados.controller;

import br.inatel.chamados.model.Equipamento;
import br.inatel.chamados.repository.EquipamentoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/equipamentos")
public class EquipamentoController {

    @Autowired
    private EquipamentoRepository equipamentoRepository;

    @GetMapping
    public List<Equipamento> listar() {
        return equipamentoRepository.findAll();
    }

    @PostMapping
    public Equipamento criar(@RequestBody Equipamento equipamento) {
        return equipamentoRepository.save(equipamento);
    }
}