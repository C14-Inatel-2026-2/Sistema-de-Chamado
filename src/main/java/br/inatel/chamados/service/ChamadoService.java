package br.inatel.chamados.service;

import br.inatel.chamados.model.Chamado;
import br.inatel.chamados.model.StatusChamado;
import br.inatel.chamados.repository.ChamadoRepository;
import org.springframework.stereotype.Service;
import br.inatel.chamados.model.Perfil;

import java.time.LocalDateTime;

@Service
public class ChamadoService {

    private final ChamadoRepository chamadoRepository;

    public ChamadoService(ChamadoRepository chamadoRepository) {
        this.chamadoRepository = chamadoRepository;
    }

    public Chamado abrirChamado(Chamado chamado) {
        if (chamado.getTitulo() == null || chamado.getTitulo().isBlank()) {
            throw new IllegalArgumentException("Título é obrigatório");
        }
        if (chamado.getDescricao() == null || chamado.getDescricao().isBlank()) {
            throw new IllegalArgumentException("Descrição é obrigatória");
        }

        chamado.setStatus(StatusChamado.ABERTO);
        chamado.setDataAbertura(LocalDateTime.now());

        return chamadoRepository.save(chamado);
    }

    public Chamado atribuirTecnico(Long chamadoId, Usuario tecnico) {
        Chamado chamado = buscarChamado(chamadoId);

        if (tecnico == null) {
            throw new IllegalArgumentException("Técnico é obrigatório");
        }

        if (tecnico.getPerfil() != Perfil.TECNICO) {
            throw new IllegalArgumentException(
                    "O usuário selecionado não possui perfil de técnico"
            );
        }

        chamado.setTecnico(tecnico);

        return chamadoRepository.save(chamado);
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

    private Chamado buscarChamado(Long chamadoId) {
        return chamadoRepository.findById(chamadoId)
                .orElseThrow(() ->
                        new IllegalArgumentException("Chamado não encontrado")
                );
    }

    private boolean transicaoPermitida(
            StatusChamado statusAtual,
            StatusChamado novoStatus) {

        return (statusAtual == StatusChamado.ABERTO
                && novoStatus == StatusChamado.EM_ANDAMENTO)

                || (statusAtual == StatusChamado.EM_ANDAMENTO
                && novoStatus == StatusChamado.RESOLVIDO)

                || (statusAtual == StatusChamado.RESOLVIDO
                && novoStatus == StatusChamado.FECHADO);
    }
}