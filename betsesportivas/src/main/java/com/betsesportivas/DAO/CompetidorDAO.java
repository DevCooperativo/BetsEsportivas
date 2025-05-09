package com.betsesportivas.DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import com.betsesportivas.DTO.AtletaDTO;
import com.betsesportivas.DTO.CompetidorDTO;
import com.betsesportivas.Domain.Atleta;
import com.betsesportivas.Domain.Competidor;
import com.betsesportivas.QueryBuilder.QueryBuilder;

public class CompetidorDAO implements ICompetidorDAO<Competidor, CompetidorDTO> {
    private Connection _conn;

    @Override
    public void Connect(Connection conn) {
        _conn = conn;
    }

    @Override
    public List<Competidor> BuscarTodos() throws SQLException {
        List<Competidor> Competidors = new ArrayList<>();
        QueryBuilder qBuilder = new QueryBuilder();
        PreparedStatement sql = _conn.prepareStatement(qBuilder.Select(null, "competidor")
                .Select(null, "atleta")
                .From("competidor").InnerJoin("atleta", "id", "=", "competidor").toString());
        ResultSet result = sql.executeQuery();
        while (result.next()) {
            int atleta_id = result.getInt("atleta_id");
            int competicao_id = result.getInt("competicao_id");
            int numero = result.getInt("numero");
            int posicao_inicial = result.getInt("posicao_inicial");
            int posicao_final = result.getInt("posicao_final");
            String nome = result.getString("nome");
            String sobrenome = result.getString("sobrenome");
            LocalDateTime nascimento = result.getTimestamp("nascimento").toLocalDateTime();
            Character sexo = result.getString("sexo").charAt(0);
            int vitorias = result.getInt("vitorias");
            int participacoes = result.getInt("participacoes");
            Competidors.add(
                    new Competidor(new Atleta(atleta_id, nome, sobrenome, nascimento, sexo,
                            vitorias, participacoes),
                            competicao_id, numero, posicao_inicial, posicao_final));
        }
        return Competidors;
    }

    @Override
    public Competidor BuscarPorId(Integer id) throws SQLException {
        PreparedStatement sql = _conn.prepareStatement(
                "SELECT comp.*, atle.* FROM competidor comp JOIN atleta atle ON atle.id = comp.atleta_id WHERE atle.id=1;");
        sql.setInt(1, id);
        ResultSet result = sql.executeQuery();
        int atleta_id = result.getInt("atleta_id");
        int competicao_id = result.getInt("competicao_id");
        int numero = result.getInt("numero");
        int posicao_inicial = result.getInt("posicao_inicial");
        int posicao_final = result.getInt("posicao_final");
        String nome = result.getString("nome");
        String sobrenome = result.getString("sobrenome");
        LocalDateTime nascimento = result.getTimestamp("nascimento").toLocalDateTime();
        Character sexo = result.getString("sexo").charAt(0);
        int vitorias = result.getInt("vitorias");
        int participacoes = result.getInt("participacoes");

        return new Competidor(new Atleta(atleta_id, nome, sobrenome, nascimento, sexo, vitorias, participacoes),
                competicao_id, numero, posicao_inicial, posicao_final);

    }

    @Override
    public Competidor Criar(Competidor valor) throws SQLException {
        PreparedStatement sql = _conn
                .prepareStatement(
                        "INSERT INTO competidor(atleta_id, competicao_id, numero, posicao_inicial, posicao_final) VALUES(?,?,?,?,?)");
        sql.setInt(1, valor.GetAtletaId());
        sql.setInt(2, valor.GetCompeticaoId());
        sql.setInt(3, valor.getNumero());
        sql.setInt(4, valor.getPosicao_inicial());
        sql.setNull(5, java.sql.Types.INTEGER);
        sql.execute();
        return valor;
    }

    @Override
    public Competidor Editar(Competidor valor) throws SQLException {
        PreparedStatement sql = _conn
                .prepareStatement(
                        "UPDATE competidor SET numero=?, posicao_inicial=?, posicao_final=? WHERE atleta_id = ? AND competicao_id = ? ");
        sql.setInt(1, valor.getNumero());
        sql.setInt(2, valor.getPosicao_inicial());
        sql.setNull(3, valor.getPosicao_final());
        sql.setInt(4, valor.GetAtletaId());
        sql.setInt(5, valor.GetCompeticaoId());
        return valor;
    }

    public void Excluir(int id) {
        throw new UnsupportedOperationException("not implemented");
    }

    public void Excluir(int atleta_id, int competicao_id) throws SQLException {
        PreparedStatement sql = _conn
                .prepareStatement("DELETE FROM competidor WHERE atleta_id = ? AND competicao_id = ?");
        sql.setInt(1, atleta_id);
        sql.setInt(2, competicao_id);

        sql.execute();
    }

    @Override
    public CompetidorDTO BuscarDTOPorId(int id) throws SQLException {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'BuscarDTOPorId'");
    }

