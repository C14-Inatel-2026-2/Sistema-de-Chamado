package br.inatel.chamados;

import br.inatel.chamados.model.Usuario;
import br.inatel.chamados.service.UsuarioService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/usuarios")
public class UsuarioController {

    private final UsuarioService usuarioService;

    public UsuarioController(UsuarioService usuarioService) {
        this.usuarioService = usuarioService;
    }

    @GetMapping
    public String listar(Model model) {
        return "usuarios";
    }

    @GetMapping("/novo")
    public String novoUsuario(Model model) {
        model.addAttribute("usuario", new Usuario());
        return "usuario-form";
    }

    @PostMapping
    public String cadastrar(@ModelAttribute Usuario usuario) {
        usuarioService.cadastrar(usuario);
        return "redirect:/usuarios";
    }
}