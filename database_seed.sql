INSERT INTO categoria (nome, descricao, cor, is_ativada, limite_participantes) VALUES ('Futebol', 'Competições de futebol nacionais e internacionais.', '#FF5733', TRUE,32);
INSERT INTO categoria (nome, descricao, cor, is_ativada, limite_participantes) VALUES('Basquete', 'Competição de basquete em nível nacional e internacional.', '#33FF57', TRUE, 16);
INSERT INTO categoria (nome, descricao, cor, is_ativada, limite_participantes) VALUES ('Correr', 'Corridas de atletismo de diferentes distâncias.', '#3399FF', TRUE, 8);
INSERT INTO categoria (nome, descricao, cor, is_ativada, limite_participantes) VALUES ('Tênis', 'Competições de tênis entre atletas individuais.', '#F1C40F', TRUE, 64);

INSERT INTO jogador (nome, saldo, email, nascimento, is_ativado) VALUES ('Lucas Mazioli', 1500.00, 'lucas@example.com', '1994-07-10 00:00:00', TRUE);
INSERT INTO jogador (nome, saldo, email, nascimento, is_ativado) VALUES ('Carlos Silva', 800.50, 'carlos@example.com', '1987-02-20 00:00:00', TRUE);
INSERT INTO jogador (nome, saldo, email, nascimento, is_ativado) VALUES ('João Pereira', 250.75, 'joao@example.com', '1992-11-15 00:00:00', TRUE);
INSERT INTO jogador (nome, saldo, email, nascimento, is_ativado) VALUES ('Maria Oliveira', 100.00, 'maria@example.com', '1990-05-25 00:00:00', TRUE);

INSERT INTO atleta (nome, sobrenome, nascimento, sexo, vitorias, participacoes) VALUES ('Neymar', 'Silva Santos', '1992-02-05 00:00:00', 'M', 100, 150);
INSERT INTO atleta (nome, sobrenome, nascimento, sexo, vitorias, participacoes) VALUES ('LeBron', 'James', '1984-12-30 00:00:00', 'M', 200, 250);
INSERT INTO atleta (nome, sobrenome, nascimento, sexo, vitorias, participacoes) VALUES ('Usain', 'Bolt', '1986-08-21 00:00:00', 'M', 120, 130);
INSERT INTO atleta (nome, sobrenome, nascimento, sexo, vitorias, participacoes) VALUES ('Serena', 'Williams', '1981-09-26 00:00:00', 'F', 150, 180);

INSERT INTO competicao (nome, data_abertura_apostas, data_fechamento_apostas, data_ocorrencia_evento, estado, categoria_id, valor_maximo_aposta, valor_minimo_aposta, data_cadastro) VALUES ('Copa do Mundo', '2025-05-11 00:00:00', '2025-05-13 00:00:00','2025-05-13 12:00:00' ,'E', 1, 100000.00, 10.00, '2025-05-01 00:00:00');
INSERT INTO competicao (nome, data_abertura_apostas, data_fechamento_apostas, data_ocorrencia_evento, estado, categoria_id, valor_maximo_aposta, valor_minimo_aposta, data_cadastro) VALUES ('NBA Finals', '2025-05-11 00:00:00', '2025-05-13 00:00:00','2025-05-13 12:00:00' ,'E', 2, 100000.00, 10.00, '2025-05-01 00:00:00');
INSERT INTO competicao (nome, data_abertura_apostas, data_fechamento_apostas, data_ocorrencia_evento, estado, categoria_id, valor_maximo_aposta, valor_minimo_aposta, data_cadastro) VALUES ('Maratona de Nova York', '2025-05-11 00:00:00','2025-05-13 15:00:00', '2025-05-13 02:00:00', 'E', 3, 100000.00, 10.00, '2025-05-01 00:00:00');
INSERT INTO competicao (nome, data_abertura_apostas, data_fechamento_apostas, data_ocorrencia_evento, estado, categoria_id, valor_maximo_aposta, valor_minimo_aposta, data_cadastro) VALUES ('US Open de Tênis', '2025-05-11 00:00:00', '2025-05-13 00:00:00','2025-05-13 12:00:00' ,'A', 4, 100000.00, 10.00, '2025-05-01 00:00:00');



INSERT INTO competidor (atleta_id, competicao_id, numero, posicao_inicial, posicao_final) VALUES (1, 1, 10, 1, 1);
INSERT INTO competidor (atleta_id, competicao_id, numero, posicao_inicial, posicao_final) VALUES (1, 2, 10, 1, 2);
INSERT INTO competidor (atleta_id, competicao_id, numero, posicao_inicial, posicao_final) VALUES (1, 3, 10, 1, 3);
INSERT INTO competidor (atleta_id, competicao_id, numero, posicao_inicial, posicao_final) VALUES (1, 4, 10, 1, 4);

