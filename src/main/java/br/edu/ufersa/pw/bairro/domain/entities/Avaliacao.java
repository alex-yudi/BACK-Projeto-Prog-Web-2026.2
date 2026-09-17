package br.edu.ufersa.pw.bairro.domain.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;

import java.time.LocalDateTime;

// Um usuario avalia um aviso como util ou nao-util. PUT .../votos/me e idempotente:
// so pode existir uma Avaliacao por (aviso, usuario) - dai o unique constraint.
@Entity
@Table(name = "avaliacoes", uniqueConstraints = @UniqueConstraint(columnNames = {"aviso_id", "usuario_id"}))
public class Avaliacao {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "aviso_id", nullable = false)
    private Aviso aviso;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "usuario_id", nullable = false)
    private Usuario usuario;

    @Column(nullable = false)
    private boolean util;

    @Column(name = "criado_em", nullable = false)
    private LocalDateTime criadoEm;

    public Avaliacao() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Aviso getAviso() {
        return aviso;
    }

    public void setAviso(Aviso aviso) {
        this.aviso = aviso;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public boolean isUtil() {
        return util;
    }

    public void setUtil(boolean util) {
        this.util = util;
    }

    public LocalDateTime getCriadoEm() {
        return criadoEm;
    }

    public void setCriadoEm(LocalDateTime criadoEm) {
        this.criadoEm = criadoEm;
    }
}
