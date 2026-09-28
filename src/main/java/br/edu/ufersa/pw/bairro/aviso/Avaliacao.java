package br.edu.ufersa.pw.bairro.aviso;

import br.edu.ufersa.pw.bairro.usuario.Usuario;
import jakarta.persistence.*;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.SQLRestriction;

import java.time.LocalDateTime;

// Um usuario avalia um aviso como util ou nao-util. PUT .../votos/me e idempotente:
// so pode existir uma Avaliacao por (aviso, usuario) - dai o unique constraint.
// Soft delete: os votos de um usuario excluido precisam sair da contagem sem perder o dado.
@Entity
@Table(name = "avaliacoes", uniqueConstraints = @UniqueConstraint(columnNames = {"aviso_id", "usuario_id"}))
@SQLDelete(sql = "UPDATE avaliacoes SET excluido_em = CURRENT_TIMESTAMP WHERE id = ?")
@SQLRestriction("excluido_em IS NULL")
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

    // Preenchido so pelo @SQLDelete; nulo = registro ativo.
    @Column(name = "excluido_em")
    private LocalDateTime excluidoEm;

    protected Avaliacao() {
    }

    public Avaliacao(Aviso aviso, Usuario usuario, boolean util) {
        if (aviso == null) {
            throw new IllegalArgumentException("O aviso é obrigatório!");
        }
        if (usuario == null) {
            throw new IllegalArgumentException("O usuário é obrigatório!");
        }
        this.aviso = aviso;
        this.usuario = usuario;
        this.util = util;
        this.criadoEm = LocalDateTime.now();
    }

    // PUT .../votos/me e idempotente: troca o voto existente em vez de criar outro.
    public void atualizarVoto(boolean util) {
        this.util = util;
    }

    public Long getId() {
        return id;
    }

    public Aviso getAviso() {
        return aviso;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public boolean isUtil() {
        return util;
    }

    public LocalDateTime getCriadoEm() {
        return criadoEm;
    }
}
