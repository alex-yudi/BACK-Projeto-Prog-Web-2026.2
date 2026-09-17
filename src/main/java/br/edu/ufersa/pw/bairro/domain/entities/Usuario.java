package br.edu.ufersa.pw.bairro.domain.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import java.util.Objects;

@Entity
@Table(name = "usuarios")
public class Usuario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String nome;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false)
    private String senha;

    @Column(nullable = false)
    private String cep;

    @Column(nullable = false)
    private String numero;

    protected Usuario() {
    }

    public Usuario(String nome, String email, String senha, String cep, String numero) {
        this.nome = validarNome(nome);
        this.email = validarEmail(email);
        this.senha = validarSenha(senha);
        this.cep = validarCep(cep);
        this.numero = validarNumero(numero);
    }

    // PUT exige todos os campos deste recurso, exceto senha - troca de senha e uma acao a parte.
    public void atualizarPerfil(String nome, String email, String cep, String numero) {
        this.nome = validarNome(nome);
        this.email = validarEmail(email);
        this.cep = validarCep(cep);
        this.numero = validarNumero(numero);
    }

    private String validarNome(String nome) {
        if (nome == null || nome.isBlank()) {
            throw new IllegalArgumentException("O nome é obrigatório!");
        }
        return nome;
    }

    private String validarEmail(String email) {
        if (email == null || email.isBlank() || !email.contains("@")) {
            throw new IllegalArgumentException("O email informado não é válido!");
        }
        return email;
    }

    private String validarSenha(String senha) {
        if (senha == null || senha.length() < 8 || senha.length() > 32) {
            throw new IllegalArgumentException("A senha deve ter entre 8 e 32 caracteres!");
        }
        return senha;
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

    public Long getId() {
        return id;
    }

    public String getNome() {
        return nome;
    }

    public String getEmail() {
        return email;
    }

    public String getSenha() {
        return senha;
    }

    public String getCep() {
        return cep;
    }

    public String getNumero() {
        return numero;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Usuario usuario = (Usuario) o;
        return id != null && id.equals(usuario.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(email);
    }
}
