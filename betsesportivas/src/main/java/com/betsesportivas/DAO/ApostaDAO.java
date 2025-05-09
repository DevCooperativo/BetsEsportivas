package com.betsesportivas.DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import com.betsesportivas.DTO.ApostaDTO;
import com.betsesportivas.Domain.Aposta;
import com.betsesportivas.QueryBuilder.QueryBuilder;

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
        List<ApostaDTO> apostaList = new LinkedList<>();
        QueryBuilder qBuilder = new QueryBuilder();
        PreparedStatement sql = _conn.prepareStatement(
                "SELECT aposta.id as aposta_id, aposta.jogador_id as aposta_jogador_id, aposta.valor as aposta_valor, aposta.atleta_id as aposta_atleta_id, aposta.competicao_id as aposta_competicao_id, aposta.odd as aposta_odd, competicao.nome as competicao_nome, (SELECT CONCAT(atleta.nome || ' ' || atleta.sobrenome) from atleta WHERE atleta.id = aposta.atleta_id) as atleta_nome, jogador.nome as jogador_nome, atleta.nome as atleta_nome from aposta JOIN competicao ON competicao.id = aposta.competicao_id JOIN atleta on aposta.atleta_id = atleta.id JOIN jogador ON jogador.id = aposta.jogador_id;");

        ResultSet result = sql.executeQuery();

        while (result.next()) {

            int aposta_id = result.getInt("aposta_id");
            int aposta_jogador_id = result.getInt("aposta_jogador_id");
            double aposta_valor = result.getDouble("aposta_valor");
            int aposta_atleta_id = result.getInt("aposta_atleta_id");
            int aposta_competicao_id = result.getInt("aposta_competicao_id");
            double aposta_odd = result.getDouble("aposta_odd");
            String competicao_nome = result.getString("competicao_nome");
            String atleta_nome = result.getString("atleta_nome");
            String jogador_nome = result.getString("jogador_nome");

            apostaList.add(new ApostaDTO(aposta_id, aposta_jogador_id, jogador_nome, aposta_valor, aposta_atleta_id,
                    atleta_nome, aposta_competicao_id, competicao_nome, aposta_odd));
        }
        return apostaList;
    }

    @Override
    public ApostaDTO EditarPorDTO(ApostaDTO valor) throws SQLException {
        try {
            _conn.setAutoCommit(false);
            PreparedStatement sql = _conn.prepareStatement("SELECT jogador.saldo, aposta.valor FROM jogador JOIN aposta ON aposta.jogador_id = jogador.id WHERE jogador.id = ? AND aposta.id = ?");
            sql.setInt(1, valor.getIdJogador());
            sql.setInt(2, valor.getId());
            ResultSet result = sql.executeQuery();
            Double valorOriginal = null;
            Double saldoOriginal = null;
            while (result.next()) {
                valorOriginal = result.getDouble("valor");
                saldoOriginal = result.getDouble("saldo");
            }
            sql = _conn.prepareStatement(
                    "UPDATE aposta SET jogador_id=?, valor=?, atleta_id=?, competicao_id=?, odd=? WHERE id = ?");
            sql.setInt(1, valor.getIdJogador());
            sql.setDouble(2, valor.getValor());
            sql.setInt(3, valor.getIdCompetidor());
            sql.setInt(4, valor.getIdCompeticao());
            sql.setDouble(5, valor.getOdd());
            sql.setInt(6, valor.getId());
            sql.execute();
            if (valor.getValor() != valorOriginal && valorOriginal != null) {
                sql = _conn.prepareStatement("UPDATE jogador SET saldo = saldo + ? - ? WHERE id = ?");
                sql.setDouble(1, valorOriginal);
                sql.setDouble(2, valor.getValor());
                sql.setInt(3, valor.getIdJogador());
                sql.execute();
            }
            _conn.commit();
            return valor;
        } catch (Exception e) {
            _conn.rollback();
            throw e;
        }
    }

    @Override
    public ApostaDTO CriarPorDTO(ApostaDTO valor) throws SQLException {
        try {
            _conn.setAutoCommit(false);
            PreparedStatement sql = _conn.prepareStatement(
                    "INSERT INTO aposta(jogador_id, valor, atleta_id, competicao_id, odd) values(?,?,?,?,?)");
            sql.setInt(1, valor.getIdJogador());
            sql.setDouble(2, valor.getValor());
            sql.setInt(3, valor.getIdCompetidor());
            sql.setInt(4, valor.getIdCompeticao());
            sql.setDouble(5, valor.getOdd());
            sql.execute();

            sql = _conn.prepareStatement("UPDATE jogador SET saldo = saldo - ? WHERE id = ?");
            sql.setDouble(1, valor.getValor());
            sql.setInt(2, valor.getIdJogador());
            sql.execute();
            _conn.commit();
            return valor;
        } catch (Exception e) {
            _conn.rollback();
            throw e;
        }

    }

}
