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
import com.betsesportivas.Domain.Competicao;
import com.betsesportivas.Helpers.DateConverterHelper;
import com.betsesportivas.QueryBuilder.QueryBuilder;

public class CompeticaoDAO implements ICompeticaoDAO<Competicao, CompeticaoDTO> {
    private Connection _conn;
    private CategoriaDAO _categoriaDAO = new CategoriaDAO();

    @Override
    public void Connect(Connection conn) {
        _conn = conn;
        _categoriaDAO.Connect(_conn);
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
        try {
            _conn.setAutoCommit(false);
            QueryBuilder qBuilder = new QueryBuilder();
            PreparedStatement sql = _conn.prepareStatement(
                    qBuilder.Delete("competidor").Where("competidor.competicao_id", true, "=", id)
                            .toString());
            sql.execute();

            qBuilder.emptyQuery();

            sql = _conn.prepareStatement(
                    qBuilder.Delete("competicao")
                            .Where("competicao.id", true, "=", id)
                            .toString());
            sql.execute();

            _conn.commit();

        } catch (Exception ex) {
            _conn.rollback();
            throw ex;
        }
    }

    @Override
    public CompeticaoDTO BuscarDTOPorId(int id) throws SQLException {

        throw new UnsupportedOperationException("Unimplemented method 'BuscarDTOPorId'");
    }