    @Override
    public List<CompetidorDTO> BuscarTodosOsDTO() throws SQLException {
        List<CompetidorDTO> competidores = new LinkedList<>();
        PreparedStatement sql = _conn
                .prepareStatement(new QueryBuilder().Select(null, "atleta").From("atleta").toString());
        ResultSet result = sql.executeQuery();
        while (result.next()) {
            int resultId = result.getInt("id");
            String resultNome = result.getString("nome");
            String resultSobrenome = result.getString("sobrenome");
            LocalDateTime nascimento = result.getTimestamp("nascimento").toLocalDateTime();
            char sexo = result.getString("sexo").charAt(0);
            int resultVitorias = result.getInt("vitorias");
            int resultParticipacoes = result.getInt("participacoes");
            competidores
                    .add(new CompetidorDTO(
                            new AtletaDTO(resultId, resultNome, resultSobrenome, sexo,
                                    nascimento, resultVitorias,
                                    resultParticipacoes)));
        }
        return competidores;
    }

    @Override
    public CompetidorDTO EditarPorDTO(CompetidorDTO valor) throws SQLException {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'EditarPorDTO'");
    }

    @Override
    public List<CompetidorDTO> BuscarCompetidoresDisponiveisDTO(int idCompeticao) throws SQLException {
        List<CompetidorDTO> CompetidorDTO = new LinkedList<>();
        PreparedStatement sql = _conn
                .prepareStatement(new QueryBuilder()
                        .Select(null, "atleta")
                        .From("atleta")
                        .WhereIn("id", "atleta",
                                (new QueryBuilder()
                                        .Select(new String[] { "atleta_id" },
                                                "competidor")
                                        .From("competidor")
                                        .Where(String.format(
                                                "competidor.competicao_id = %d",
                                                idCompeticao))),
                                true)
                        .GroupBy("id", "atleta").toString());
        System.out.println(sql);
        ResultSet result = sql.executeQuery();
        while (result.next()) {
            int resultId = result.getInt("id");
            String resultNome = result.getString("nome");
            String resultSobrenome = result.getString("sobrenome");
            LocalDateTime nascimento = result.getTimestamp("nascimento").toLocalDateTime();
            char sexo = result.getString("sexo").charAt(0);
            int resultVitorias = result.getInt("vitorias");
            int resultParticipacoes = result.getInt("participacoes");
            CompetidorDTO
                    .add(new CompetidorDTO(
                            new AtletaDTO(resultId, resultNome, resultSobrenome, sexo,
                                    nascimento, resultVitorias,
                                    resultParticipacoes)));
        }
        return CompetidorDTO;
    }

    @Override
    public CompetidorDTO CriarPorDTO(CompetidorDTO valor) throws SQLException {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public List<CompetidorDTO> BuscarCompetidoresParticipandoSemAposta(int idCompeticao, int idJogador) throws SQLException {
        List<CompetidorDTO> listaCompetidores = new LinkedList();
        PreparedStatement sql = _conn.prepareStatement(
                "SELECT  atleta.id AS atleta_id, atleta.nome AS atleta_nome, atleta.sobrenome AS atleta_sobrenome, atleta.sexo AS atleta_sexo, atleta.nascimento AS atleta_nascimento, atleta.vitorias AS atleta_vitorias, atleta.participacoes AS atleta_participacoes, competidor.competicao_id AS competidor_competicao_id, competidor.numero AS competidor_numero, competidor.posicao_inicial AS competidor_posicao_inicial, competidor.posicao_final AS competidor_posicao_final FROM  atleta JOIN competidor ON atleta.id = competidor.atleta_id JOIN competicao ON competicao.id = competidor.competicao_id LEFT JOIN aposta ON aposta.atleta_id = atleta.id AND aposta.competicao_id = competidor.competicao_id AND aposta.jogador_id = ? WHERE competidor.competicao_id = ? AND aposta.id IS NULL;");

        sql.setInt(1, idJogador);
        sql.setInt(2, idCompeticao);

        ResultSet result = sql.executeQuery();
        while (result.next()) {
            int atleta_id = result.getInt("atleta_id");
            String atleta_nome = result.getString("atleta_nome");
            String atleta_sobrenome = result.getString("atleta_sobrenome");
            Character atleta_sexo = result.getString("atleta_sexo").charAt(0);
            LocalDateTime atleta_nascimento = result.getTimestamp("atleta_nascimento").toLocalDateTime();
            int atleta_vitorias = result.getInt("atleta_vitorias");
            int atleta_participacoes = result.getInt("atleta_participacoes");
            int competidor_competicao_id = result.getInt("competidor_competicao_id");
            int competidor_numero = result.getInt("competidor_numero");
            int competidor_posicao_inicial = result.getInt("competidor_posicao_inicial");
            int competidor_posicao_final = result.getInt("competidor_posicao_final");
            listaCompetidores.add(new CompetidorDTO(
                    new AtletaDTO(atleta_id, atleta_nome, atleta_sobrenome, atleta_sexo,
                            atleta_nascimento, atleta_vitorias, atleta_participacoes),
                    competidor_competicao_id, competidor_numero, competidor_posicao_final,
                    competidor_posicao_inicial));
        }

        return listaCompetidores;
    }

}
