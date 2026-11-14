package br.inatel.chamados.service;

import br.inatel.chamados.model.Chamado;
import br.inatel.chamados.model.StatusChamado;
import br.inatel.chamados.model.Usuario;
import br.inatel.chamados.repository.ChamadoRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class ChamadoService {

    private static final String PERFIL_TECNICO = "TECNICO";

    private final ChamadoRepository chamadoRepository;

    public ChamadoService(ChamadoRepository chamadoRepository) {
        this.chamadoRepository = chamadoRepository;
    }

    public Chamado abrirChamado(Chamado chamado) {
        validarTextoObrigatorio(chamado.getTitulo(), "Título é obrigatório");
        validarTextoObrigatorio(chamado.getDescricao(), "Descrição é obrigatória");

        chamado.setStatus(StatusChamado.ABERTO);
        chamado.setDataAbertura(LocalDateTime.now());

        return chamadoRepository.save(chamado);
    }

    public Chamado atribuirTecnico(Long chamadoId, Usuario tecnico) {
        Chamado chamado = buscarChamado(chamadoId);

        validarTecnico(tecnico);

        chamado.setTecnico(tecnico);

        return chamadoRepository.save(chamado);
    }

    private void validarTecnico(Usuario tecnico) {

        if (tecnico == null) {
            throw new IllegalArgumentException("Técnico é obrigatório");
        }

        if (!PERFIL_TECNICO.equalsIgnoreCase(tecnico.getPerfil())) {
            throw new IllegalArgumentException(
                    "O usuário selecionado não possui perfil de técnico"
            );
        }
    }

    public Chamado atualizarStatus(Long chamadoId, StatusChamado novoStatus) {
        Chamado chamado = buscarChamado(chamadoId);

        if (novoStatus == null) {
            throw new IllegalArgumentException("Novo status é obrigatório");
        }

        StatusChamado statusAtual = chamado.getStatus();

        if (!transicaoPermitida(statusAtual, novoStatus)) {
            throw new IllegalArgumentException(
                    "Transição de status não permitida: "
                            + statusAtual + " → " + novoStatus
            );
        }

        chamado.setStatus(novoStatus);

        return chamadoRepository.save(chamado);
    }

    private void validarTextoObrigatorio(String texto, String mensagem) {
        if (texto == null || texto.isBlank()) {
            throw new IllegalArgumentException(mensagem);
        }
    }

    private Chamado buscarChamado(Long chamadoId) {
        return chamadoRepository.findById(chamadoId)
                .orElseThrow(() ->
                        new IllegalArgumentException("Chamado não encontrado")
                );
    }

    private boolean transicaoPermitida(
            StatusChamado statusAtual,
            StatusChamado novoStatus) {

        return switch (statusAtual) {
            case ABERTO -> novoStatus == StatusChamado.EM_ANDAMENTO;
            case EM_ANDAMENTO -> novoStatus == StatusChamado.RESOLVIDO;
            case RESOLVIDO -> novoStatus == StatusChamado.FECHADO;
            case FECHADO -> false;
            case null -> false;
        };
    }
}