    @Override
    public List<CompeticaoDTO> BuscarTodosOsDTO() throws SQLException {
        List<CompeticaoDTO> competicoes = new ArrayList<>();
        PreparedStatement sql = _conn.prepareStatement(
                "SELECT com.*, SUM(ap.valor) as ValorEmJogo, CASE WHEN NOW() - com.data_fechamento_apostas > INTERVAL '1 second' THEN 'Encerrado' ELSE 'Aberta' END as Status FROM competicao com LEFT JOIN aposta ap ON ap.competicao_id = com.id JOIN categoria cat ON cat.id=com.categoria_id GROUP BY com.id, cat.id");
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
            double maximoApostas = result.getDouble("valor_maximo_aposta");
            double minimoApostas = result.getDouble("valor_minimo_aposta");
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
                LocalDateTime nascimento = innerResult.getTimestamp("nascimento").toLocalDateTime();
                Character sexo = innerResult.getString("sexo").charAt(0);
                int innerVitorias = innerResult.getInt("vitorias");
                int innerParticipacoes = innerResult.getInt("participacoes");
                competidorDTO.add(new CompetidorDTO(
                        new AtletaDTO(innerId, innerNome, innerSobrenome, sexo, nascimento,
                                innerVitorias,
                                innerParticipacoes),
                        innerCompeticao_id, innerNumero,
                        innerPosicao_final, innerPosicao_inicial));
            }
            competicoes.add(new CompeticaoDTO(id, nome, categoriaDTO, data_cadastro, data_abertura_apostas,
                    data_fechamento_apostas, data_ocorrencia_evento,
                    valorEmJogo, status, competidorDTO, maximoApostas, minimoApostas));
        }

        return competicoes;
    }

    @Override
    public CompeticaoDTO EditarPorDTO(CompeticaoDTO valor) throws SQLException {
        // // TODO Auto-generated method stub
        try {
            _conn.setAutoCommit(false);
            QueryBuilder qBuilder = new QueryBuilder();
            List<CompetidorDTO> competicaoAtletas = valor.Competidores;
            PreparedStatement sql;
            List<Integer> excluidos = new LinkedList<>();
            // #region transaction
            if (competicaoAtletas.size() > 0) {
                sql = _conn.prepareStatement(
                        qBuilder.Delete("competidor")
                                .WhereIn("atleta_id", "competidor", competicaoAtletas
                                        .stream().map(x -> x.AtletaDTO.getId())
                                        .collect(Collectors.toList()),
                                        false)
                                .Where("competidor.competicao_id", true, "=",
                                        valor.getId())
                                .Returning("competidor", Arrays.asList("atleta_id"))
                                .toString());
                ResultSet result = sql.executeQuery();
                while (result.next()) {
                    excluidos.add(result.getInt("atleta_id"));
                }
            } else {
                sql = _conn.prepareStatement(
                        "DELETE FROM competidor WHERE competicao_id = ? RETURNING atleta_id");
                sql.setInt(1, valor.getId());
                ResultSet result = sql.executeQuery();
                while (result.next()) {
                    excluidos.add(result.getInt("atleta_id"));
                }
            }
            // #endregion

            // #region transaction
            for (int i : excluidos) {
                sql = _conn.prepareStatement(
                        "UPDATE atleta SET participacoes = participacoes - 1 WHERE id = ?;");
                sql.setInt(1, i);
                sql.execute();
            }
            // #endregion

            // #region transaction

            for (CompetidorDTO c : competicaoAtletas.stream()
                    .filter(x -> !excluidos.contains(x.AtletaDTO.getId()))
                    .collect(Collectors.toList())) {
                sql = _conn.prepareStatement(
                        "SELECT posicao_inicial, posicao_final, numero, atleta_id FROM competidor JOIN atleta ON atleta.id = competidor.atleta_id WHERE competidor.competicao_id = ? AND atleta.id = ? GROUP BY competidor.posicao_inicial, competidor.posicao_final, competidor.numero, atleta_id");
                sql.setInt(1, valor.getId());
                sql.setInt(2, c.getAtleta_id());
                ResultSet result = sql.executeQuery();
                while (result.next()) {
                    if (result.getInt("posicao_inicial") != c.getPosicao_inicial()) {
                        sql = _conn.prepareStatement(
                                "UPDATE competidor SET posicao_inicial = ? WHERE atleta_id = ? AND competicao_id = ?");
                        sql.setInt(1, c.getPosicao_inicial());
                        sql.setInt(2, c.getAtleta_id());
                        sql.setInt(3, c.getCompeticao_id());
                        sql.execute();
                    }
                }
            }

            // #endregion

            qBuilder.emptyQuery();
            sql = _conn.prepareStatement(qBuilder
                    .Select(new String[] { "atleta_id" }, "competidor")
                    .From("competidor")
                    .Where(String.format("competidor.competicao_id = %s", valor.getId()))
                    .toString());
            List<Integer> restantes = new LinkedList<>();
            ResultSet result = sql.executeQuery();
            while (result.next()) {
                restantes.add(result.getInt("atleta_id"));
            }
            qBuilder.emptyQuery();
            for (CompetidorDTO cDto : valor.getCompetidores()) {
                if (!restantes.contains(cDto.getAtleta_id())) {

                    // #region transaction
                    sql = _conn.prepareStatement(
                            "INSERT INTO competidor (atleta_id, competicao_id, posicao_inicial, posicao_final, numero) VALUES(?,?,?,?,?);");
                    sql.setInt(1, cDto.getAtleta_id());
                    sql.setInt(2, valor.getId());
                    sql.setInt(3, cDto.getPosicao_inicial());
                    sql.setInt(4, cDto.getPosicao_final());
                    sql.setInt(5, cDto.getNumero());
                    sql.execute();
                    // #endregion

                    // #region transaction
                    sql = _conn.prepareStatement(
                            "UPDATE atleta SET participacoes = participacoes + 1 WHERE id = ?");
                    sql.setInt(1, cDto.getAtleta_id());
                    sql.execute();
                    // #endregion

                }

            }

            qBuilder.emptyQuery();
            sql = _conn.prepareStatement(
                    qBuilder
                            .Update("competicao")
                            .Set("nome", valor.getNome())
                            .Set("data_abertura_apostas", DateConverterHelper
                                    .ConvertLocalDateTimeToTimestamp(valor
                                            .getData_abertura_apostas())
                                    .toString())
                            .Set("data_fechamento_apostas", DateConverterHelper
                                    .ConvertLocalDateTimeToTimestamp(valor
                                            .getData_fechamento_apostas())
                                    .toString())
                            .Set("data_ocorrencia_evento", DateConverterHelper
                                    .ConvertLocalDateTimeToTimestamp(valor
                                            .getData_ocorrencia_evento())
                                    .toString())
                            .Set("categoria_id", valor.getCategoria().getId())
                            .Set("valor_maximo_aposta", valor.getValor_maximo_aposta())
                            .Set("valor_minimo_aposta", valor.getValor_minimo_aposta())
                            .Where(String.format("competicao.id = %d", valor.getId()))
                            .toString());
            sql.execute();
            _conn.commit();
            return valor;
        } catch (Exception e) {
            _conn.rollback();
            throw e;
        }
    }

    @Override
    public CompeticaoDTO CriarPorDTO(CompeticaoDTO valor) throws Exception {
        try {
            List<CompetidorDTO> competidorDTO = valor.Competidores;
            _conn.setAutoCommit(false);
            QueryBuilder qBuilder = new QueryBuilder();
            PreparedStatement sql;
            int idCompeticao = 0;
            // #region transaction
            sql = _conn.prepareStatement(qBuilder
                    .Insert("competicao",
                            Arrays.asList("nome", "data_abertura_apostas",
                                    "data_fechamento_apostas",
                                    "data_ocorrencia_evento", "categoria_id",
                                    "valor_maximo_aposta",
                                    "valor_minimo_aposta", "data_cadastro"))
                    .InsertValue(valor.getNome())
                    .InsertValue(valor.getData_abertura_apostas())
                    .InsertValue(valor.getData_fechamento_apostas().toString())
                    .InsertValue(valor.getData_ocorrencia_evento())
                    .InsertValue(valor.getCategoria().getId())
                    .InsertValue(valor.getValor_maximo_aposta())
                    .InsertValue(valor.getValor_minimo_aposta())
                    .InsertValue(valor.getData_cadastro().toString())
                    .EndInsertValue()
                    .Returning("competicao", Arrays.asList("id"))
                    .toString());
            ResultSet result = sql.executeQuery();
            if (result.next()) {
                idCompeticao = result.getInt("id");
            } else {
                throw new Exception("Competição não criada");
            }
            // #endregion
            qBuilder.emptyQuery();

            for (CompetidorDTO competidor : competidorDTO) {
                // #region
                sql = _conn.prepareStatement(qBuilder
                        .Insert("competidor",
                                Arrays.asList("atleta_id", "competicao_id", "numero",
                                        "posicao_inicial",
                                        "posicao_final"))
                        .InsertValue(competidor.getAtleta_id())
                        .InsertValue(idCompeticao)
                        .InsertValue(competidor.getNumero())
                        .InsertValue(competidor.getPosicao_inicial())
                        .InsertValue(competidor.getPosicao_final())
                        .EndInsertValue().toString());
                sql.execute();
                // #endregion

                // #region transaction
                sql = _conn.prepareStatement(
                        "UPDATE atleta SET participacoes = participacoes + 1 WHERE id = ?");
                sql.setInt(1, competidor.getAtleta_id());
                sql.execute();
                // #endregion

                qBuilder.emptyQuery();
            }
            _conn.commit();
            return valor;
        } catch (Exception e) {
            _conn.rollback();
            throw e;
        }
    }

    @Override
    public List<CompeticaoDTO> BuscarDTOsEmAberto() throws SQLException {
        try {
            _conn.setAutoCommit(false);
            List<CompeticaoDTO> competicoes = new ArrayList<>();
            PreparedStatement sql = _conn.prepareStatement(
                    "SELECT \r\n" + //
                            "\tcom.*, \r\n" + //
                            "\tSUM(ap.valor) as ValorEmJogo, \r\n" + //
                            "\tCASE WHEN NOW() > com.data_fechamento_apostas THEN \r\n" + //
                            "\t\tCASE \r\n" + //
                            "\t\t\tWHEN NOW() > com.data_ocorrencia_evento AND com.estado = 'E' THEN 'Evento Finalizado'\r\n"
                            + //
                            "\t\t\tWHEN NOW() > com.data_ocorrencia_evento AND NOT com.estado = 'E' THEN 'Aguardando Resultados'\r\n"
                            + //
                            "\t\t\tWHEN NOW() < com.data_ocorrencia_evento THEN 'Aguardando Início do evento'\r\n" + //
                            "\t\tEND\r\n" + //
                            "\t\tELSE 'Apostas Abertas'\r\n" + //
                            "\tEND as Status \r\n" + //
                            "\tFROM competicao com \r\n" + //
                            "\tLEFT JOIN aposta ap ON ap.competicao_id = com.id \r\n" + //
                            "\tJOIN categoria cat ON cat.id=com.categoria_id \r\n" + //
                            "\tWHERE NOT estado = 'E'  \r\n" + //
                            "\tGROUP BY com.id, cat.id;");
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
                double maximoApostas = result.getDouble("valor_maximo_aposta");
                double minimoApostas = result.getDouble("valor_minimo_aposta");
                Character estado = result.getString("estado").charAt(0);
                CategoriaDTO categoriaDTO = _categoriaDAO.BuscarDTOPorId(result.getInt("categoria_id"));
                double valorEmJogo = result.getDouble("valorEmJogo");
                String status = result.getString("Status");
                CompeticaoDTO competicaoDTO = new CompeticaoDTO();
                competicaoDTO.setId(id);
                competicaoDTO.setNome(nome);
                competicaoDTO.setCategoria(categoriaDTO);
                competicaoDTO.setData_cadastro(data_cadastro);
                competicaoDTO.setData_abertura_apostas(data_abertura_apostas);
                competicaoDTO.setData_fechamento_apostas(data_fechamento_apostas);
                competicaoDTO.setData_ocorrencia_evento(data_ocorrencia_evento);
                competicaoDTO.setValorEmJogo(valorEmJogo);
                competicaoDTO.setStatus(status);
                competicaoDTO.setEstado(estado);
                competicaoDTO.setValor_maximo_aposta(maximoApostas);
                competicaoDTO.setValor_minimo_aposta(minimoApostas);
                competicoes.add(competicaoDTO);
            }
            _conn.commit();
            return competicoes;
        } catch (Exception e) {
            _conn.rollback();
            throw e;
        }
    }

    @Override
    public List<CompeticaoDTO> BuscarDTOsEmAbertoComCompetidores() throws SQLException {
        try {
            _conn.setAutoCommit(false);
            List<CompeticaoDTO> competicoes = new ArrayList<>();
            PreparedStatement sql = _conn.prepareStatement(
                    "SELECT \r\n" + //
                            "\tcom.*, \r\n" + //
                            "\tSUM(ap.valor) as ValorEmJogo, \r\n" + //
                            "\tCASE WHEN NOW() > com.data_fechamento_apostas THEN \r\n" + //
                            "\t\tCASE \r\n" + //
                            "\t\t\tWHEN NOW() > com.data_ocorrencia_evento AND com.estado = 'E' THEN 'Evento Finalizado'\r\n"
                            + //
                            "\t\t\tWHEN NOW() > com.data_ocorrencia_evento AND NOT com.estado = 'E' THEN 'Aguardando Resultados'\r\n"
                            + //
                            "\t\t\tWHEN NOW() < com.data_ocorrencia_evento THEN 'Aguardando Início do evento'\r\n" + //
                            "\t\tEND\r\n" + //
                            "\t\tELSE 'Apostas Abertas'\r\n" + //
                            "\tEND as Status \r\n" + //
                            "\tFROM competicao com \r\n" + //
                            "\tLEFT JOIN aposta ap ON ap.competicao_id = com.id \r\n" + //
                            "\tJOIN categoria cat ON cat.id=com.categoria_id \r\n" + //
                            "\tWHERE NOT estado = 'E'  \r\n" + //
                            "\tGROUP BY com.id, cat.id;");
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
                double maximoApostas = result.getDouble("valor_maximo_aposta");
                double minimoApostas = result.getDouble("valor_minimo_aposta");
                Character estado = result.getString("estado").charAt(0);
                CategoriaDTO categoriaDTO = _categoriaDAO.BuscarDTOPorId(result.getInt("categoria_id"));
                double valorEmJogo = result.getDouble("valorEmJogo");
                String status = result.getString("Status");
                CompeticaoDTO competicaoDTO = new CompeticaoDTO();
                competicaoDTO.setId(id);
                competicaoDTO.setNome(nome);
                competicaoDTO.setCategoria(categoriaDTO);
                competicaoDTO.setData_cadastro(data_cadastro);
                competicaoDTO.setData_abertura_apostas(data_abertura_apostas);
                competicaoDTO.setData_fechamento_apostas(data_fechamento_apostas);
                competicaoDTO.setData_ocorrencia_evento(data_ocorrencia_evento);
                competicaoDTO.setValorEmJogo(valorEmJogo);
                competicaoDTO.setStatus(status);
                competicaoDTO.setEstado(estado);
                competicaoDTO.setValor_maximo_aposta(maximoApostas);
                competicaoDTO.setValor_minimo_aposta(minimoApostas);
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
                    LocalDateTime nascimento = innerResult.getTimestamp("nascimento").toLocalDateTime();
                    Character sexo = innerResult.getString("sexo").charAt(0);
                    int innerVitorias = innerResult.getInt("vitorias");
                    int innerParticipacoes = innerResult.getInt("participacoes");
                    competidorDTO.add(new CompetidorDTO(
                            new AtletaDTO(innerId, innerNome, innerSobrenome, sexo, nascimento,
                                    innerVitorias,
                                    innerParticipacoes),
                            innerCompeticao_id, innerNumero,
                            innerPosicao_final, innerPosicao_inicial));
                }
                competicaoDTO.setCompetidores(competidorDTO);
                competicoes.add(competicaoDTO);

            }
            _conn.commit();
            return competicoes;
        } catch (Exception e) {
            _conn.rollback();
            throw e;
        }

    }

    @Override
    public void FinalizarCompeticao(CompeticaoDTO competicao) throws SQLException {
        try {
            _conn.setAutoCommit(false);

            // #region transaction
            PreparedStatement sql = _conn.prepareStatement("UPDATE competicao SET estado = 'E' WHERE id = ?");
            sql.setInt(1, competicao.getId());
            sql.execute();
            // #endregion

            List<Integer> vitoriosos = new LinkedList<>();
            // #region transaction
            for (CompetidorDTO c : competicao.getCompetidores()) {
                if (c.getPosicao_final() == 1) {
                    sql = _conn.prepareStatement("UPDATE atleta SET vitorias = vitorias + 1 WHERE id = ?");
                    sql.setInt(1, c.getAtleta_id());
                    sql.execute();
                    vitoriosos.add(c.getAtleta_id());
                }
            }
            // #endregion

            // #region
            for (int i : vitoriosos) {
                sql = _conn.prepareStatement("SELECT \r\n" + //
                        "\taposta.id as aposta_id,\r\n" + //
                        "\taposta.jogador_id as aposta_jogador_id,\r\n" + //
                        "\taposta.valor as aposta_valor,\r\n" + //
                        "\taposta.odd as aposta_odd\r\n" + //
                        "\tFROM aposta\r\n" + //
                        "WHERE \r\n" + //
                        "\taposta.competicao_id = ? AND\r\n" + //
                        "\taposta.atleta_id = ?;");
                sql.setInt(1, competicao.getId());
                sql.setInt(2, i);
                ResultSet result = sql.executeQuery();
                while (result.next()) {
                    int aposta_id = result.getInt("aposta_id");
                    int aposta_jogador_id = result.getInt("aposta_jogador_id");
                    double aposta_valor = result.getDouble("aposta_valor");
                    double aposta_odd = result.getDouble("aposta_odd");
                    // #region transaction
                    sql = _conn.prepareStatement("UPDATE jogador set saldo = saldo + (? * ?) WHERE id = ?");
                    sql.setDouble(1, aposta_valor);
                    sql.setDouble(2, aposta_odd);
                    sql.setInt(3, aposta_jogador_id);
                    sql.execute();
                    // #endregion
                }
            }
            // #endregion

            _conn.commit();
        } catch (Exception e) {
            _conn.rollback();
            throw e;
        }

    }

}
