package com.betsesportivas.DAO;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;

import com.betsesportivas.DTO.CompeticaoDTO;
import com.betsesportivas.Domain.Competicao;

public class CompeticaoDAO implements IBaseDAO<Competicao, CompeticaoDTO> {
    private Connection _conn;

    @Override
    public void Connect(Connection conn) {
        _conn = conn;
    }

    @Override
    public List<Competicao> BuscarTodos() throws SQLException {
        List<Competicao> competicoes = new ArrayList<>();
        PreparedStatement sql = _conn.prepareStatement("SELECT * FROM competicao");
        ResultSet result = sql.executeQuery();
        while (result.next()) {
            Integer id = result.getInt("id");
            String nome = result.getString("nome");
            LocalDate data_cadastro = LocalDate.ofInstant(result.getDate("data_cadastro").toInstant(),
                    ZoneId.systemDefault());
            LocalDate data_abertura_apostas = LocalDate.ofInstant(result.getDate("data_abertura_apostas").toInstant(),
                    ZoneId.systemDefault());
            LocalDate data_fechamento_apostas = LocalDate
                    .ofInstant(result.getDate("data_fechamento_apostas").toInstant(), ZoneId.systemDefault());
            int categoria_id = result.getInt("categoria_id");
            competicoes.add(new Competicao(id, nome, data_cadastro, data_abertura_apostas, data_fechamento_apostas,
                    categoria_id));
        }
        return competicoes;
    }

    @Override
    public Competicao BuscarPorId(Integer id) throws SQLException {
        PreparedStatement sql = _conn.prepareStatement("SELECT * FROM competicao WHERE id = ?");
        sql.setInt(1, id);
        ResultSet result = sql.executeQuery();
        Integer resultId = result.getInt("id");
        String nome = result.getString("nome");
        LocalDate data_cadastro = LocalDate.ofInstant(result.getDate("data_cadastro").toInstant(),
                ZoneId.systemDefault());
        LocalDate data_abertura_apostas = LocalDate.ofInstant(result.getDate("data_abertura_apostas").toInstant(),
                ZoneId.systemDefault());
        LocalDate data_fechamento_apostas = LocalDate
                .ofInstant(result.getDate("data_fechamento_apostas").toInstant(), ZoneId.systemDefault());
        int categoria_id = result.getInt("categoria_id");

        return new Competicao(resultId, nome, data_cadastro, data_abertura_apostas, data_fechamento_apostas,
                categoria_id);

    }

    @Override
    public Competicao Criar(Competicao valor) throws SQLException {
        PreparedStatement sql = _conn
                .prepareStatement(
                        "INSERT INTO competicao(nome, data_cadastro, data_abertura_apostas, data_fechamento_apostas, categoria_id) VALUES(?,?,?,?,?)");
        sql.setString(1, valor.GetNome());
        sql.setDate(2, (Date) Date.from(valor.GetDataCadastro().atStartOfDay(ZoneId.systemDefault()).toInstant()));
        sql.setDate(3,
                (Date) Date.from(valor.GetDataAberturaApostas().atStartOfDay(ZoneId.systemDefault()).toInstant()));
        sql.setDate(3,
                (Date) Date.from(valor.GetDataFechamentoApostas().atStartOfDay(ZoneId.systemDefault()).toInstant()));
        sql.setInt(4, valor.GetCategoriaId());
        sql.execute();
        return valor;
    }

    @Override
    public Competicao Editar(Competicao valor) throws SQLException {
        PreparedStatement sql = _conn
                .prepareStatement(
                        "UPDATE competicao SET nome = ?, data_cadastro=?, data_abertura_apostas = ?, data_fechamento_apostas = ?, categoria_id = ? WHERE id=?");
        sql.setString(1, valor.GetNome());
        sql.setDate(2, (Date) Date.from(valor.GetDataCadastro().atStartOfDay(ZoneId.systemDefault()).toInstant()));
        sql.setDate(3,
                (Date) Date.from(valor.GetDataAberturaApostas().atStartOfDay(ZoneId.systemDefault()).toInstant()));
        sql.setDate(3,
                (Date) Date.from(valor.GetDataFechamentoApostas().atStartOfDay(ZoneId.systemDefault()).toInstant()));
        sql.setInt(4, valor.GetCategoriaId());
        sql.setInt(5, valor.GetId());
        sql.execute();
        return valor;
    }

    @Override
    public void Excluir(int id) throws SQLException {
        PreparedStatement sql = _conn.prepareStatement("DELETE FROM competicao WHERE id = ?");
        sql.setInt(1, id);
        sql.execute();
    }

}
