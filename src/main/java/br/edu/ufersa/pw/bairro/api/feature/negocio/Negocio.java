package br.edu.ufersa.pw.bairro.api.feature.negocio;

import jakarta.persistence.*;

@Entity
@Table(name = "negocios")
public class Negocio {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Adicione os outros campos conforme a necessidade futura

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
}