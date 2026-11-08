```sql
-- ==========================================
-- ROTACENTER
-- CRIAÇÃO DO BANCO DE DADOS
-- Sistema de gerenciamento de clientes e pedidos
-- ==========================================

-- Criação do banco de dados
CREATE DATABASE rotacenter;

-- Seleciona o banco para utilização
USE rotacenter;


-- ==========================================
-- 1. TABELA CLIENTES
-- Armazena os dados cadastrais dos clientes.
-- CPF/CNPJ é único para evitar duplicidade.
-- ==========================================

CREATE TABLE clientes (
    id INT AUTO_INCREMENT PRIMARY KEY,
    nome VARCHAR(100) NOT NULL,
    cpf_cnpj VARCHAR(18) NOT NULL UNIQUE,
    endereco VARCHAR(150),
    bairro VARCHAR(100),
    cidade VARCHAR(100),
    cep VARCHAR(9)
);


-- ==========================================
-- 2. TABELA PEDIDOS
-- Armazena os pedidos realizados pelos clientes.
-- cliente_id estabelece o relacionamento com clientes.
-- Cada pedido pertence a um cliente.
-- ==========================================

CREATE TABLE pedidos (
    id INT AUTO_INCREMENT PRIMARY KEY,
    cliente_id INT NOT NULL,
    data_compra DATE NOT NULL,
    data_retirada DATE,
    status VARCHAR(20) NOT NULL DEFAULT 'PENDENTE',

    FOREIGN KEY (cliente_id) REFERENCES clientes(id)
);


-- ==========================================
-- 3. TABELA ITEM_PEDIDOS
-- Armazena os produtos vinculados a cada pedido.
-- pedido_id estabelece o relacionamento com pedidos.
-- Cada pedido pode possuir vários itens.
-- ==========================================

CREATE TABLE item_pedidos (
    id INT AUTO_INCREMENT PRIMARY KEY,
    pedido_id INT NOT NULL,
    produto VARCHAR(100) NOT NULL,
    quantidade INT NOT NULL,
    valor DECIMAL(10,2) NOT NULL,

    FOREIGN KEY (pedido_id) REFERENCES pedidos(id)
);