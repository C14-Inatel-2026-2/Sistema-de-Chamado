package br.inatel.chamados.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.ui.ExtendedModelMap;
import org.springframework.ui.Model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

class PaginaControllerTest {

    private PaginaController controller;

    @BeforeEach
    void setUp() {
        controller = new PaginaController();
    }

    @Test
    void inicioComMockDeveAdicionarTituloERetornarDashboard() {
        Model model = mock(Model.class);

        String pagina = controller.inicio(model);

        assertEquals("dashboard", pagina);
        verify(model).addAttribute(
                "titulo",
                "Sistema de Chamados de Manutenção"
        );
    }

    @Test
    void dashboardComMockDeveAdicionarTituloERetornarDashboard() {
        Model model = mock(Model.class);

        String pagina = controller.dashboard(model);

        assertEquals("dashboard", pagina);
        verify(model).addAttribute(
                "titulo",
                "Sistema de Chamados de Manutenção"
        );
    }

    @Test
    void inicioSemMockDevePreencherModelERetornarDashboard() {
        ExtendedModelMap model = new ExtendedModelMap();

        String pagina = controller.inicio(model);

        assertEquals("dashboard", pagina);
        assertEquals(
                "Sistema de Chamados de Manutenção",
                model.get("titulo")
        );
    }

    @Test
    void dashboardSemMockComModelNuloDeveLancarExcecao() {
        assertThrows(
                IllegalArgumentException.class,
                () -> controller.dashboard(null)
        );
    }
}
