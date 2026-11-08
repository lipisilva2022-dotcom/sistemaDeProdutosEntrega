```sql
-- ==========================================
-- ROTACENTER
-- ALTERAÇÕES E RESTRIÇÕES DO BANCO DE DADOS
-- ==========================================

USE rotacenter;


-- ==========================================
-- 1. ALTERAÇÃO DA TABELA PEDIDOS
-- Define os status permitidos para um pedido.
-- O status inicial será PENDENTE.
-- ==========================================

ALTER TABLE pedidos
MODIFY COLUMN status
ENUM('PENDENTE', 'FATURADO', 'ENTREGUE')
NOT NULL DEFAULT 'PENDENTE';


-- ==========================================
-- 2. RESTRIÇÃO DA DATA DE RETIRADA
-- Impede que a data de retirada seja anterior
-- à data da compra.
-- Também permite que a data de retirada seja NULL.
-- ==========================================

ALTER TABLE pedidos
ADD CONSTRAINT chk_data_retirada
CHECK (
    data_retirada IS NULL
    OR data_retirada >= data_compra
);


-- ==========================================
-- 3. RESTRIÇÃO DA QUANTIDADE
-- Impede o cadastro de itens com quantidade
-- igual ou inferior a zero.
-- ==========================================

ALTER TABLE item_pedidos
ADD CONSTRAINT chk_quantidade
CHECK (quantidade > 0);


-- ==========================================
-- 4. RESTRIÇÃO DO VALOR
-- Impede que um produto seja cadastrado
-- com valor negativo.
-- ==========================================

ALTER TABLE item_pedidos
ADD CONSTRAINT chk_valor
CHECK (valor >= 0);


-- ==========================================
-- FIM DAS ALTERAÇÕES DO BANCO DE DADOS
-- ==========================================
```
