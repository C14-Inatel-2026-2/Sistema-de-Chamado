package br.inatel.chamados.model;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "historico_chamado")
public class HistoricoChamado {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "chamado_id", nullable = false)
    private Chamado chamado;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private StatusChamado statusAnterior;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private StatusChamado statusNovo;

    @Column(nullable = false)
    private LocalDateTime data;

    public HistoricoChamado() {
    }

    public HistoricoChamado(Chamado chamado, StatusChamado statusAnterior,
                            StatusChamado statusNovo, LocalDateTime data) {
        this.chamado = chamado;
        this.statusAnterior = statusAnterior;
        this.statusNovo = statusNovo;
        this.data = data;
    }

    public Long getId() {
        return id;
    }

    public Chamado getChamado() {
        return chamado;
    }

    public void setChamado(Chamado chamado) {
        this.chamado = chamado;
    }

    public StatusChamado getStatusAnterior() {
        return statusAnterior;
    }

    public void setStatusAnterior(StatusChamado statusAnterior) {
        this.statusAnterior = statusAnterior;
    }

    public StatusChamado getStatusNovo() {
        return statusNovo;
    }

    public void setStatusNovo(StatusChamado statusNovo) {
        this.statusNovo = statusNovo;
    }

    public LocalDateTime getData() {
        return data;
    }

    public void setData(LocalDateTime data) {
        this.data = data;
    }
}