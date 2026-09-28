package br.edu.ufersa.pw.bairro.negocio;

import br.edu.ufersa.pw.bairro.usuario.Usuario;
import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "reivindicacoes")
public class Reivindicacao {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "negocio_id", nullable = false)
    private Negocio negocio;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "usuario_id", nullable = false)
    private Usuario usuario;

    @Column(length = 500)
    private String justificativa;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private StatusReivindicacao status;

    @Column(name = "criado_em", nullable = false)
    private LocalDateTime criadoEm;

    // Preenchidos so quando um ADMIN decide; nulos enquanto PENDENTE.
    @Column(name = "decidida_em")
    private LocalDateTime decididaEm;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "decidida_por_id")
    private Usuario decididaPor;

    protected Reivindicacao() {
    }

    // Nasce sempre PENDENTE. A justificativa e opcional.
    public Reivindicacao(Negocio negocio, Usuario usuario, String justificativa) {
        if (negocio == null) {
            throw new IllegalArgumentException("O negócio é obrigatório!");
        }
        if (usuario == null) {
            throw new IllegalArgumentException("O usuário é obrigatório!");
        }
        this.negocio = negocio;
        this.usuario = usuario;
        this.justificativa = justificativa;
        this.status = StatusReivindicacao.PENDENTE;
        this.criadoEm = LocalDateTime.now();
    }

    // ADMIN aprova: quem de fato vira dono do negocio e regra do service (chama Negocio.reivindicar).
    public void aprovar(Usuario admin) {
        decidir(admin);
        this.status = StatusReivindicacao.APROVADA;
    }

    // ADMIN rejeita: so marca, nao mexe no negocio.
    public void rejeitar(Usuario admin) {
        decidir(admin);
        this.status = StatusReivindicacao.REJEITADA;
    }

    private void decidir(Usuario admin) {
        if (status != StatusReivindicacao.PENDENTE) {
            throw new IllegalStateException("Esta reivindicação já foi decidida.");
        }
        this.decididaPor = admin;
        this.decididaEm = LocalDateTime.now();
    }

    public Long getId() {
        return id;
    }

    public Negocio getNegocio() {
        return negocio;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public String getJustificativa() {
        return justificativa;
    }

    public StatusReivindicacao getStatus() {
        return status;
    }

    public LocalDateTime getCriadoEm() {
        return criadoEm;
    }

    public LocalDateTime getDecididaEm() {
        return decididaEm;
    }

    public Usuario getDecididaPor() {
        return decididaPor;
    }
}
