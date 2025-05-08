CREATE TABLE IF NOT EXISTS categoria (
    id SERIAL PRIMARY KEY,

    nome VARCHAR(255) NOT NULL,
    descricao VARCHAR(400) NOT NULL,
    cor VARCHAR(9) NOT NULL DEFAULT('#A1A1A111'),
    is_ativada BOOLEAN NOT NULL DEFAULT(TRUE),
    limite_participantes INTEGER

);

CREATE TABLE IF NOT EXISTS jogador (
    id SERIAL PRIMARY KEY,

    nome VARCHAR(255) NOT NULL,
    saldo NUMERIC(10, 2) NOT NULL,
    email VARCHAR(255) NOT NULL UNIQUE,
    nascimento TIMESTAMP NOT NULL,
    is_ativado BOOLEAN NOT NULL DEFAULT(TRUE)
);

CREATE TABLE IF NOT EXISTS atleta (
    id SERIAL PRIMARY KEY,

    nome VARCHAR(50) NOT NULL,
    sobrenome VARCHAR(100) NOT NULL,
    nascimento TIMESTAMP NOT NULL,
    sexo CHARACTER(1) NOT NULL,

    vitorias INTEGER NOT NULL DEFAULT 0,
    participacoes INTEGER NOT NULL DEFAULT 0
);

CREATE TABLE IF NOT EXISTS competicao (
    id SERIAL PRIMARY KEY,

    nome VARCHAR(255) NOT NULL,
    data_abertura_apostas TIMESTAMP NOT NULL,
    data_fechamento_apostas TIMESTAMP NOT NULL,
    data_ocorrencia_evento TIMESTAMP NOT NULL,
    categoria_id INTEGER NOT NULL,
    valor_maximo_aposta NUMERIC(11,2) NOT NULL,
    valor_minimo_aposta NUMERIC(11,2) NOT NULL,

    data_cadastro TIMESTAMP NOT NULL,
    FOREIGN KEY (categoria_id) REFERENCES categoria (id)
);

CREATE TABLE IF NOT EXISTS competidor (
    atleta_id INTEGER NOT NULL,
    competicao_id INTEGER NOT NULL,
    numero INTEGER NOT NULL,
    posicao_inicial INTEGER,
    posicao_final INTEGER,    

    PRIMARY KEY (atleta_id, competicao_id),
    FOREIGN KEY (atleta_id) REFERENCES atleta (id),
    FOREIGN KEY (competicao_id) REFERENCES competicao (id)
);

CREATE TABLE IF NOT EXISTS aposta (
    id SERIAL PRIMARY KEY,
    
    jogador_id INTEGER NOT NULL,
    valor NUMERIC(10, 2) NOT NULL,
    atleta_id INTEGER NOT NULL,
    competicao_id INTEGER NOT NULL,
    odd NUMERIC(5,2) NOT NULL DEFAULT 1.00,

    FOREIGN KEY (jogador_id) REFERENCES jogador (id),
    FOREIGN KEY (atleta_id, competicao_id) REFERENCES competidor (atleta_id, competicao_id),
    UNIQUE(jogador_id, atleta_id, competicao_id)
);