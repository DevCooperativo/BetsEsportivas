package com.betsesportivas.DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.betsesportivas.Domain.Categoria;

public class CategoriaDAO implements IBaseDAO<Categoria> {
    private Connection _conn;

    @Override
    public void Connect(Connection conn) {
        _conn = conn;
    }

    @Override
    public List<Categoria> BuscarTodos() throws SQLException {
        List<Categoria> categorias = new ArrayList<>();
        PreparedStatement sql = _conn.prepareStatement("SELECT * FROM categoria");
        ResultSet result = sql.executeQuery();
        while (result.next()) {
            Integer id = result.getInt("id");
            String nome = result.getString("nome");
            categorias.add(new Categoria(id, nome));
        }
        return categorias;
    }

    @Override
    public Categoria BuscarPorId(Integer id) throws SQLException {
        PreparedStatement sql = _conn.prepareStatement("SELECT * FROM categoria WHERE id = ?");
        sql.setInt(1, id);
        ResultSet result = sql.executeQuery();
        Integer resultId = result.getInt("id");
        String nome = result.getString("nome");

        return new Categoria(resultId, nome);

    }

    @Override
    public Categoria Criar(Categoria valor) throws SQLException {
        PreparedStatement sql = _conn
                .prepareStatement("INSERT INTO categoria(nome) VALUES(?)");
        sql.setString(1, valor.GetNome());
        sql.execute();
        return valor;
    }

    @Override
    public Categoria Editar(Categoria valor) throws SQLException {
        PreparedStatement sql = _conn
                .prepareStatement(
                        "UPDATE categoria SET nome = ? WHERE id=?");
        sql.setString(1, valor.GetNome());
        sql.setInt(2, valor.GetId());
        sql.execute();
        return valor;
    }

    @Override
    public void Excluir(Integer id) throws SQLException {
        PreparedStatement sql = _conn.prepareStatement("DELETE FROM categoria WHERE id = ?");
        sql.setInt(1, id);
        sql.execute();
    }

}
