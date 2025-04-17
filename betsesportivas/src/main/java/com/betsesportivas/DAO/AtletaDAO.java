package com.betsesportivas.DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.betsesportivas.Domain.Atleta;

public class AtletaDAO implements IBaseDAO<Atleta> {
    private Connection _conn;

    @Override
    public void Connect(Connection conn) {
        _conn = conn;
    }

    @Override
    public List<Atleta> BuscarTodos() throws SQLException {
        List<Atleta> atletas = new ArrayList<>();
        PreparedStatement sql = _conn.prepareStatement("SELECT * FROM atleta");
        ResultSet result = sql.executeQuery();
        while (result.next()) {
            int id = result.getInt("id");
            String nome = result.getString("nome");
            int vitorias = result.getInt("vitorias");
            int participacoes = result.getInt("participacoes");
            atletas.add(new Atleta(id, nome, vitorias, participacoes));
        }
        return atletas;
    }

    @Override
    public Atleta BuscarPorId(Integer id) throws SQLException {
        PreparedStatement sql = _conn.prepareStatement("SELECT * FROM atleta WHERE id = ?");
        sql.setInt(1, id);
        ResultSet result = sql.executeQuery();
        int resultId = result.getInt("id");
        String nome = result.getString("nome");
        int vitorias = result.getInt("vitorias");
        int participacoes = result.getInt("participacoes");

        return new Atleta(resultId, nome, vitorias, participacoes);

    }

    @Override
    public Atleta Criar(Atleta valor) throws SQLException {
        PreparedStatement sql = _conn
                .prepareStatement("INSERT INTO atleta(nome, vitorias, paticipacoes) VALUES(?,?,?)");
        sql.setString(1, valor.GetNome());
        sql.setInt(2, valor.GetVitorias());
        sql.setInt(3, valor.GetParticipacoes());
        sql.execute();
        return valor;
    }

    @Override
    public Atleta Editar(Atleta valor) throws SQLException {
        PreparedStatement sql = _conn
                .prepareStatement(
                        "UPDATE atleta SET nome = ?, vitorias=?, participacoes = ? WHERE id=?");
        sql.setString(1, valor.GetNome());
        sql.setInt(2, valor.GetVitorias());
        sql.setInt(3, valor.GetParticipacoes());
        sql.setInt(4, valor.GetId());
        sql.execute();
        return valor;
    }

    @Override
    public void Excluir(Integer id) throws SQLException {
        PreparedStatement sql = _conn.prepareStatement("DELETE FROM atleta WHERE id = ?");
        sql.setInt(1, id);
        sql.execute();
    }

}
