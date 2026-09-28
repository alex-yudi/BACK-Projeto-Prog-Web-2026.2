package br.edu.ufersa.pw.bairro.negocio;

import br.edu.ufersa.pw.bairro.usuario.Usuario;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.Version;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.SQLRestriction;

import java.time.LocalDateTime;
import java.util.Objects;

// Soft delete: repository.delete(...) vira UPDATE em excluido_em e toda consulta ignora
// os excluidos. Como ha @Version, o UPDATE tambem confere e incrementa a versao.
@Entity
@Table(name = "negocios")
@SQLDelete(sql = "UPDATE negocios SET excluido_em = CURRENT_TIMESTAMP, version = version + 1 WHERE id = ? AND version = ?")
@SQLRestriction("excluido_em IS NULL")
public class Negocio {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String nome;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private CategoriaNegocio categoria;

    @Column(nullable = false)
    private String cep;

    @Column(nullable = false)
    private String numero;

    @Column(nullable = false)
    private String bairro;

    @Column(nullable = false, length = 500)
    private String descricao;

    // null = negocio ainda nao reivindicado por nenhum usuario.
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "dono_id")
    private Usuario dono;

    // Lock otimista: duas alteracoes simultaneas do mesmo negocio (ex.: duas aprovacoes de reivindicacao)
    // fazem a segunda falhar em vez de sobrescrever a primeira. O default cobre linhas ja existentes.
    @Version
    @ColumnDefault("0")
    @Column(nullable = false)
    private Long version;

    // Preenchido so pelo @SQLDelete; nulo = registro ativo.
    @Column(name = "excluido_em")
    private LocalDateTime excluidoEm;

    protected Negocio() {
    }

    public Negocio(String nome, CategoriaNegocio categoria, String cep, String numero, String bairro, String descricao) {
        this.nome = validarNome(nome);
        this.categoria = validarCategoria(categoria);
        this.cep = validarCep(cep);
        this.numero = validarNumero(numero);
        this.bairro = validarBairro(bairro);
        this.descricao = validarDescricao(descricao);
    }

    // PUT exige todos os campos deste recurso.
    public void atualizar(String nome, CategoriaNegocio categoria, String cep, String numero, String bairro, String descricao) {
        this.nome = validarNome(nome);
        this.categoria = validarCategoria(categoria);
        this.cep = validarCep(cep);
        this.numero = validarNumero(numero);
        this.bairro = validarBairro(bairro);
        this.descricao = validarDescricao(descricao);
    }

    // POST /api/v1/negocios/{id}/reivindicacoes - so pode reivindicar quem ainda nao tem dono.
    public void reivindicar(Usuario usuario) {
        if (usuario == null) {
            throw new IllegalArgumentException("O usuário que está reivindicando é obrigatório!");
        }
        if (this.dono != null) {
            throw new IllegalStateException("Este negócio já foi reivindicado!");
        }
        this.dono = usuario;
    }

    private String validarNome(String nome) {
        if (nome == null || nome.isBlank()) {
            throw new IllegalArgumentException("O nome é obrigatório!");
        }
        return nome;
    }

    private CategoriaNegocio validarCategoria(CategoriaNegocio categoria) {
        if (categoria == null) {
            throw new IllegalArgumentException("A categoria é obrigatória!");
        }
        return categoria;
    }

    private String validarCep(String cep) {
        if (cep == null || !cep.matches("\\d{8}")) {
            throw new IllegalArgumentException("O CEP deve conter exatamente 8 dígitos!");
        }
        return cep;
    }

    private String validarNumero(String numero) {
        if (numero == null || numero.isBlank()) {
            throw new IllegalArgumentException("O número é obrigatório!");
        }
        return numero;
    }

    private String validarBairro(String bairro) {
        if (bairro == null || bairro.isBlank()) {
            throw new IllegalArgumentException("O bairro é obrigatório!");
        }
        return bairro;
    }

    private String validarDescricao(String descricao) {
        if (descricao == null || descricao.isBlank()) {
            throw new IllegalArgumentException("A descrição é obrigatória!");
        }
        return descricao;
    }

    public Long getId() {
        return id;
    }

    public String getNome() {
        return nome;
    }

    public CategoriaNegocio getCategoria() {
        return categoria;
    }

    public String getCep() {
        return cep;
    }

    public String getNumero() {
        return numero;
    }

    public String getBairro() {
        return bairro;
    }

    public String getDescricao() {
        return descricao;
    }

    public Usuario getDono() {
        return dono;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Negocio negocio = (Negocio) o;
        return id != null && id.equals(negocio.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(nome, cep, numero);
    }
}
