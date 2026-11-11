package br.inatel.chamados.config;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

@Component
public class ControleAcessoInterceptor implements HandlerInterceptor {

    private static final String PERFIL_ADMIN = "ADMIN";

    @Override
    public boolean preHandle(HttpServletRequest request,
                             HttpServletResponse response,
                             Object handler) throws Exception {
        String caminho = request.getServletPath();
        String metodo = request.getMethod();

        // Este controle pertence ao módulo de usuários. Não interfere no dashboard
        // nem nos endpoints mantidos pelos outros integrantes da equipe.
        if (!caminho.equals("/usuarios") && !caminho.startsWith("/usuarios/")) {
            return true;
        }

        if (ehRotaPublica(caminho, metodo)) {
            return true;
        }

        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("usuarioId") == null) {
            response.sendRedirect(request.getContextPath() + "/usuarios/login");
            return false;
        }

        String perfil = String.valueOf(session.getAttribute("usuarioPerfil"));
        if (!perfil.equals("ADMIN") && !perfil.equals("TECNICO")
                && !perfil.equals("SOLICITANTE")) {
            response.sendError(HttpServletResponse.SC_FORBIDDEN,
                    "Perfil de usuário inválido ou sem permissão.");
            return false;
        }

        if (requerAdministrador(caminho, metodo) && !PERFIL_ADMIN.equals(perfil)) {
            response.sendError(HttpServletResponse.SC_FORBIDDEN,
                    "Seu perfil não possui permissão para acessar este recurso.");
            return false;
        }

        return true;
    }

    private boolean ehRotaPublica(String caminho, String metodo) {
        return caminho.equals("/usuarios/login")
                || caminho.equals("/usuarios/novo") && metodo.equals("GET")
                || caminho.equals("/usuarios") && metodo.equals("POST")
                || caminho.startsWith("/css/")
                || caminho.startsWith("/js/")
                || caminho.equals("/favicon.ico")
                || caminho.equals("/h2-console")
                || caminho.startsWith("/h2-console/");
    }

    private boolean requerAdministrador(String caminho, String metodo) {
        return caminho.equals("/usuarios") && metodo.equals("GET");
    }
}
