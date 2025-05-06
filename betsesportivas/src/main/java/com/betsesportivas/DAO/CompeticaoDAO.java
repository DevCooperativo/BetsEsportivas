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
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

import com.betsesportivas.DTO.AtletaDTO;
import com.betsesportivas.DTO.CategoriaDTO;
import com.betsesportivas.DTO.CompeticaoDTO;
import com.betsesportivas.DTO.CompetidorDTO;
import com.betsesportivas.Domain.Categoria;
import com.betsesportivas.Domain.Competicao;
import com.betsesportivas.Helpers.DateConverterHelper;
import com.betsesportivas.QueryBuilder.QueryBuilder;

public class CompeticaoDAO implements ICompeticaoDAO<Competicao, CompeticaoDTO> {
    private Connection _conn;
    private IBaseDAO<Categoria, CategoriaDTO> _categoriaDAO;

    public CompeticaoDAO(IBaseDAO<Categoria, CategoriaDTO> categoriaDAO) {
        _categoriaDAO = categoriaDAO;
    }

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
            LocalDate data_abertura_apostas = LocalDate.ofInstant(
                    result.getDate("data_abertura_apostas").toInstant(),
                    ZoneId.systemDefault());
            LocalDate data_fechamento_apostas = LocalDate
                    .ofInstant(result.getDate("data_fechamento_apostas").toInstant(),
                            ZoneId.systemDefault());
            int categoria_id = result.getInt("categoria_id");
            double valor_limite_vencedor = result.getDouble("valor_limite_vencedor");
            competicoes.add(new Competicao(id, categoria_id, data_abertura_apostas, data_cadastro,
                    data_fechamento_apostas, nome, valor_limite_vencedor));
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
        LocalDate data_abertura_apostas = LocalDate.ofInstant(
                result.getDate("data_abertura_apostas").toInstant(),
                ZoneId.systemDefault());
        LocalDate data_fechamento_apostas = LocalDate
                .ofInstant(result.getDate("data_fechamento_apostas").toInstant(),
                        ZoneId.systemDefault());
        int categoria_id = result.getInt("categoria_id");
        double valor_limite_vencedor = result.getDouble("valor_limite_vencedor");

