package br.edu.ufersa.pw.bairro.aviso;

import org.springframework.stereotype.Service;

// Ainda sem regra de dominio propria: o voto e um upsert idempotente sem checagem de
// autor/dono, e a unicidade (aviso_id + usuario_id) ja e garantida pela constraint do banco.
// Classe criada para manter o padrao Application/Domain Service de todas as features; passa
// a receber metodos quando alguma regra de negocio (ex.: limite de votos, impedir autovoto)
// for adicionada ao votar().
@Service
class AvaliacaoDomainService {
}
