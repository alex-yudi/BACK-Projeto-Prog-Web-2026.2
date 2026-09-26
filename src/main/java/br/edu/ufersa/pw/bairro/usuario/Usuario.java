package br.edu.ufersa.pw.bairro.usuario;

import jakarta.persistence.*;
import org.hibernate.annotations.ColumnDefault;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "usuarios")
public class Usuario implements UserDetails {

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

    // Default no banco: linhas anteriores ao role viram USER, nunca ADMIN.
    @ColumnDefault("'USER'")
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private UserRole role;

    protected Usuario() {
    }

    // Recebe a senha ja codificada (BCrypt); a regra de tamanho da senha em texto puro
    // vive no UsuarioRegistroRequest. O cadastro sempre cria USER - ADMIN nunca vem do cliente.
    public Usuario(String nome, String email, String senhaCodificada, String cep, String numero) {
        this.nome = validarNome(nome);
        this.email = validarEmail(email);
        this.senha = validarSenhaCodificada(senhaCodificada);
        this.cep = validarCep(cep);
        this.numero = validarNumero(numero);
        this.role = UserRole.USER;
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

    private String validarSenhaCodificada(String senhaCodificada) {
        if (senhaCodificada == null || senhaCodificada.isBlank()) {
            throw new IllegalArgumentException("A senha é obrigatória!");
        }
        return senhaCodificada;
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

    // UserDetails: o "username" de login e o e-mail (e o subject do JWT).
    @Override
    public String getUsername() {
        return email;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return senha;
    }

    public String getCep() {
        return cep;
    }

    public String getNumero() {
        return numero;
    }

    public UserRole getRole() {return role;}

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

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities(){
        if(this.role == UserRole.ADMIN){
            return List.of(
                    new SimpleGrantedAuthority("ROLE_ADMIN"),
                    new SimpleGrantedAuthority("ROLE_USER")
            );
        }

        return List.of(new SimpleGrantedAuthority("ROLE_USER"));
    }
}
