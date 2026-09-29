-- ATENÇÃO: o TRUNCATE abaixo apaga TODOS os dados do banco (todas as tabelas),
-- de propósito, para o seed sempre partir de um estado limpo e previsível. Só
-- rode isso enquanto o banco só tiver dado de teste/demonstração.
--
-- Popula uma linha de cada entidade, com nomes genéricos e texto simulado.
-- Não usa a API: insere direto no banco, então bypassa as regras de negócio
-- (por isso o negócio fica sem dono e já nasce com um pedido de reivindicação
-- pendente — é assim que essas duas coisas convivem sem violar regra nenhuma).
--
-- No fim, um SELECT mostra os ids gerados: copie-os para as variáveis do
-- topo do apresentacao.http (negocioSeedId, ofertaSeedId, reivindicacaoSeedId).
TRUNCATE TABLE avaliacoes,
avisos,
ofertas,
reivindicacoes,
negocios,
usuarios
RESTART IDENTITY CASCADE;

WITH
    novo_admin AS (
        INSERT INTO
            usuarios (
                nome,
                email,
                senha,
                cep,
                numero,
                role
            )
        VALUES (
                'Admin',
                'admin@bairro.com',
                '$2a$10$ICoUEMZqlgNcl65xbtdL8u8RPXeI2O8J.AuVE7MDVsfEEXDs7lOS2', -- senha: Admin123!
                '59600000',
                '1',
                'ADMIN'
            ) RETURNING id
    ),
    novo_usuario AS (
        INSERT INTO
            usuarios (
                nome,
                email,
                senha,
                cep,
                numero,
                role
            )
        VALUES (
                'Usuário Exemplo',
                'usuario.exemplo@bairro.com',
                '$2a$10$SXP2Dl1c4x4OADblRbikueql8.M7T8zG0LW.w6n7nDkyMuOkcoo0S', -- senha: Usuario123!
                '59600000',
                '100',
                'USER'
            ) RETURNING id
    ),
    novo_negocio AS (
        INSERT INTO
            negocios (
                nome,
                categoria,
                cep,
                numero,
                bairro,
                descricao,
                dono_id,
                version
            )
        VALUES (
                'Negócio Exemplo',
                'MERCADO',
                '59600000',
                '200',
                'Bairro Exemplo',
                'Negócio de exemplo, criado para demonstração.',
                NULL,
                0
            ) RETURNING id
    ),
    nova_oferta AS (
        INSERT INTO
            ofertas (
                negocio_id,
                nome,
                descricao,
                preco,
                criado_em
            )
        SELECT id, 'Oferta Exemplo', 'Oferta de exemplo, criada para demonstração.', 19.90, now()
        FROM novo_negocio RETURNING id
    ),
    novo_aviso AS (
        INSERT INTO
            avisos (
                negocio_id,
                autor_id,
                categoria,
                texto,
                criado_em
            )
        SELECT n.id, u.id, 'ATENDIMENTO', 'Aviso de exemplo, criado para demonstração.', now()
        FROM
            novo_negocio n,
            novo_usuario u RETURNING id
    ),
    nova_avaliacao AS (
        INSERT INTO
            avaliacoes (
                aviso_id,
                usuario_id,
                util,
                criado_em
            )
        SELECT a.id, u.id, true, now()
        FROM
            novo_aviso a,
            novo_usuario u RETURNING id
    ),
    nova_reivindicacao AS (
        INSERT INTO
            reivindicacoes (
                negocio_id,
                usuario_id,
                justificativa,
                status,
                criado_em
            )
        SELECT n.id, u.id, 'Justificativa de exemplo, criada para demonstração.', 'PENDENTE', now()
        FROM
            novo_negocio n,
            novo_usuario u RETURNING id
    )
SELECT (
        SELECT id
        FROM novo_admin
    ) AS admin_id,
    (
        SELECT id
        FROM novo_usuario
    ) AS usuario_id,
    (
        SELECT id
        FROM novo_negocio
    ) AS negocio_id,
    (
        SELECT id
        FROM nova_oferta
    ) AS oferta_id,
    (
        SELECT id
        FROM novo_aviso
    ) AS aviso_id,
    (
        SELECT id
        FROM nova_avaliacao
    ) AS avaliacao_id,
    (
        SELECT id
        FROM nova_reivindicacao
    ) AS reivindicacao_id;