INSERT INTO jogador VALUES (1, 'Lucas Mazioli', 1500.00, 'lucas@example.com', '1994-07-10 00:00:00', true);
INSERT INTO jogador VALUES (2, 'Carlos Silva', 800.50, 'carlos@example.com', '1987-02-20 00:00:00', true);
INSERT INTO jogador VALUES (3, 'João Pereira', 250.75, 'joao@example.com', '1992-11-15 00:00:00', true);
INSERT INTO jogador VALUES (4, 'Maria Oliveira', 100.00, 'maria@example.com', '1990-05-25 00:00:00', true);

INSERT INTO categoria VALUES (1, 'Futebol', 'Competições de futebol nacionais e internacionais.', '#FF5733', true, 32);
INSERT INTO categoria VALUES (2, 'Basquete', 'Competição de basquete em nível nacional e internacional.', '#33FF57', true, 16);
INSERT INTO categoria VALUES (3, 'Correr', 'Corridas de atletismo de diferentes distâncias.', '#3399FF', true, 8);
INSERT INTO categoria VALUES (4, 'Tênis', 'Competições de tênis entre atletas individuais.', '#F1C40F', true, 64);

INSERT INTO atleta VALUES (2, 'LeBron', 'James', '1984-12-30 00:00:00', 'M', 200, 250);
INSERT INTO atleta VALUES (3, 'Usain', 'Bolt', '1986-08-21 00:00:00', 'M', 120, 130);
INSERT INTO atleta VALUES (4, 'Serena', 'Williams', '1981-09-26 00:00:00', 'F', 150, 180);
INSERT INTO atleta VALUES (1, 'Neymar', 'Silva Santos', '1992-02-05 00:00:00', 'M', 100, 151);




INSERT INTO competicao VALUES (1, 'Copa do Mundo', '2025-06-01 00:00:00', '2025-06-02 11:00:00', '2025-06-02 12:00:00', 1, 10.00, 100000.00, 'E', '2025-06-01 00:00:00');
INSERT INTO competicao VALUES (2, 'NBA Finals', '2025-06-01 00:00:00', '2025-06-02 11:00:00', '2025-06-02 12:00:00', 2, 10.00, 100000.00, 'E', '2025-06-01 00:00:00');
INSERT INTO competicao VALUES (3, 'Maratona de Nova York', '2025-06-01 00:00:00', '2025-06-02 11:00:00', '2025-06-02 12:00:00', 3, 10.00, 100000.00, 'E', '2025-06-01 00:00:00');
INSERT INTO competicao VALUES (4, 'US Open de Tênis', '2025-06-01 00:00:00', '2025-06-02 11:00:00', '2025-06-12 15:00:00', 4, 10.00, 100000.00, 'A', '2025-06-01 00:00:00');


INSERT INTO competidor VALUES (1, 1, 10, 1, 1);
INSERT INTO competidor VALUES (2, 2, 23, 1, 1);
INSERT INTO competidor VALUES (3, 3, 100, 1, 1);
INSERT INTO competidor VALUES (4, 4, 1, 1, 1);
INSERT INTO competidor VALUES (1, 4, 0, 0, 0);
INSERT INTO competidor VALUES (2, 4, 2, 0, 0);




INSERT INTO aposta VALUES (1, 1, 100.00, 1, 1, 2.00);
INSERT INTO aposta VALUES (2, 1, 200.00, 2, 2, 2.00);
INSERT INTO aposta VALUES (3, 1, 300.00, 3, 3, 2.00);
INSERT INTO aposta VALUES (4, 1, 400.00, 4, 4, 2.00);
INSERT INTO aposta VALUES (5, 2, 50.00, 1, 1, 2.00);
INSERT INTO aposta VALUES (6, 3, 50.00, 2, 2, 2.00);
INSERT INTO aposta VALUES (7, 4, 50.00, 3, 3, 2.00);
INSERT INTO aposta VALUES (8, 2, 50.00, 4, 4, 2.00);


-- O jogador Lucas Mazioli não poderá realizar uma aposta pois ja recebeu 30% de lucro neste mês (mês de junho) = Resultado cadastrado após a data de término da Competição;
-- Não poderá ser adicionada nova competição pois a competição US Open de Tênis não foi finalizada = Näo podem existir duas Competições sem Resultados; Cliente com 10+ apostas no més näo pode apostar e se lucrou 30%+;