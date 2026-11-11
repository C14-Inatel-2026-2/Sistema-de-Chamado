package br.inatel.chamados.controller;

import br.inatel.chamados.model.Usuario;
import br.inatel.chamados.service.UsuarioService;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/usuarios")
public class UsuarioController {

    private final UsuarioService usuarioService;

    public UsuarioController(UsuarioService usuarioService) {
        this.usuarioService = usuarioService;
    }

    @GetMapping
    public ModelAndView listar() {
        return new ModelAndView("usuarios")
                .addObject("usuarios", usuarioService.listarTodos());
    }

    @GetMapping("/novo")
    public String novoUsuario(Model model) {
        model.addAttribute("usuario", new Usuario());
        return "usuario-form";
    }

    @PostMapping
    public String cadastrar(@Valid @ModelAttribute("usuario") Usuario usuario,
                            BindingResult resultado, Model model) {
        if (resultado.hasErrors()) {
            return "usuario-form";
        }

        try {
            usuarioService.cadastrar(usuario);
            return "redirect:/usuarios/login?cadastro=sucesso";
        } catch (IllegalArgumentException exception) {
            model.addAttribute("erro", exception.getMessage());
            return "usuario-form";
        }
    }

    @GetMapping("/login")
    public String telaLogin(@RequestParam(value = "cadastro", required = false) String cadastro,
                            Model model) {
        if ("sucesso".equals(cadastro)) {
            model.addAttribute("mensagem", "Cadastro realizado! Faça login para continuar.");
        }
        return "login";
    }

    @PostMapping("/login")
    public String login(@RequestParam("email") String email, @RequestParam("senha") String senha,
                        HttpSession session, Model model) {
        Usuario usuario = usuarioService.autenticar(email, senha);
        if (usuario == null) {
            model.addAttribute("erro", "E-mail ou senha inválidos.");
            return "login";
        }

        session.setAttribute("usuarioId", usuario.getId());
        session.setAttribute("usuarioNome", usuario.getNome());
        session.setAttribute("usuarioPerfil", usuario.getPerfil());
        return "redirect:/dashboard";
    }

    @PostMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate();
        return "redirect:/usuarios/login";
    }
}