INSERT INTO competidor (atleta_id, competicao_id, numero, posicao_inicial, posicao_final) VALUES (2, 1, 23, 1, 4);
INSERT INTO competidor (atleta_id, competicao_id, numero, posicao_inicial, posicao_final) VALUES (2, 2, 23, 1, 3);
INSERT INTO competidor (atleta_id, competicao_id, numero, posicao_inicial, posicao_final) VALUES (2, 3, 23, 1, 2);
INSERT INTO competidor (atleta_id, competicao_id, numero, posicao_inicial, posicao_final) VALUES (2, 4, 23, 1, 1);

INSERT INTO competidor (atleta_id, competicao_id, numero, posicao_inicial, posicao_final) VALUES (3, 1, 100, 1, 4);
INSERT INTO competidor (atleta_id, competicao_id, numero, posicao_inicial, posicao_final) VALUES (3, 2, 100, 1, 1);
INSERT INTO competidor (atleta_id, competicao_id, numero, posicao_inicial, posicao_final) VALUES (3, 3, 100, 1, 2);
INSERT INTO competidor (atleta_id, competicao_id, numero, posicao_inicial, posicao_final) VALUES (3, 4, 100, 1, 3);

INSERT INTO competidor (atleta_id, competicao_id, numero, posicao_inicial, posicao_final) VALUES (4, 1, 1, 1, 0);
INSERT INTO competidor (atleta_id, competicao_id, numero, posicao_inicial, posicao_final) VALUES (4, 2, 1, 1, 0);
INSERT INTO competidor (atleta_id, competicao_id, numero, posicao_inicial, posicao_final) VALUES (4, 3, 1, 1, 0);
INSERT INTO competidor (atleta_id, competicao_id, numero, posicao_inicial, posicao_final) VALUES (4, 4, 1, 1, 0);



INSERT INTO aposta (jogador_id, valor, atleta_id, competicao_id, odd) VALUES (1, 100.00, 1, 1, 1.5);
INSERT INTO aposta (jogador_id, valor, atleta_id, competicao_id, odd) VALUES (2, 50.00, 2, 2, 1.3);
INSERT INTO aposta (jogador_id, valor, atleta_id, competicao_id, odd) VALUES (3, 25.00, 3, 3, 1.5);

INSERT INTO aposta (jogador_id, valor, atleta_id, competicao_id, odd) VALUES (1, 14.00, 1, 4, 2.3);  
INSERT INTO aposta (jogador_id, valor, atleta_id, competicao_id, odd) VALUES (1, 23.00, 2, 4, 2.1);  
INSERT INTO aposta (jogador_id, valor, atleta_id, competicao_id, odd) VALUES (1, 21.00, 3, 4, 2.0);  
INSERT INTO aposta (jogador_id, valor, atleta_id, competicao_id, odd) VALUES (1, 20.00, 4, 4, 2.8); 

INSERT INTO aposta (jogador_id, valor, atleta_id, competicao_id, odd) VALUES (2, 14.00, 1, 4, 2.3);  
INSERT INTO aposta (jogador_id, valor, atleta_id, competicao_id, odd) VALUES (2, 23.00, 2, 4, 2.1);  
INSERT INTO aposta (jogador_id, valor, atleta_id, competicao_id, odd) VALUES (2, 21.00, 3, 4, 2.0);  
INSERT INTO aposta (jogador_id, valor, atleta_id, competicao_id, odd) VALUES (2, 30.00, 4, 4, 2.8);  

INSERT INTO aposta (jogador_id, valor, atleta_id, competicao_id, odd) VALUES (3, 14.00, 1, 4, 2.3);  
INSERT INTO aposta (jogador_id, valor, atleta_id, competicao_id, odd) VALUES (3, 23.00, 2, 4, 2.1);  
INSERT INTO aposta (jogador_id, valor, atleta_id, competicao_id, odd) VALUES (3, 21.00, 3, 4, 2.0);  
INSERT INTO aposta (jogador_id, valor, atleta_id, competicao_id, odd) VALUES (3, 200.00, 4, 4, 2.8);  

INSERT INTO aposta (jogador_id, valor, atleta_id, competicao_id, odd) VALUES (4, 14.00, 1, 4, 2.3);  
INSERT INTO aposta (jogador_id, valor, atleta_id, competicao_id, odd) VALUES (4, 23.00, 2, 4, 2.1);  
INSERT INTO aposta (jogador_id, valor, atleta_id, competicao_id, odd) VALUES (4, 21.00, 3, 4, 2.0);  
INSERT INTO aposta (jogador_id, valor, atleta_id, competicao_id, odd) VALUES (4, 200.00, 4, 4, 2.8);  