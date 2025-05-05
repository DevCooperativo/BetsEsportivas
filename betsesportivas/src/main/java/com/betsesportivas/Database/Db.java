package com.betsesportivas.Database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Db {

    private String _url="jdbc:postgresql://127.0.0.1/postgres";
    private String _username="postgres";
    private String _password="postgres";

    public Db(){}

    public Db(String url, String username, String password) throws SQLException {
        this._url = url;
        this._username = username;
        this._password = password;
    }

    public Connection Connect() throws SQLException {
        return (DriverManager.getConnection(this._url, this._username, this._password));
    }

    public void initialize() throws SQLException{
        this.Connect().prepareStatement("CREATE TABLE IF NOT EXISTS categoria (\r\n" + //
                        "    id SERIAL PRIMARY KEY,\r\n" + //
                        "    nome VARCHAR(255) NOT NULL\r\n" + //
                        ");\r\n" + //
                        "\r\n" + //
                        "CREATE TABLE IF NOT EXISTS jogador (\r\n" + //
                        "    id SERIAL PRIMARY KEY,\r\n" + //
                        "    nome VARCHAR(255) NOT NULL,\r\n" + //
                        "    saldo NUMERIC(10, 2) NOT NULL,\r\n" + //
                        "    email VARCHAR(255) NOT NULL UNIQUE\r\n" + //
                        ");\r\n" + //
                        "\r\n" + //
                        "CREATE TABLE IF NOT EXISTS atleta (\r\n" + //
                        "    id SERIAL PRIMARY KEY,\r\n" + //
                        "    nome VARCHAR(255) NOT NULL,\r\n" + //
                        "    vitorias INTEGER NOT NULL DEFAULT 0,\r\n" + //
                        "    participacoes INTEGER NOT NULL DEFAULT 0\r\n" + //
                        ");\r\n" + //
                        "\r\n" + //
                        "CREATE TABLE IF NOT EXISTS competicao (\r\n" + //
                        "    id SERIAL PRIMARY KEY,\r\n" + //
                        "    nome VARCHAR(255) NOT NULL,\r\n" + //
                        "    data_cadastro DATE NOT NULL,\r\n" + //
                        "    data_abertura_apostas DATE NOT NULL,\r\n" + //
                        "    data_fechamento_apostas DATE NOT NULL,\r\n" + //
                        "    categoria_id INTEGER NOT NULL,\r\n" + //
                        "    FOREIGN KEY (categoria_id) REFERENCES categoria (id)\r\n" + //
                        ");\r\n" + //
                        "\r\n" + //
                        "CREATE TABLE IF NOT EXISTS competidor (\r\n" + //
                        "    atleta_id INTEGER NOT NULL,\r\n" + //
                        "    competicao_id INTEGER NOT NULL,\r\n" + //
                        "    PRIMARY KEY (atleta_id, competicao_id),\r\n" + //
                        "    FOREIGN KEY (atleta_id) REFERENCES atleta (id),\r\n" + //
                        "    FOREIGN KEY (competicao_id) REFERENCES competicao (id)\r\n" + //
                        ");\r\n" + //
                        "\r\n" + //
                        "CREATE TABLE IF NOT EXISTS aposta (\r\n" + //
                        "    id SERIAL PRIMARY KEY,\r\n" + //
                        "    jogador_id INTEGER NOT NULL,\r\n" + //
                        "    valor NUMERIC(10, 2) NOT NULL,\r\n" + //
                        "    atleta_id INTEGER NOT NULL,\r\n" + //
                        "    competicao_id INTEGER NOT NULL,\r\n" + //
                        "    FOREIGN KEY (jogador_id) REFERENCES jogador (id),\r\n" + //
                        "    FOREIGN KEY (atleta_id, competicao_id) REFERENCES competidor (atleta_id, competicao_id)\r\n" + //
                        ");").execute();
    }
}
