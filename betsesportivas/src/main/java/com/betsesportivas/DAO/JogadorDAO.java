package com.betsesportivas.DAO;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;

import com.betsesportivas.DTO.JogadorDTO;
import com.betsesportivas.Domain.Jogador;
import com.betsesportivas.Helpers.DateConverterHelper;

public class JogadorDAO implements IJogadorDAO<Jogador, JogadorDTO> {
    private Connection _conn;

    @Override
    public void Connect(Connection conn) {
        _conn = conn;
    }

    @Override
    public List<Jogador> BuscarTodos() throws SQLException {
        List<Jogador> jogadores = new ArrayList<>();
        PreparedStatement sql = _conn.prepareStatement("SELECT * FROM jogador;");
        ResultSet result = sql.executeQuery();
        while (result.next()) {
            Integer id = result.getInt("id");
            double saldo = result.getDouble("saldo");
            String nome = result.getString("nome");
            String email = result.getString("email");
            LocalDate nascimento = result.getDate("nascimento").toLocalDate();
            boolean isAtivado = result.getBoolean("is_ativado");
            jogadores.add(new Jogador(id, nome, nascimento, saldo, email, isAtivado));
        }
        return jogadores;
    }

    @Override
    public Jogador BuscarPorId(Integer id) throws SQLException {
        PreparedStatement sql = _conn.prepareStatement("SELECT * FROM jogador WHERE id = ?;");
        sql.setInt(1, id);
        ResultSet result = sql.executeQuery();
        Integer resultId = result.getInt("id");
        double saldo = result.getDouble("saldo");
        String nome = result.getString("nome");
        String email = result.getString("email");
        LocalDate nascimento = result.getDate("nascimento").toLocalDate();
        boolean isAtivado = result.getBoolean("is_ativado");

        return new Jogador(resultId, nome, nascimento, saldo, email, isAtivado);

    }

    @Override
    public Jogador Criar(Jogador valor) throws SQLException {
        PreparedStatement sql = _conn
                .prepareStatement("INSERT INTO jogador(nome, saldo, email) VALUES(?,?,?);");
        sql.setString(1, valor.getNome());
        sql.setDouble(2, valor.GetSaldo());
        sql.setString(3, valor.GetEmail());
        sql.execute();
        return valor;
    }

    @Override
    public Jogador Editar(Jogador valor) throws SQLException {
        PreparedStatement sql = _conn
                .prepareStatement(
                        "UPDATE jogador SET nome = ?, saldo=?, email = ? WHERE id=?;");
        sql.setString(1, valor.getNome());
        sql.setDouble(2, valor.GetSaldo());
        sql.setString(3, valor.GetEmail());
        sql.setInt(4, valor.getId());
        sql.execute();
        return valor;
    }

    @Override
    public void Excluir(int id) throws SQLException {
        PreparedStatement sql = _conn.prepareStatement("DELETE FROM jogador WHERE id = ?;");
        sql.setInt(1, id);
        sql.execute();
    }

    @Override
    public JogadorDTO BuscarDTOPorId(int id) throws SQLException {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'BuscarDTOPorId'");
    }

    @Override
    public List<JogadorDTO> BuscarTodosOsDTO() throws SQLException {
        List<JogadorDTO> jogadores = new ArrayList<>();
        PreparedStatement sql = _conn.prepareStatement("SELECT * FROM jogador;");
        ResultSet result = sql.executeQuery();
        while (result.next()) {
            Integer id = result.getInt("id");
            double saldo = result.getDouble("saldo");
            String nome = result.getString("nome");
            String email = result.getString("email");
            LocalDate nascimento = result.getDate("nascimento").toLocalDate();
            boolean isAtivado = result.getBoolean("is_ativado");
            jogadores.add(new JogadorDTO(id, nome, nascimento, saldo, email, isAtivado));
        }
        return jogadores;
    }

    @Override
    public JogadorDTO EditarPorDTO(JogadorDTO valor) throws SQLException {
        PreparedStatement sql = _conn
                .prepareStatement(
                        "UPDATE jogador SET nome = ?, email = ? WHERE id=?;");
        sql.setString(1, valor.getNome());
        sql.setString(2, valor.getEmail());
        sql.setInt(3, valor.getId());
        sql.execute();
        return valor;
    }

    @Override
    public double AdicionarSaldo(int jogadorId, double valor) throws SQLException {
        PreparedStatement sqlRecuperar = _conn.prepareStatement("SELECT * FROM jogador WHERE id = ?;");
        sqlRecuperar.setInt(1, jogadorId);
        ResultSet result = sqlRecuperar.executeQuery();
        result.next();
        double saldoAtual = result.getDouble("saldo");

        PreparedStatement sql = _conn
                .prepareStatement("UPDATE jogador SET saldo =? WHERE id=?;");

        sql.setDouble(1, valor + saldoAtual);
        sql.setInt(2, jogadorId);
        sql.execute();

        ResultSet resultAtualizado = sqlRecuperar.executeQuery();
        resultAtualizado.next();
        double saldoAtualizado = resultAtualizado.getDouble("saldo");

        return saldoAtualizado;
    }

    @Override
    public JogadorDTO CriarPorDTO(JogadorDTO valor) throws SQLException {
        PreparedStatement sql = _conn
                .prepareStatement("INSERT INTO jogador(nome, saldo, email, nascimento) VALUES(?,?,?,?);");
        sql.setString(1, valor.getNome());
        sql.setDouble(2, valor.getSaldo());
        sql.setString(3, valor.getEmail());
        sql.setTimestamp(4, DateConverterHelper.ConvertLocalDateToTimestamp(valor.getNascimento()));
        sql.execute();
        return valor;
    }

}