        return new Competicao(resultId, categoria_id, data_abertura_apostas, data_cadastro,
                data_fechamento_apostas, nome, valor_limite_vencedor);

    }

    @Override
    public Competicao Criar(Competicao valor) throws SQLException {
        PreparedStatement sql = _conn
                .prepareStatement(
                        "INSERT INTO competicao(nome, data_cadastro, data_abertura_apostas, data_fechamento_apostas, categoria_id) VALUES(?,?,?,?,?)");
        sql.setString(1, valor.getNome());
        sql.setDate(2, (Date) Date
                .from(valor.GetDataCadastro().atStartOfDay(ZoneId.systemDefault()).toInstant()));
        sql.setDate(3,
                (Date) Date.from(valor.GetDataAberturaApostas().atStartOfDay(ZoneId.systemDefault())
                        .toInstant()));
        sql.setDate(3,
                (Date) Date.from(valor.GetDataFechamentoApostas().atStartOfDay(ZoneId.systemDefault())
                        .toInstant()));
        sql.setInt(4, valor.GetCategoriaId());
        sql.execute();
        return valor;
    }

    @Override
    public Competicao Editar(Competicao valor) throws SQLException {
        PreparedStatement sql = _conn
                .prepareStatement(
                        "UPDATE competicao SET nome = ?, data_cadastro=?, data_abertura_apostas = ?, data_fechamento_apostas = ?, categoria_id = ? WHERE id=?");
        sql.setString(1, valor.getNome());
        sql.setDate(2, (Date) Date
                .from(valor.GetDataCadastro().atStartOfDay(ZoneId.systemDefault()).toInstant()));
        sql.setDate(3,
                (Date) Date.from(valor.GetDataAberturaApostas().atStartOfDay(ZoneId.systemDefault())
                        .toInstant()));
        sql.setDate(3,
                (Date) Date.from(valor.GetDataFechamentoApostas().atStartOfDay(ZoneId.systemDefault())
                        .toInstant()));
        sql.setInt(4, valor.GetCategoriaId());
        sql.setInt(5, valor.getId());
        sql.execute();
        return valor;
    }

    @Override
    public void Excluir(int id) throws SQLException {
        PreparedStatement sql = _conn.prepareStatement("DELETE FROM competicao WHERE id = ?");
        sql.setInt(1, id);
        sql.execute();
    }

    @Override
    public CompeticaoDTO BuscarDTOPorId(int id) throws SQLException {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'BuscarDTOPorId'");
    }

    @Override
    public List<CompeticaoDTO> BuscarTodosOsDTO() throws SQLException {
        List<CompeticaoDTO> competicoes = new ArrayList<>();
        PreparedStatement sql = _conn.prepareStatement("SELECT \r\n" + //
                "\tcom.*, \r\n" + //
                "\tSUM(ap.valor) as ValorEmJogo, \r\n" + //
                "\tCOUNT(apo.*) as quantidadeDeApostas, cat.*, \r\n" + //
                "\tCASE \r\n" + //
                "\t\tWHEN NOW() - com.data_fechamento_apostas > INTERVAL '24 Hours' \r\n" + //
                "\t\tTHEN 'Encerrado' ELSE 'Aberta' \r\n" + //
                "\t\tEND as Status\r\n" + //
                "\tFROM competicao com\r\n" + //
                "\tJOIN aposta ap ON ap.competicao_id = com.id \r\n" + //
                "\tJOIN categoria cat ON cat.id=com.categoria_id \r\n" + //
                "\tJOIN aposta apo ON apo.competicao_id=com.id \r\n" + //
                "\tGROUP BY com.id, cat.id;\t");
        ResultSet result = sql.executeQuery();
        while (result.next()) {
            Integer id = result.getInt("id");
            String nome = result.getString("nome");
            LocalDateTime data_cadastro = result.getTimestamp("data_cadastro").toLocalDateTime();
            LocalDateTime data_abertura_apostas = result.getTimestamp("data_abertura_apostas")
                    .toLocalDateTime();
            LocalDateTime data_fechamento_apostas = result.getTimestamp("data_fechamento_apostas")
                    .toLocalDateTime();
            LocalDateTime data_ocorrencia_evento = result.getTimestamp("data_ocorrencia_evento")
                    .toLocalDateTime();
            int quantidadeDeApostas = result.getInt("quantidadeDeApostas");
            CategoriaDTO categoriaDTO = _categoriaDAO.BuscarDTOPorId(result.getInt("categoria_id"));
            double valorEmJogo = result.getDouble("valorEmJogo");
            String status = result.getString("Status");
            PreparedStatement innerSql = _conn.prepareStatement(
                    "SELECT comp.*, atle.* FROM competidor as comp JOIN atleta atle ON atle.id = comp.atleta_id WHERE comp.competicao_id =?");
            innerSql.setInt(1, id);
            ResultSet innerResult = innerSql.executeQuery();
            List<CompetidorDTO> competidorDTO = new LinkedList<>();
            while (innerResult.next()) {
                int innerCompeticao_id = innerResult.getInt("competicao_id");
                int innerNumero = innerResult.getInt("numero");
                int innerPosicao_inicial = innerResult.getInt("posicao_inicial");
                int innerPosicao_final = innerResult.getInt("posicao_final");
                int innerId = innerResult.getInt("id");
                String innerNome = innerResult.getString("nome");
                String innerSobrenome = innerResult.getString("sobrenome");
                LocalDate nascimento = innerResult.getDate("nascimento").toLocalDate();
                char sexo = innerResult.getString("sexo").charAt(0);
                int innerVitorias = innerResult.getInt("vitorias");
                int innerParticipacoes = innerResult.getInt("participacoes");
                competidorDTO.add(new CompetidorDTO(
                        new AtletaDTO(innerId, innerNome, innerSobrenome, sexo, nascimento,
                                innerVitorias,
                                innerParticipacoes),
                        innerCompeticao_id, innerNumero, innerPosicao_inicial,
                        innerPosicao_final));
            }
            competicoes.add(new CompeticaoDTO(id, nome, categoriaDTO, data_cadastro, data_abertura_apostas,
                    data_fechamento_apostas, data_ocorrencia_evento, quantidadeDeApostas,
                    valorEmJogo, status, competidorDTO));
        }

        return competicoes;
    }

    @Override
    public CompeticaoDTO EditarPorDTO(CompeticaoDTO valor) throws SQLException {
        // TODO Auto-generated method stub
        try {
            _conn.setAutoCommit(false);
            QueryBuilder qBuilder = new QueryBuilder();
            // #region atleta/competidor
            List<Integer> participando = new ArrayList<>(
                    valor.Competidores.stream().map(x -> x.AtletaDTO.getId()).collect(Collectors.toList()));
            PreparedStatement sql = _conn.prepareStatement(
                    QueryBuilder.Delete("competidor").WhereIn("atleta_id", "competidor", participando, false)
                            .Where("competidor.competicao_id", true, "=", valor.getId()).toString());
            sql.execute();

            sql = _conn.prepareStatement(qBuilder
                    .Select(new String[] { "atleta_id" }, "competidor")
                    .From("competidor")
                    .Where(String.format("competidor.competicao_id = %s", valor.getId())).toString());
            ResultSet result = sql.executeQuery();
            while (result.next()) {

            }

            for (CompetidorDTO cDto : valor.getCompetidores()) {
                sql = _conn.prepareStatement(qBuilder
                        .Insert("competidor",
                                Arrays.asList("atleta_id", "competicao_id", "posicao_inicial", "posicao_final",
                                        "numero"))
                        .InsertValue(cDto.getAtleta_id()).InsertValue(valor.getId())
                        .InsertValue(cDto.getPosicao_inicial()).InsertValue(cDto.getPosicao_final())
                        .InsertValue(cDto.getNumero()).toString());
                sql.execute();
            }
            // #endregion
            sql = _conn.prepareStatement(
                    new QueryBuilder()
                            .Update("competicao")
                            .Set("nome", valor.getNome())
                            .Set("data_abertura_apostas", DateConverterHelper
                                    .ConvertLocalDateTimeToTimestamp(valor.getData_abertura_apostas()).toString())
                            .Set("data_fechamento_apostas", DateConverterHelper
                                    .ConvertLocalDateTimeToTimestamp(valor.getData_fechamento_apostas()).toString())
                            .Set("data_ocorrencia_evento", DateConverterHelper
                                    .ConvertLocalDateTimeToTimestamp(valor.getData_ocorrencia_evento()).toString())
                            .Set("categoria_id", valor.getCategoria().getId())
                            .Set("valor_em_jogo", valor.getValorEmJogo())
                            .Where(String.format("competicao.id = %d", valor.getId())).toString());
            sql.execute();
            _conn.commit();
        } catch (Exception e) {
            _conn.rollback();
            throw e;
        }
    }

    @Override
    public CompeticaoDTO CriarPorDTO(CompeticaoDTO valor) throws SQLException {
        // List<AtletaDTO> atltetaDTO = valor.Competidores;
        // _conn.setAutoCommit(false);
        // for (AtletaDTO atletaDTO : atltetaDTO) {
        // PreparedStatement sql = _conn.prepareStatement("INSERT INTO
        // competidor(atleta_id, competicao_id VALUES");
        // }
        throw new UnsupportedOperationException();
    }

}
