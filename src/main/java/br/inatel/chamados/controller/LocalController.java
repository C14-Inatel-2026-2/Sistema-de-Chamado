package br.inatel.chamados.controller;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.inatel.chamados.model.Local;
import br.inatel.chamados.service.LocalService;

@RestController
@RequestMapping("/locais")
public class LocalController {

    private final LocalService localService;

    public LocalController(LocalService localService) {
        this.localService = localService;
    }

    @GetMapping
    public List<Local> listar() {
        return localService.listar();
    }

    @PostMapping
    public Local criar(@RequestBody Local local) {
        return localService.criar(local);
    }
}