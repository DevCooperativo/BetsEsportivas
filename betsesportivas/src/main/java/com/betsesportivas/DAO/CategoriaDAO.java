package com.betsesportivas.DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import com.betsesportivas.DTO.CategoriaDTO;
import com.betsesportivas.Domain.Categoria;

public class CategoriaDAO implements IBaseDAO<Categoria, CategoriaDTO> {
    private Connection _conn;

    public CategoriaDAO() {
    }

    public CategoriaDAO(Connection conn) throws SQLException {
        _conn = conn;
    }

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
        sql.setString(1, valor.getNome());
        sql.execute();
        return valor;
    }

    @Override
    public Categoria Editar(Categoria valor) throws SQLException {
        PreparedStatement sql = _conn
                .prepareStatement(
                        "UPDATE categoria SET nome = ? WHERE id=?");
        sql.setString(1, valor.getNome());
        sql.setInt(2, valor.GetId());
        sql.execute();
        return valor;
    }

    @Override
    public void Excluir(int id) throws SQLException {
        PreparedStatement sql = _conn.prepareStatement("DELETE FROM categoria WHERE id = ?");
        sql.setInt(1, id);
        sql.execute();
    }

    @Override
    public CategoriaDTO BuscarDTOPorId(int id) throws SQLException {
        PreparedStatement sql = _conn.prepareStatement(
                "SELECT cat.*, COUNT(com.id) as vezesUtilizada FROM categoria cat JOIN competicao as com ON com.categoria_id = cat.id WHERE cat.id= ? GROUP BY cat.id ");
        sql.setInt(1, id);
        ResultSet result = sql.executeQuery();
        result.next();
        int resultId = result.getInt("id");
        String nome = result.getString("nome");
        String descricao = result.getString("descricao");
        int maxParticipantes = result.getInt("limite_participantes");
        String cor = result.getString("cor");
        int vezesUtilizada = result.getInt("vezesUtilizada");
        boolean isAtiva = result.getBoolean("is_ativada");
        return new CategoriaDTO(resultId, nome, descricao, cor, vezesUtilizada, maxParticipantes, isAtiva);
    }

    @Override
    public List<CategoriaDTO> BuscarTodosOsDTO() throws SQLException {
        PreparedStatement sql = _conn.prepareStatement(
                "SELECT cat.*, COUNT(com.id) as vezesUtilizada FROM categoria cat LEFT JOIN competicao as com ON com.categoria_id = cat.id GROUP BY cat.id");
        ResultSet result = sql.executeQuery();
        List<CategoriaDTO> categoriaDTO = new LinkedList<>();
        while (result.next()) {
            int resultId = result.getInt("id");
            String nome = result.getString("nome");
            String descricao = result.getString("descricao");
            int maxParticipantes = result.getInt("limite_participantes");
            String cor = result.getString("cor");
            boolean isAtiva = result.getBoolean("is_ativada");
            int vezesUtilizada = result.getInt("vezesUtilizada");
            categoriaDTO.add(new CategoriaDTO(resultId, nome, descricao, cor, vezesUtilizada, maxParticipantes, isAtiva));
        }
        return categoriaDTO;
    }

    @Override
    public CategoriaDTO EditarPorDTO(CategoriaDTO valor) throws SQLException {
        PreparedStatement sql = _conn.prepareStatement(
                "UPDATE categoria SET nome = ?, descricao = ?, cor = ?, is_ativada = ?, limite_participantes = ?  WHERE id = ?;");

        sql.setString(1, valor.getNome());
        sql.setString(2, valor.getDescricao());
        sql.setString(3, valor.getCor());
        sql.setBoolean(4, valor.isAtiva());
        sql.setInt(5, valor.getMaxParticipantes());
        sql.setInt(6, valor.getId());

        sql.execute();
        return valor;
    }

    @Override
    public CategoriaDTO CriarPorDTO(CategoriaDTO valor) throws SQLException {
        PreparedStatement sql = _conn.prepareStatement(
                "INSERT INTO categoria (nome, descricao, cor, is_ativada, limite_participantes) VALUES (?, ?, ?, ?, ?);");

        sql.setString(1, valor.getNome());
        sql.setString(2, valor.getDescricao());
        sql.setString(3, valor.getCor());
        sql.setBoolean(4, true);
        sql.setInt(5, valor.getMaxParticipantes());

        sql.execute();
        return valor;
    }

}
