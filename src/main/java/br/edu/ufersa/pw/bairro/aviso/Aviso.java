package br.edu.ufersa.pw.bairro.aviso;

import br.edu.ufersa.pw.bairro.usuario.Usuario;
import br.edu.ufersa.pw.bairro.negocio.Negocio;
import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "avisos")
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

    public Aviso() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Negocio getNegocio() {
        return negocio;
    }

    public void setNegocio(Negocio negocio) {
        this.negocio = negocio;
    }

    public Usuario getAutor() {
        return autor;
    }

    public void setAutor(Usuario autor) {
        this.autor = autor;
    }

    public CategoriaAviso getCategoria() {
        return categoria;
    }

    public void setCategoria(CategoriaAviso categoria) {
        this.categoria = categoria;
    }

    public String getTexto() {
        return texto;
    }

    public void setTexto(String texto) {
        this.texto = texto;
    }

    public LocalDateTime getCriadoEm() {
        return criadoEm;
    }

    public void setCriadoEm(LocalDateTime criadoEm) {
        this.criadoEm = criadoEm;
    }
}
