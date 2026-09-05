package br.inatel.chamados.controller;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class PaginaControllerTest {

    @Test
    void deveRetornarDashboardNaRotaInicial() {
        PaginaController controller = new PaginaController();

        String pagina = controller.inicio();

        assertEquals("dashboard", pagina);
    }
}
