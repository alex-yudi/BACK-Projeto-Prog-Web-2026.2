package br.edu.ufersa.pw.bairro.oferta;

import br.edu.ufersa.pw.bairro.negocio.Negocio;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.SQLRestriction;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

// Soft delete (padrao da F2): repository.delete(...) vira UPDATE em excluido_em, e toda consulta ignora as
// linhas excluidas.
@Entity
@Table(name = "ofertas")
@SQLDelete(sql = "UPDATE ofertas SET excluido_em = CURRENT_TIMESTAMP WHERE id = ?")
@SQLRestriction("excluido_em IS NULL")
public class Oferta {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "negocio_id", nullable = false)
    private Negocio negocio;

    @Column(nullable = false, length = 150)
    private String nome;

    @Column(nullable = false, length = 500)
    private String descricao;

    @Column(nullable = false, precision = 10, scale = 2)
    private BigDecimal preco;

    // Data ate quando a oferta vale; nulo = sem data de validade definida.
    private LocalDate validade;

    @Column(name = "criado_em", nullable = false)
    private LocalDateTime criadoEm;

    // Preenchido so pelo @SQLDelete; nulo = registro ativo.
    @Column(name = "excluido_em")
    private LocalDateTime excluidoEm;

    protected Oferta() {
    }

    public Oferta(Negocio negocio, String nome, String descricao, BigDecimal preco, LocalDate validade) {
        if (negocio == null) {
            throw new IllegalArgumentException("O negócio é obrigatório!");
        }
        this.negocio = negocio;
        this.nome = validarNome(nome);
        this.descricao = validarDescricao(descricao);
        this.preco = validarPreco(preco);
        this.validade = validade;
        this.criadoEm = LocalDateTime.now();
    }

    // PUT exige todos os campos deste recurso. O negocio da oferta nao muda.
    public void atualizar(String nome, String descricao, BigDecimal preco, LocalDate validade) {
        this.nome = validarNome(nome);
        this.descricao = validarDescricao(descricao);
        this.preco = validarPreco(preco);
        this.validade = validade;
    }

    private static String validarNome(String nome) {
        if (nome == null || nome.isBlank()) {
            throw new IllegalArgumentException("O nome é obrigatório!");
        }
        return nome;
    }

    private static String validarDescricao(String descricao) {
        if (descricao == null || descricao.isBlank()) {
            throw new IllegalArgumentException("A descrição é obrigatória!");
        }
        return descricao;
    }

    private static BigDecimal validarPreco(BigDecimal preco) {
        if (preco == null) {
            throw new IllegalArgumentException("O preço é obrigatório!");
        }
        if (preco.compareTo(BigDecimal.ZERO) < 0) {
            throw new IllegalArgumentException("O preço não pode ser negativo!");
        }
        return preco;
    }

    public Long getId() {
        return id;
    }

    public Negocio getNegocio() {
        return negocio;
    }

    public String getNome() {
        return nome;
    }

    public String getDescricao() {
        return descricao;
    }

    public BigDecimal getPreco() {
        return preco;
    }

    public LocalDate getValidade() {
        return validade;
    }

    public LocalDateTime getCriadoEm() {
        return criadoEm;
    }
}
