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

    private static final String TITULO_ESPERADO =
            "Sistema de Chamados de Manutenção";

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
        verify(model).addAttribute("titulo", TITULO_ESPERADO);
    }

    @Test
    void dashboardComMockDeveAdicionarTituloERetornarDashboard() {
        Model model = mock(Model.class);

        String pagina = controller.dashboard(model);

        assertEquals("dashboard", pagina);
        verify(model).addAttribute("titulo", TITULO_ESPERADO);
    }

    @Test
    void inicioSemMockDevePreencherModelERetornarDashboard() {
        ExtendedModelMap model = new ExtendedModelMap();

        String pagina = controller.inicio(model);

        assertEquals("dashboard", pagina);
        assertEquals(TITULO_ESPERADO, model.get("titulo"));
    }

    @Test
    void dashboardSemMockDevePreencherModelERetornarDashboard() {
        ExtendedModelMap model = new ExtendedModelMap();

        String pagina = controller.dashboard(model);

        assertEquals("dashboard", pagina);
        assertEquals(TITULO_ESPERADO, model.get("titulo"));
    }

    @Test
    void dashboardSemMockComModelNuloDeveLancarExcecao() {
        IllegalArgumentException excecao = assertThrows(
                IllegalArgumentException.class,
                () -> controller.dashboard(null)
        );

        assertEquals(
                "O model não pode ser nulo",
                excecao.getMessage()
        );
    }

    @Test
    void inicioSemMockComModelNuloDeveLancarExcecao() {
        IllegalArgumentException excecao = assertThrows(
                IllegalArgumentException.class,
                () -> controller.inicio(null)
        );

        assertEquals(
                "O model não pode ser nulo",
                excecao.getMessage()
        );
    }
}