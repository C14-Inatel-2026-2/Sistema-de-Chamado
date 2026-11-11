package br.inatel.chamados.config;

import jakarta.servlet.http.HttpServletResponse;
import org.junit.jupiter.api.Test;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.mock.web.MockHttpSession;

import static org.junit.jupiter.api.Assertions.*;

class ControleAcessoInterceptorTest {

    private final ControleAcessoInterceptor interceptor = new ControleAcessoInterceptor();

    @Test
    void devePermitirAcessoAoLoginSemSessao() throws Exception {
        MockHttpServletRequest request = request("GET", "/usuarios/login");
        MockHttpServletResponse response = new MockHttpServletResponse();
        assertTrue(interceptor.preHandle(request, response, new Object()));
    }

    @Test
    void deveRedirecionarUsuarioNaoAutenticadoAoTentarListarUsuarios() throws Exception {
        MockHttpServletRequest request = request("GET", "/usuarios");
        MockHttpServletResponse response = new MockHttpServletResponse();
        assertFalse(interceptor.preHandle(request, response, new Object()));
        assertEquals("/usuarios/login", response.getRedirectedUrl());
    }

    @Test
    void deveBloquearSolicitanteNaListagemDeUsuarios() throws Exception {
        MockHttpServletRequest request = request("GET", "/usuarios");
        adicionarSessao(request, "SOLICITANTE");
        MockHttpServletResponse response = new MockHttpServletResponse();
        assertFalse(interceptor.preHandle(request, response, new Object()));
        assertEquals(HttpServletResponse.SC_FORBIDDEN, response.getStatus());
    }

    @Test
    void deveBloquearTecnicoNaListagemDeUsuarios() throws Exception {
        MockHttpServletRequest request = request("GET", "/usuarios");
        adicionarSessao(request, "TECNICO");
        MockHttpServletResponse response = new MockHttpServletResponse();
        assertFalse(interceptor.preHandle(request, response, new Object()));
        assertEquals(HttpServletResponse.SC_FORBIDDEN, response.getStatus());
    }

    @Test
    void devePermitirAdministradorNaListagemDeUsuarios() throws Exception {
        MockHttpServletRequest request = request("GET", "/usuarios");
        adicionarSessao(request, "ADMIN");
        MockHttpServletResponse response = new MockHttpServletResponse();
        assertTrue(interceptor.preHandle(request, response, new Object()));
    }

    @Test
    void naoDeveInterferirNoDashboard() throws Exception {
        MockHttpServletRequest request = request("GET", "/dashboard");
        MockHttpServletResponse response = new MockHttpServletResponse();
        assertTrue(interceptor.preHandle(request, response, new Object()));
    }

    @Test
    void naoDeveInterferirNosEndpointsDeOutrosModulos() throws Exception {
        MockHttpServletRequest request = request("POST", "/equipamentos");
        MockHttpServletResponse response = new MockHttpServletResponse();
        assertTrue(interceptor.preHandle(request, response, new Object()));
    }

    private MockHttpServletRequest request(String metodo, String caminho) {
        MockHttpServletRequest request = new MockHttpServletRequest();
        request.setMethod(metodo);
        request.setRequestURI(caminho);
        request.setServletPath(caminho);
        return request;
    }

    private void adicionarSessao(MockHttpServletRequest request, String perfil) {
        MockHttpSession session = new MockHttpSession();
        session.setAttribute("usuarioId", 1L);
        session.setAttribute("usuarioPerfil", perfil);
        request.setSession(session);
    }
}
