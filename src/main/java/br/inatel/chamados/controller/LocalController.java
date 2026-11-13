package br.inatel.chamados.controller;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
   import org.springframework.web.bind.annotation.RequestBody;
   import org.springframework.web.bind.annotation.RequestMapping;
   import org.springframework.web.bind.annotation.RestController;

   import br.inatel.chamados.model.Local;
import br.inatel.chamados.repository.LocalRepository;

@RestController
@RequestMapping("/locais")
public class LocalController {

    private final LocalRepository localRepository;

   public LocalController(LocalRepository localRepository) {
    this.localRepository = localRepository;
   }
    @GetMapping
    public List<Local> listar() {
        return localRepository.findAll();
    }

    @PostMapping
    public Local criar(@RequestBody Local local) {
        return localRepository.save(local);
    }
}