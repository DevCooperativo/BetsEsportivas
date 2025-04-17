CREATE TABLE categoria (
    id SERIAL PRIMARY KEY,
    nome VARCHAR(255) NOT NULL
);

CREATE TABLE cliente (
    id SERIAL PRIMARY KEY,
    nome VARCHAR(255) NOT NULL,
    saldo NUMERIC(10, 2) NOT NULL,
    email VARCHAR(255) NOT NULL UNIQUE
);

CREATE TABLE atleta (
    id SERIAL PRIMARY KEY,
    nome VARCHAR(255) NOT NULL,
    vitorias INTEGER NOT NULL DEFAULT 0,
    participacoes INTEGER NOT NULL DEFAULT 0
);

CREATE TABLE competicao (
    id SERIAL PRIMARY KEY,
    nome VARCHAR(255) NOT NULL,
    data_cadastro DATE NOT NULL,
    data_abertura_apostas DATE NOT NULL,
    data_fechamento_apostas DATE NOT NULL,
    categoria_id INTEGER NOT NULL,
    FOREIGN KEY (categoria_id) REFERENCES categoria (id)
);

CREATE TABLE competidor (
    atleta_id INTEGER NOT NULL,
    competicao_id INTEGER NOT NULL,
    PRIMARY KEY (atleta_id, competicao_id),
    FOREIGN KEY (atleta_id) REFERENCES atleta (id),
    FOREIGN KEY (competicao_id) REFERENCES competicao (id)
);

CREATE TABLE aposta (
    id SERIAL PRIMARY KEY,
    cliente_id INTEGER NOT NULL,
    valor NUMERIC(10, 2) NOT NULL,
    atleta_id INTEGER NOT NULL,
    competicao_id INTEGER NOT NULL,
    FOREIGN KEY (cliente_id) REFERENCES cliente (id),
    FOREIGN KEY (atleta_id, competicao_id) REFERENCES competidor (atleta_id, competicao_id)
);