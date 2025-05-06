package com.betsesportivas.DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import com.betsesportivas.DTO.AtletaDTO;
import com.betsesportivas.Domain.Atleta;
import com.betsesportivas.Helpers.DateConverterHelper;

public class AtletaDAO implements IAtletaDAO<Atleta, AtletaDTO> {
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
            String sobrenome = result.getString("sobrenome");
            LocalDate nascimento = result.getDate("nascimento").toLocalDate();
            String sexo = result.getString("sexo");
            int vitorias = result.getInt("vitorias");
            int participacoes = result.getInt("participacoes");
            atletas.add(new Atleta(id, nome, sobrenome, nascimento, sexo, vitorias, participacoes));
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
        String sobrenome = result.getString("sobrenome");
        LocalDate nascimento = result.getDate("nascimento").toLocalDate();
        String sexo = result.getString("sexo");
        int vitorias = result.getInt("vitorias");
        int participacoes = result.getInt("participacoes");

        return new Atleta(resultId, nome, sobrenome, nascimento, sexo, vitorias, participacoes);

    }

    @Override
    public Atleta Criar(Atleta valor) throws SQLException {
        PreparedStatement sql = _conn
                .prepareStatement("INSERT INTO atleta(nome, vitorias, paticipacoes) VALUES(?,?,?)");
        sql.setString(1, valor.getNome());
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
        sql.setString(1, valor.getNome());
        sql.setInt(2, valor.GetVitorias());
        sql.setInt(3, valor.GetParticipacoes());
        sql.setInt(4, valor.getId());
        sql.execute();
        return valor;
    }

    @Override
    public void Excluir(int id) throws SQLException {
        PreparedStatement sql = _conn.prepareStatement("DELETE FROM atleta WHERE id = ?");
        sql.setInt(1, id);
        sql.execute();
    }

    @Override
    public AtletaDTO BuscarDTOPorId(int id) throws SQLException {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'BuscarDTOPorId'");
    }

    @Override
    public List<AtletaDTO> BuscarTodosOsDTO() throws SQLException {
        List<AtletaDTO> atletas = new ArrayList<>();
        PreparedStatement sql = _conn.prepareStatement("SELECT * FROM atleta");
        ResultSet result = sql.executeQuery();
        while (result.next()) {
            int id = result.getInt("id");
            String nome = result.getString("nome");
            String sobrenome = result.getString("sobrenome");
            LocalDate nascimento = result.getDate("nascimento").toLocalDate();
            char sexo = result.getString("sexo").charAt(0);
            int vitorias = result.getInt("vitorias");
            int participacoes = result.getInt("participacoes");
            atletas.add(new AtletaDTO(id, nome, sobrenome, sexo, nascimento, vitorias, participacoes));
        }
        return atletas;
    }

    @Override
    public AtletaDTO EditarPorDTO(AtletaDTO valor) throws SQLException {
        PreparedStatement sql = _conn
                .prepareStatement(
                        "UPDATE atleta SET nome = ?, sobrenome =? WHERE id=?");
        sql.setString(1, valor.getNome());
        sql.setString(2, valor.getSobrenome());
        sql.setInt(3, valor.getId());
        sql.execute();
        return valor;
    }

    @Override
    public List<AtletaDTO> BuscarAtletasDisponiveisDTO() throws SQLException {
        List<AtletaDTO> atletaDTO = new LinkedList<>();
        PreparedStatement sql = _conn
                .prepareStatement(
                        "SELECT atle.* FROM atleta AS atle;");
        ResultSet result = sql.executeQuery();
        while (result.next()) {
            int resultId = result.getInt("id");
            String resultNome = result.getString("nome");
            String resultSobrenome = result.getString("sobrenome");
            char sexo = result.getString("sexo").charAt(0);
            LocalDate nascimento = result.getDate("nascimento").toLocalDate();
            int resultVitorias = result.getInt("vitorias");
            int resultParticipacoes = result.getInt("participacoes");
            atletaDTO.add(new AtletaDTO(resultId, resultNome, resultSobrenome, sexo, nascimento, resultVitorias,
                    resultParticipacoes));
        }
        return atletaDTO;
    }

    @Override
    public List<AtletaDTO> BuscarAtletasDisponiveisDTO(int idCompeticao) throws SQLException {
        List<AtletaDTO> atletaDTO = new LinkedList<>();
        PreparedStatement sql = _conn
                .prepareStatement(
                        "SELECT atle.* FROM atleta AS atle WHERE atle.id NOT IN(SELECT comp.atleta_id FROM competidor AS comp WHERE comp.competicao_id = ?) GROUP BY atle.id;");
        sql.setInt(1, idCompeticao);
        ResultSet result = sql.executeQuery();
        while (result.next()) {
            int resultId = result.getInt("id");
            String resultNome = result.getString("nome");
            String resultSobrenome = result.getString("sobrenome");
            char sexo = result.getString("sexo").charAt(0);
            LocalDate nascimento = result.getDate("nascimento").toLocalDate();
            int resultVitorias = result.getInt("vitorias");
            int resultParticipacoes = result.getInt("participacoes");
            atletaDTO.add(new AtletaDTO(resultId, resultNome, resultSobrenome, sexo, nascimento, resultVitorias,
                    resultParticipacoes));
        }
        return atletaDTO;
    }

    @Override
    public AtletaDTO CriarPorDTO(AtletaDTO valor) throws SQLException {
        PreparedStatement sql = _conn
                .prepareStatement("INSERT INTO atleta(nome, sobrenome, nascimento, sexo) VALUES(?,?,?,?)");
        sql.setString(1, valor.getNome());
        sql.setString(2, valor.getSobrenome());
        sql.setTimestamp(3, DateConverterHelper.ConvertLocalDateToTimestamp(valor.getNascimento()));
        sql.setString(4, "" + valor.getSexo());
        sql.execute();
        return valor;
    }

}
