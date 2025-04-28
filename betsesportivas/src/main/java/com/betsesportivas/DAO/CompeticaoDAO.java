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
import java.util.LinkedList;
import java.util.List;

import com.betsesportivas.DTO.AtletaDTO;
import com.betsesportivas.DTO.CategoriaDTO;
import com.betsesportivas.DTO.CompeticaoDTO;
import com.betsesportivas.Domain.Categoria;
import com.betsesportivas.Domain.Competicao;

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
            LocalDateTime data_abertura_apostas = result.getTimestamp("data_abertura_apostas").toLocalDateTime();
            LocalDateTime data_fechamento_apostas = result.getTimestamp("data_fechamento_apostas").toLocalDateTime();
            LocalDateTime data_ocorrencia_evento = result.getTimestamp("data_ocorrencia_evento").toLocalDateTime();
            int quantidadeDeApostas = result.getInt("quantidadeDeApostas");
            CategoriaDTO categoriaDTO = _categoriaDAO.BuscarDTOPorId(result.getInt("categoria_id"));
            double valorEmJogo = result.getDouble("valorEmJogo");
            String status = result.getString("Status");
            PreparedStatement innerSql = _conn.prepareStatement("SELECT\r\n" + //
                    "\tatle.*,\r\n" + //
                    "\tcomp.*\r\n" + //
                    "\tFROM atleta atle\r\n" + //
                    "\tJOIN competidor comp\r\n" + //
                    "\t\tON comp.atleta_id = atle.id\r\n" + //
                    "\tJOIN competicao com \r\n" + //
                    "\t\tON com.id = comp.competicao_id\r\n" + //
                    "\tWHERE com.id = ?;");
            innerSql.setInt(1, id);
            ResultSet innerResult = innerSql.executeQuery();
            List<AtletaDTO> atletaDTO = new LinkedList<>();
            while (innerResult.next()) {
                int innerId = innerResult.getInt("id");
                String innerNome = innerResult.getString("nome");
                int innerVitorias = innerResult.getInt("vitorias");
                int innerParticipacoes = innerResult.getInt("participacoes");
                atletaDTO.add(new AtletaDTO(innerId, innerNome, innerVitorias, innerParticipacoes));
            }
            competicoes.add(new CompeticaoDTO(id, nome, categoriaDTO, data_cadastro, data_abertura_apostas,
                    data_fechamento_apostas, data_ocorrencia_evento, quantidadeDeApostas, valorEmJogo, status, atletaDTO));
        }

        return competicoes;
    }

    @Override
    public CompeticaoDTO EditarPorDTO(CompeticaoDTO valor) throws SQLException {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'EditarPorDTO'");
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
