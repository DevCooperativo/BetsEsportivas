package com.betsesportivas.DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import com.betsesportivas.DTO.ClienteDTO;
import com.betsesportivas.Domain.Cliente;

public class ClienteDAO implements IBaseDAO<Cliente, ClienteDTO> {
    private Connection _conn;

    @Override
    public void Connect(Connection conn) {
        _conn = conn;
    }

    @Override
    public List<Cliente> BuscarTodos() throws SQLException {
        List<Cliente> clientes = new ArrayList<>();
        PreparedStatement sql = _conn.prepareStatement("SELECT * FROM cliente");
        ResultSet result = sql.executeQuery();
        while (result.next()) {
            Integer id = result.getInt("id");
            double saldo = result.getDouble("saldo");
            String nome = result.getString("nome");
            String email = result.getString("email");
            LocalDate nascimento = result.getDate("nascimento").toLocalDate();
            String isAtivado = result.getString("is_ativado");
            clientes.add(new Cliente(id, nome, nascimento, saldo, email, isAtivado));
        }
        return clientes;
    }

    @Override
    public Cliente BuscarPorId(Integer id) throws SQLException {
        PreparedStatement sql = _conn.prepareStatement("SELECT * FROM cliente WHERE id = ?");
        sql.setInt(1, id);
        ResultSet result = sql.executeQuery();
        Integer resultId = result.getInt("id");
        double saldo = result.getDouble("saldo");
        String nome = result.getString("nome");
        String email = result.getString("email");
        LocalDate nascimento = result.getDate("nascimento").toLocalDate();
        String isAtivado = result.getString("is_ativado");

        return new Cliente(resultId, nome, nascimento, saldo, email, isAtivado);

    }

    @Override
    public Cliente Criar(Cliente valor) throws SQLException {
        PreparedStatement sql = _conn
                .prepareStatement("INSERT INTO cliente(nome, saldo, email) VALUES(?,?,?)");
        sql.setString(1, valor.getNome());
        sql.setDouble(2, valor.GetSaldo());
        sql.setString(3, valor.GetEmail());
        sql.execute();
        return valor;
    }

    @Override
    public Cliente Editar(Cliente valor) throws SQLException {
        PreparedStatement sql = _conn
                .prepareStatement(
                        "UPDATE cliente SET nome = ?, saldo=?, email = ? WHERE id=?");
        sql.setString(1, valor.getNome());
        sql.setDouble(2, valor.GetSaldo());
        sql.setString(3, valor.GetEmail());
        sql.setInt(4, valor.GetId());
        sql.execute();
        return valor;
    }

    @Override
    public void Excluir(int id) throws SQLException {
        PreparedStatement sql = _conn.prepareStatement("DELETE FROM cliente WHERE id = ?");
        sql.setInt(1, id);
        sql.execute();
    }

    @Override
    public ClienteDTO BuscarDTOPorId(int id) throws SQLException {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'BuscarDTOPorId'");
    }

    @Override
    public List<ClienteDTO> BuscarTodosOsDTO() throws SQLException {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'BuscarTodosOsDTO'");
    }

    @Override
    public ClienteDTO EditarPorDTO(ClienteDTO valor) throws SQLException {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'EditarPorDTO'");
    }

    @Override
    public ClienteDTO CriarPorDTO(ClienteDTO valor) throws SQLException {
        throw new UnsupportedOperationException("Not supported yet.");
    }

}
