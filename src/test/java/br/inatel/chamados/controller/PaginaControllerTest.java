package br.inatel.chamados.controller;

import br.inatel.chamados.repository.ChamadoRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.ui.ExtendedModelMap;
import org.springframework.ui.Model;

import java.util.Collections;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class PaginaControllerTest {

    private static final String TITULO_ESPERADO =
            "Sistema de Chamados de Manutenção";

    private PaginaController controller;
    private ChamadoRepository chamadoRepository;

    @BeforeEach
    void setUp() {

        chamadoRepository = mock(ChamadoRepository.class);

        when(chamadoRepository.findAll())
                .thenReturn(Collections.emptyList());

        controller = new PaginaController(chamadoRepository);
    }

    @Test
    void inicioComMockDeveAdicionarTituloERetornarDashboard() {

        Model model = mock(Model.class);

        String pagina = controller.inicio(model);

        assertEquals("dashboard", pagina);

        verify(model)
                .addAttribute("titulo", TITULO_ESPERADO);
    }

    @Test
    void dashboardComMockDeveAdicionarTituloERetornarDashboard() {

        Model model = mock(Model.class);

        String pagina = controller.dashboard(model);

        assertEquals("dashboard", pagina);

        verify(model)
                .addAttribute("titulo", TITULO_ESPERADO);
    }

    @Test
    void inicioSemMockDevePreencherModelERetornarDashboard() {

        ExtendedModelMap model = new ExtendedModelMap();

        String pagina = controller.inicio(model);

        assertEquals("dashboard", pagina);

        assertEquals(
                TITULO_ESPERADO,
                model.get("titulo")
        );
    }

    @Test
    void dashboardSemMockDevePreencherModelERetornarDashboard() {

        ExtendedModelMap model = new ExtendedModelMap();

        String pagina = controller.dashboard(model);

        assertEquals("dashboard", pagina);

        assertEquals(
                TITULO_ESPERADO,
                model.get("titulo")
        );
    }

    @Test
    void dashboardSemChamadosDeveAdicionarResumoZerado() {

        ExtendedModelMap model = new ExtendedModelMap();

        controller.dashboard(model);

        assertEquals(
                0,
                model.get("totalChamados")
        );

        assertEquals(
                0L,
                model.get("chamadosAbertos")
        );

        assertEquals(
                0L,
                model.get("chamadosEmAndamento")
        );

        assertEquals(
                0L,
                model.get("chamadosResolvidos")
        );

        assertEquals(
                0L,
                model.get("chamadosFechados")
        );
    }

    @Test
    void dashboardSemChamadosDeveAdicionarPrioridadesZeradas() {

        ExtendedModelMap model = new ExtendedModelMap();

        controller.dashboard(model);

        assertEquals(
                0L,
                model.get("prioridadeAlta")
        );

        assertEquals(
                0L,
                model.get("prioridadeMedia")
        );

        assertEquals(
                0L,
                model.get("prioridadeBaixa")
        );
    }

    @Test
    void dashboardDeveConsultarChamadosNoRepositorio() {

        ExtendedModelMap model = new ExtendedModelMap();

        controller.dashboard(model);

        verify(chamadoRepository).findAll();
    }

    @Test
    void dashboardSemMockComModelNuloDeveLancarExcecao() {

        IllegalArgumentException excecao =
                assertThrows(
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

        IllegalArgumentException excecao =
                assertThrows(
                        IllegalArgumentException.class,
                        () -> controller.inicio(null)
                );

        assertEquals(
                "O model não pode ser nulo",
                excecao.getMessage()
        );
    }
}