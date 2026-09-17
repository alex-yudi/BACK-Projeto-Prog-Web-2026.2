package br.edu.ufersa.pw.bairro.domain.repositories;

import br.edu.ufersa.pw.bairro.domain.entities.CategoriaNegocio;
import br.edu.ufersa.pw.bairro.domain.entities.Negocio;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface NegocioRepository extends JpaRepository<Negocio, Long> {

    // GET /negocios - Feed e Busca, com bairro/categoria/q todos opcionais (null = sem filtro)
    @Query("""
            SELECT n FROM Negocio n
            WHERE (:bairro IS NULL OR n.bairro = :bairro)
              AND (:categoria IS NULL OR n.categoria = :categoria)
              AND (:q IS NULL OR LOWER(n.nome) LIKE LOWER(CONCAT('%', :q, '%')) OR LOWER(n.descricao) LIKE LOWER(CONCAT('%', :q, '%')))
            """)
    Page<Negocio> buscar(String bairro, CategoriaNegocio categoria, String q, Pageable pageable);
}
