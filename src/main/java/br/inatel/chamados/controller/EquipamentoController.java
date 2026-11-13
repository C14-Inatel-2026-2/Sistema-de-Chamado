package br.inatel.chamados.controller;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.inatel.chamados.model.Equipamento;
import br.inatel.chamados.service.EquipamentoService;

@RestController
@RequestMapping("/equipamentos")
public class EquipamentoController {

    private final EquipamentoService equipamentoService;

    public EquipamentoController(EquipamentoService equipamentoService) {
        this.equipamentoService = equipamentoService;
    }

    @GetMapping
    public List<Equipamento> listar() {
        return equipamentoService.listar();
    }

    @PostMapping
    public Equipamento criar(@RequestBody Equipamento equipamento) {
        return equipamentoService.criar(equipamento);
    }
}
