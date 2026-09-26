package br.edu.ufersa.pw.bairro.negocio;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

interface NegocioRepository extends JpaRepository<Negocio, Long> {

    // GET /api/v1/negocios - Feed e Busca, com bairro/categoria/q todos opcionais (null = sem filtro)
    // O CAST nos parametros de texto e necessario no Postgres: sem ele, um parametro nulo e
    // enviado como bytea e o banco falha com "function lower(bytea) does not exist".
    @Query("""
            SELECT n FROM Negocio n
            WHERE (CAST(:bairro AS string) IS NULL OR n.bairro = :bairro)
              AND (:categoria IS NULL OR n.categoria = :categoria)
              AND (CAST(:q AS string) IS NULL OR LOWER(n.nome) LIKE LOWER(CONCAT('%', CAST(:q AS string), '%')) OR LOWER(n.descricao) LIKE LOWER(CONCAT('%', CAST(:q AS string), '%')))
            """)
    Page<Negocio> buscar(String bairro, CategoriaNegocio categoria, String q, Pageable pageable);
}
