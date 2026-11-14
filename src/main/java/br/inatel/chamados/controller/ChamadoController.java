package br.inatel.chamados.controller;

import br.inatel.chamados.model.Chamado;
import br.inatel.chamados.service.ChamadoConsultaService;
import br.inatel.chamados.service.ChamadoService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.dao.DataIntegrityViolationException;

import java.util.List;
import java.util.NoSuchElementException;

@RestController
@RequestMapping("/chamados")
public class ChamadoController {

    private final ChamadoService chamadoService;
    private final ChamadoConsultaService consultaService;

    public ChamadoController(ChamadoService chamadoService,
                             ChamadoConsultaService consultaService) {
        this.chamadoService = chamadoService;
        this.consultaService = consultaService;
    }

    @PostMapping
    public ResponseEntity<Chamado> abrir(@RequestBody Chamado chamado) {
        if (chamado.getPrioridade() == null) {
            throw new IllegalArgumentException("Prioridade é obrigatória");
        }
        Chamado criado = chamadoService.abrirChamado(chamado);
        return ResponseEntity.status(HttpStatus.CREATED).body(criado);
    }

    @GetMapping
    public List<Chamado> listar() {
        return consultaService.listarTodos();
    }

    @GetMapping("/{id}")
    public Chamado buscarPorId(@PathVariable Long id) {
        return consultaService.buscarPorId(id);
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<String> tratarRequisicaoInvalida(IllegalArgumentException e) {
        return ResponseEntity.badRequest().body(e.getMessage());
    }

    @ExceptionHandler(NoSuchElementException.class)
    public ResponseEntity<String> tratarNaoEncontrado(NoSuchElementException e) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
    }

    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<String> tratarVinculoInvalido(DataIntegrityViolationException e) {
        return ResponseEntity.badRequest()
                .body("Dados inválidos: verifique se o solicitante, o local e o equipamento informados existem");
    }
}
