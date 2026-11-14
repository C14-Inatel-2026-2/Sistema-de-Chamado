package br.inatel.chamados.controller;

import br.inatel.chamados.model.Chamado;
import br.inatel.chamados.repository.ChamadoRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.Comparator;
import java.util.List;

@Controller
public class PaginaController {

    private static final String TITULO =
            "Sistema de Chamados de Manutenção";

    private final ChamadoRepository chamadoRepository;

    public PaginaController(ChamadoRepository chamadoRepository) {
        this.chamadoRepository = chamadoRepository;
    }

    @GetMapping("/")
    public String inicio(Model model) {
        return dashboard(model);
    }

    @GetMapping("/dashboard")
    public String dashboard(Model model) {

        adicionarTituloAoModel(model);
        adicionarResumoChamadosAoModel(model);

        return "dashboard";
    }

    private void adicionarTituloAoModel(Model model) {

        if (model == null) {
            throw new IllegalArgumentException(
                    "O model não pode ser nulo"
            );
        }

        model.addAttribute(
                "titulo",
                TITULO
        );
    }

    private void adicionarResumoChamadosAoModel(Model model) {

        List<Chamado> chamados =
                chamadoRepository.findAll();

        model.addAttribute(
                "totalChamados",
                chamados.size()
        );

        model.addAttribute(
                "chamadosAbertos",
                contarPorStatus(
                        chamados,
                        "ABERTO"
                )
        );

        model.addAttribute(
                "chamadosEmAndamento",
                contarPorStatus(
                        chamados,
                        "EM_ANDAMENTO"
                )
        );

        model.addAttribute(
                "chamadosResolvidos",
                contarPorStatus(
                        chamados,
                        "RESOLVIDO"
                )
        );

        model.addAttribute(
                "chamadosFechados",
                contarPorStatus(
                        chamados,
                        "FECHADO"
                )
        );

        model.addAttribute(
                "prioridadeAlta",
                contarPorPrioridade(
                        chamados,
                        "ALTA"
                )
        );

        model.addAttribute(
                "prioridadeMedia",
                contarPorPrioridade(
                        chamados,
                        "MEDIA"
                )
        );

        model.addAttribute(
                "prioridadeBaixa",
                contarPorPrioridade(
                        chamados,
                        "BAIXA"
                )
        );

        List<Chamado> ultimosChamados =
                chamados.stream()
                        .sorted(
                                Comparator.comparingLong(
                                        (Chamado chamado) ->
                                                chamado.getId() == null
                                                        ? 0L
                                                        : chamado.getId()
                                ).reversed()
                        )
                        .limit(5)
                        .toList();

        model.addAttribute(
                "ultimosChamados",
                ultimosChamados
        );
    }

    private long contarPorStatus(
            List<Chamado> chamados,
            String status
    ) {

        return chamados.stream()
                .filter(
                        chamado ->
                                chamado.getStatus() != null
                )
                .filter(
                        chamado ->
                                status.equals(
                                        chamado
                                                .getStatus()
                                                .toString()
                                )
                )
                .count();
    }

    private long contarPorPrioridade(
            List<Chamado> chamados,
            String prioridade
    ) {

        return chamados.stream()
                .filter(
                        chamado ->
                                chamado.getPrioridade() != null
                )
                .filter(
                        chamado ->
                                prioridade.equals(
                                        chamado
                                                .getPrioridade()
                                                .toString()
                                )
                )
                .count();
    }
}