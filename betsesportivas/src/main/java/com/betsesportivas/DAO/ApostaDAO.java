package com.betsesportivas.DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.betsesportivas.DTO.ApostaDTO;
import com.betsesportivas.Domain.Aposta;

public class ApostaDAO implements IBaseDAO<Aposta, ApostaDTO> {
    private Connection _conn;

    @Override
    public void Connect(Connection conn) {
        _conn = conn;
    }

    @Override
    public List<Aposta> BuscarTodos() throws SQLException {
        List<Aposta> apostas = new ArrayList<>();
        PreparedStatement sql = _conn.prepareStatement("SELECT * FROM aposta");
        ResultSet result = sql.executeQuery();
        while (result.next()) {
            Integer id = result.getInt("id");
            Integer jogador_id = result.getInt("jogador_id");
            Double valor = result.getDouble("valor");
            Integer atleta_id = result.getInt("atleta_id");
            Integer competicao_id = result.getInt("competicao_id");
            apostas.add(new Aposta(id, jogador_id, valor, atleta_id, competicao_id));
        }
        return apostas;
    }

    @Override
    public Aposta BuscarPorId(Integer id) throws SQLException {
        PreparedStatement sql = _conn.prepareStatement("SELECT * FROM aposta WHERE id = ?");
        sql.setInt(1, id);
        ResultSet result = sql.executeQuery();
        Integer result_id = result.getInt("id");
        Integer jogador_id = result.getInt("jogador_id");
        double valor = result.getDouble("valor");
        Integer atleta_id = result.getInt("atleta_id");
        Integer competicao_id = result.getInt("competicao_id");

        return new Aposta(result_id, jogador_id, valor, atleta_id, competicao_id);

    }

    @Override
    public Aposta Criar(Aposta valor) throws SQLException {
        PreparedStatement sql = _conn
                .prepareStatement("INSERT INTO aposta(jogador_id, valor, atleta_id, competicao_id) VALUES(?,?,?,?)");
        sql.setInt(1, valor.GetJogadorId());
        sql.setDouble(2, valor.GetValor());
        sql.setInt(3, valor.GetAtletaId());
        sql.setInt(4, valor.GetCompeticaoId());
        sql.execute();
        return valor;
    }

    @Override
    public Aposta Editar(Aposta valor) throws SQLException {
        PreparedStatement sql = _conn
                .prepareStatement(
                        "UPDATE aposta SET jogador_id = ?, valor=?, atleta_id = ?, competicao_id = ? WHERE id=?");
        sql.setInt(1, valor.GetJogadorId());
        sql.setDouble(2, valor.GetValor());
        sql.setInt(3, valor.GetAtletaId());
        sql.setInt(4, valor.GetCompeticaoId());
        sql.setInt(5, valor.getId());
        sql.execute();
        return valor;
    }

    @Override
    public void Excluir(int id) throws SQLException {
        PreparedStatement sql = _conn.prepareStatement("DELETE FROM aposta WHERE id = ?");
        sql.setInt(1, id);
        sql.execute();
    }

    @Override
    public ApostaDTO BuscarDTOPorId(int id) throws SQLException {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'BuscarDTOPorId'");
    }

    @Override
    public List<ApostaDTO> BuscarTodosOsDTO() throws SQLException {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'BuscarTodosOsDTO'");
    }

    @Override
    public ApostaDTO EditarPorDTO(ApostaDTO valor) throws SQLException {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'EditarPorDTO'");
    }

    @Override
    public ApostaDTO CriarPorDTO(ApostaDTO valor) throws SQLException {
        throw new UnsupportedOperationException("Not supported yet.");
    }

}
