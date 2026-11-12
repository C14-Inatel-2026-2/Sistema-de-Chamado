package br.inatel.chamados.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class PaginaController {

    private static final String TITULO = "Sistema de Chamados de Manutenção";

    @GetMapping("/")
    public String inicio(Model model) {
        prepararModel(model);
        return "dashboard";
    }

    @GetMapping("/dashboard")
    public String dashboard(Model model) {
        prepararModel(model);
        return "dashboard";
    }

    private void prepararModel(Model model) {
        if (model == null) {
            throw new IllegalArgumentException("O model não pode ser nulo");
        }

        model.addAttribute("titulo", TITULO);
    }
}
