INSERT INTO categoria (nome, descricao, cor, is_ativada, limite_participantes) VALUES ('Futebol', 'Competições de futebol nacionais e internacionais.', 'FF5733', 'S',32);
INSERT INTO categoria (nome, descricao, cor, is_ativada, limite_participantes) VALUES('Basquete', 'Competição de basquete em nível nacional e internacional.', '33FF57', 'S', 16);
INSERT INTO categoria (nome, descricao, cor, is_ativada, limite_participantes) VALUES ('Correr', 'Corridas de atletismo de diferentes distâncias.', '3399FF', 'S', 8);
INSERT INTO categoria (nome, descricao, cor, is_ativada, limite_participantes) VALUES ('Tênis', 'Competições de tênis entre atletas individuais.', 'F1C40F', 'S', 64);

INSERT INTO cliente (nome, saldo, email, nascimento, is_ativado) VALUES ('Lucas Mazioli', 1500.00, 'lucas@example.com', '1994-07-10 00:00:00', 'S');
INSERT INTO cliente (nome, saldo, email, nascimento, is_ativado) VALUES ('Carlos Silva', 800.50, 'carlos@example.com', '1987-02-20 00:00:00', 'S');
INSERT INTO cliente (nome, saldo, email, nascimento, is_ativado) VALUES ('João Pereira', 250.75, 'joao@example.com', '1992-11-15 00:00:00', 'S');
INSERT INTO cliente (nome, saldo, email, nascimento, is_ativado) VALUES ('Maria Oliveira', 100.00, 'maria@example.com', '1990-05-25 00:00:00', 'S');

INSERT INTO atleta (nome, sobrenome, nascimento, sexo, vitorias, participacoes) VALUES ('Neymar', 'Silva Santos', '1992-02-05 00:00:00', 'M', 100, 150);
INSERT INTO atleta (nome, sobrenome, nascimento, sexo, vitorias, participacoes) VALUES ('LeBron', 'James', '1984-12-30 00:00:00', 'M', 200, 250);
INSERT INTO atleta (nome, sobrenome, nascimento, sexo, vitorias, participacoes) VALUES ('Usain', 'Bolt', '1986-08-21 00:00:00', 'M', 120, 130);
INSERT INTO atleta (nome, sobrenome, nascimento, sexo, vitorias, participacoes) VALUES ('Serena', 'Williams', '1981-09-26 00:00:00', 'F', 150, 180);

INSERT INTO competicao (nome, data_abertura_apostas, data_fechamento_apostas, data_ocorrencia_evento, categoria_id, valor_limite_vencedor, data_cadastro) VALUES ('Copa do Mundo', '2025-06-01 00:00:00', '2025-07-01 00:00:00','2025-07-01 15:00:00' , 1, 5000000.00, '2025-01-01 00:00:00');
INSERT INTO competicao (nome, data_abertura_apostas, data_fechamento_apostas, data_ocorrencia_evento, categoria_id, valor_limite_vencedor, data_cadastro) VALUES ('NBA Finals', '2025-06-15 00:00:00', '2025-07-15 00:00:00','2025-07-15 15:00:00' , 2, 1000000.00, '2025-03-01 00:00:00');
INSERT INTO competicao (nome, data_abertura_apostas, data_fechamento_apostas, data_ocorrencia_evento, categoria_id, valor_limite_vencedor, data_cadastro) VALUES ('Maratona de Nova York', '2025-07-01 00:00:00','2025-07-01 15:00:00' , '2025-07-10 00:00:00', 3, 50000.00, '2025-02-01 00:00:00');
INSERT INTO competicao (nome, data_abertura_apostas, data_fechamento_apostas, data_ocorrencia_evento, categoria_id, valor_limite_vencedor, data_cadastro) VALUES ('US Open de Tênis', '2025-08-01 00:00:00', '2025-08-15 00:00:00','2025-08-15 15:00:00' , 4, 200000.00, '2025-04-01 00:00:00');

INSERT INTO competidor (atleta_id, competicao_id, numero, posicao_inicial, posicao_final) VALUES (1, 1, 10, 1, 1);
INSERT INTO competidor (atleta_id, competicao_id, numero, posicao_inicial, posicao_final) VALUES (2, 2, 23, 1, 1);
INSERT INTO competidor (atleta_id, competicao_id, numero, posicao_inicial, posicao_final) VALUES (3, 3, 100, 1, 1);
INSERT INTO competidor (atleta_id, competicao_id, numero, posicao_inicial, posicao_final) VALUES (4, 4, 1, 1, 1); 

INSERT INTO aposta (cliente_id, valor, atleta_id, competicao_id, odd) VALUES (1, 100.00, 1, 1, 16.5);
INSERT INTO aposta (cliente_id, valor, atleta_id, competicao_id, odd) VALUES (2, 50.00, 2, 2, 5.2);
INSERT INTO aposta (cliente_id, valor, atleta_id, competicao_id, odd) VALUES (3, 25.00, 3, 3, 3.1);
INSERT INTO aposta (cliente_id, valor, atleta_id, competicao_id, odd) VALUES (4, 200.00, 4, 4, 7.8);  