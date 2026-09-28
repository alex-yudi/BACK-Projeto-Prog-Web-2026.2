package br.edu.ufersa.pw.bairro.aviso;

import br.edu.ufersa.pw.bairro.negocio.Negocio;
import br.edu.ufersa.pw.bairro.usuario.Usuario;
import jakarta.persistence.*;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.SQLRestriction;

import java.time.LocalDateTime;

// Soft delete: repository.delete(...) vira UPDATE em excluido_em, e toda consulta ignora as excluidas.
@Entity
@Table(name = "avisos")
@SQLDelete(sql = "UPDATE avisos SET excluido_em = CURRENT_TIMESTAMP WHERE id = ?")
@SQLRestriction("excluido_em IS NULL")
public class Aviso {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "negocio_id", nullable = false)
    private Negocio negocio;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "autor_id", nullable = false)
    private Usuario autor;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private CategoriaAviso categoria;

    @Column(nullable = false, length = 500)
    private String texto;

    @Column(name = "criado_em", nullable = false)
    private LocalDateTime criadoEm;

    // votosUtil/votosNaoUtil NAO sao colunas aqui - sao contados a partir das
    // Avaliacoes desse aviso (AvaliacaoRepository) na hora de montar o AvisoResponse.

    // Preenchido so pelo @SQLDelete; nulo = registro ativo.
    @Column(name = "excluido_em")
    private LocalDateTime excluidoEm;

    protected Aviso() {
    }

    public Aviso(Negocio negocio, Usuario autor, CategoriaAviso categoria, String texto) {
        if (negocio == null) {
            throw new IllegalArgumentException("O negócio é obrigatório!");
        }
        if (autor == null) {
            throw new IllegalArgumentException("O autor é obrigatório!");
        }
        this.negocio = negocio;
        this.autor = autor;
        this.categoria = validarCategoria(categoria);
        this.texto = validarTexto(texto);
        this.criadoEm = LocalDateTime.now();
    }

    // PUT exige categoria e texto; o negocio e o autor nao mudam.
    public void atualizar(CategoriaAviso categoria, String texto) {
        this.categoria = validarCategoria(categoria);
        this.texto = validarTexto(texto);
    }

    private static CategoriaAviso validarCategoria(CategoriaAviso categoria) {
        if (categoria == null) {
            throw new IllegalArgumentException("A categoria é obrigatória!");
        }
        return categoria;
    }

    private static String validarTexto(String texto) {
        if (texto == null || texto.isBlank()) {
            throw new IllegalArgumentException("O texto é obrigatório!");
        }
        if (texto.length() > 500) {
            throw new IllegalArgumentException("O texto deve ter no máximo 500 caracteres.");
        }
        return texto;
    }

    public Long getId() {
        return id;
    }

    public Negocio getNegocio() {
        return negocio;
    }

    public Usuario getAutor() {
        return autor;
    }

    public CategoriaAviso getCategoria() {
        return categoria;
    }

    public String getTexto() {
        return texto;
    }

    public LocalDateTime getCriadoEm() {
        return criadoEm;
    }
}
