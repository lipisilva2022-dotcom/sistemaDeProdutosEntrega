```sql
-- ==========================================
-- ROTACENTER
-- PAINEL DE CONTROLE
-- Consultas para administração e acompanhamento
-- dos dados armazenados no banco.
-- ==========================================

USE rotacenter;


-- ==========================================
-- 1. CONSULTAS INDIVIDUAIS
-- Exibe todos os registros de cada tabela.
-- ==========================================

-- Clientes cadastrados
SELECT * FROM clientes;

-- Pedidos registrados
SELECT * FROM pedidos;

-- Produtos vinculados aos pedidos
SELECT * FROM item_pedidos;


-- ==========================================
-- 2. CONSULTA COMPLETA DOS PEDIDOS
-- Relaciona clientes, pedidos e produtos.
-- Também calcula o subtotal de cada item.
-- ==========================================

SELECT
    p.id AS pedido,
    c.nome AS cliente,
    p.data_compra,
    p.data_retirada,
    p.status,
    ip.produto,
    ip.quantidade,
    ip.valor,
    (ip.quantidade * ip.valor) AS subtotal

FROM pedidos p

INNER JOIN clientes c
    ON p.cliente_id = c.id

INNER JOIN item_pedidos ip
    ON p.id = ip.pedido_id;


-- ==========================================
-- 3. CONSULTA DE PEDIDOS PENDENTES
-- Exibe somente pedidos que ainda estão pendentes.
-- ==========================================

SELECT
    p.id AS pedido,
    c.nome AS cliente,
    p.data_compra,
    p.data_retirada,
    p.status

FROM pedidos p

INNER JOIN clientes c
    ON p.cliente_id = c.id

WHERE p.status = 'PENDENTE';


-- ==========================================
-- 4. HISTÓRICO DE PEDIDOS POR CLIENTE
-- Localiza os pedidos de um cliente
-- utilizando o CPF/CNPJ.
-- ==========================================

SELECT
    c.nome AS cliente,
    c.cpf_cnpj,
    p.id AS pedido,
    p.data_compra,
    p.data_retirada,
    p.status

FROM clientes c

INNER JOIN pedidos p
    ON c.id = p.cliente_id

WHERE c.cpf_cnpj = '12345678900';


-- ==========================================
-- 5. RESUMO FINANCEIRO DOS PEDIDOS
-- Calcula o valor total de cada pedido,
-- considerando todos os seus produtos.
-- ==========================================

SELECT
    p.id AS pedido,
    c.nome AS cliente,
    SUM(ip.quantidade * ip.valor) AS valor_total

FROM pedidos p

INNER JOIN clientes c
    ON p.cliente_id = c.id

INNER JOIN item_pedidos ip
    ON p.id = ip.pedido_id

GROUP BY p.id, c.nome;


-- ==========================================
-- 6. CONTROLE DE STATUS
-- Conta quantos pedidos existem em cada situação.
-- ==========================================

SELECT
    status,
    COUNT(*) AS quantidade_pedidos

FROM pedidos

GROUP BY status;


-- ==========================================
-- 7. PRODUTOS MAIS VENDIDOS
-- Soma as quantidades de cada produto
-- registrados nos pedidos.
-- ==========================================

SELECT
    produto,
    SUM(quantidade) AS quantidade_vendida

FROM item_pedidos

GROUP BY produto

ORDER BY quantidade_vendida DESC;


-- ==========================================
-- FIM DO PAINEL DE CONTROLE
-- ==========================================
```
