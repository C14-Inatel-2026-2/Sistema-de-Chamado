package br.inatel.chamados.controller;

import br.inatel.chamados.model.Local;
import br.inatel.chamados.repository.LocalRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/locais")
public class LocalController {

    @Autowired
    private LocalRepository localRepository;

    @GetMapping
    public List<Local> listar() {
        return localRepository.findAll();
    }

    @PostMapping
    public Local criar(@RequestBody Local local) {
        return localRepository.save(local);
    }
}