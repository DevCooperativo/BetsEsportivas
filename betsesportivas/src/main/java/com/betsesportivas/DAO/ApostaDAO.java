package com.betsesportivas.DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import com.betsesportivas.DTO.ApostaDTO;
import com.betsesportivas.DTO.AtletaDTO;
import com.betsesportivas.DTO.CompeticaoDTO;
import com.betsesportivas.DTO.CompetidorDTO;
import com.betsesportivas.DTO.JogadorDTO;
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
        PreparedStatement sql = _conn.prepareStatement("DELETE FROM aposta WHERE id = ? RETURNING jogador_id, valor");
        sql.setInt(1, id);
        ResultSet result = sql.executeQuery();
        while (result.next()) {
            sql = _conn.prepareStatement("UPDATE jogador SET saldo = saldo + ? WHERE id = ?");
            sql.setDouble(1, result.getDouble("valor"));
            sql.setInt(2, result.getInt("jogador_id"));
            sql.execute();
        }
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
                "SELECT \r\n" + //
                        "\taposta.id as aposta_id, \r\n" + //
                        "\taposta.jogador_id as aposta_jogador_id, \r\n" + //
                        "\taposta.valor as aposta_valor, \r\n" + //
                        "\taposta.atleta_id as aposta_atleta_id, \r\n" + //
                        "\taposta.competicao_id as aposta_competicao_id, \r\n" + //
                        "\taposta.odd as aposta_odd, \r\n" + //
                        "\tcompeticao.nome as competicao_nome, \r\n" + //
                        "\tcompeticao.data_abertura_apostas as competicao_data_abertura_apostas,\r\n" + //
                        "\tcompeticao.data_fechamento_apostas as competicao_data_fechamento_apostas,\r\n" + //
                        "\tcompeticao.data_ocorrencia_evento as competicao_data_ocorrencia_evento,\r\n" + //
                        "\tcompeticao.valor_minimo_aposta as competicao_valor_minimo_aposta, \r\n" + //
                        "\tcompeticao.valor_maximo_aposta as competicao_valor_maximo_aposta, \r\n" + //
                        "\tatleta.nome as atleta_nome,\r\n" + //
                        "\tatleta.sobrenome as atleta_sobrenome,\r\n" + //
                        "\tjogador.nome as jogador_nome, \r\n" + //
                        "\tjogador.saldo as jogador_saldo,\r\n" + //
                        "\tatleta.nome as atleta_nome \r\n" + //
                        "\tfrom aposta \r\n" + //
                        "\tJOIN competicao \r\n" + //
                        "\t\tON competicao.id = aposta.competicao_id \r\n" + //
                        "\tJOIN atleta \r\n" + //
                        "\t\tON aposta.atleta_id = atleta.id \r\n" + //
                        "\tJOIN jogador \r\n" + //
                        "\t\tON jogador.id = aposta.jogador_id;");

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
            String atleta_sobrenome = result.getString("atleta_sobrenome");
            LocalDateTime competicao_data_abertura_apostas = result.getTimestamp("competicao_data_abertura_apostas")
                    .toLocalDateTime();
            LocalDateTime competicao_data_fechamento_apostas = result.getTimestamp("competicao_data_fechamento_apostas")
                    .toLocalDateTime();
            LocalDateTime competicao_data_ocorrencia_evento = result.getTimestamp("competicao_data_ocorrencia_evento")
                    .toLocalDateTime();
            double competicao_valor_maximo_aposta = result.getDouble("competicao_valor_maximo_aposta");
            double competicao_valor_minimo_aposta = result.getDouble("competicao_valor_minimo_aposta");
            String jogador_nome = result.getString("jogador_nome");
            double jogador_saldo = result.getDouble("jogador_saldo");

            apostaList.add(new ApostaDTO(aposta_id, aposta_valor, aposta_odd,
                    new JogadorDTO(aposta_jogador_id, jogador_nome, jogador_saldo),
                    new CompeticaoDTO(aposta_competicao_id, competicao_nome, competicao_data_abertura_apostas,
                            competicao_data_fechamento_apostas, competicao_data_ocorrencia_evento,
                            competicao_valor_maximo_aposta,
                            competicao_valor_minimo_aposta),
                    new CompetidorDTO(new AtletaDTO(aposta_atleta_id, atleta_nome, atleta_sobrenome))));
        }
        return apostaList;
    }

    @Override
    public ApostaDTO EditarPorDTO(ApostaDTO valor) throws SQLException {
        try {
            _conn.setAutoCommit(false);
            PreparedStatement sql = _conn.prepareStatement(
                    "SELECT jogador.saldo, aposta.valor FROM jogador JOIN aposta ON aposta.jogador_id = jogador.id WHERE jogador.id = ? AND aposta.id = ?");
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
    public ApostaDTO CriarPorDTO(ApostaDTO valor) throws SQLException, Exception {
        try {
            _conn.setAutoCommit(false);
            PreparedStatement sql = _conn.prepareStatement("SELECT \r\n" + //
                    "    jogador.id AS jogador_id,\r\n" + //
                    "    jogador.nome AS jogador_nome,\r\n" + //
                    "    aposta_stats.total_apostas,\r\n" + //
                    "    aposta_stats.valor_total_apostado,\r\n" + //
                    "    aposta_stats.lucro,\r\n" + //
                    "    CASE \r\n" + //
                    "        WHEN aposta_stats.total_apostas < 10 \r\n" + //
                    "             AND aposta_stats.lucro < 0.3 * aposta_stats.valor_total_apostado \r\n" + //
                    "        THEN 1\r\n" + //
                    "        ELSE -1\r\n" + //
                    "    END AS status_aposta\r\n" + //
                    "FROM jogador\r\n" + //
                    "JOIN (\r\n" + //
                    "    SELECT \r\n" + //
                    "        aposta.jogador_id,\r\n" + //
                    "        COUNT(DISTINCT aposta.id) AS total_apostas,\r\n" + //
                    "        SUM(aposta.valor) AS valor_total_apostado,\r\n" + //
                    "        SUM(\r\n" + //
                    "            CASE \r\n" + //
                    "                WHEN aposta.atleta_id = competidor.atleta_id \r\n" + //
                    "                     AND competidor.posicao_final = 1 \r\n" + //
                    "                THEN aposta.valor * aposta.odd \r\n" + //
                    "                ELSE -aposta.valor \r\n" + //
                    "            END\r\n" + //
                    "        ) AS lucro\r\n" + //
                    "    FROM aposta\r\n" + //
                    "    LEFT JOIN competidor ON competidor.competicao_id = aposta.competicao_id \r\n" + //
                    "                         AND competidor.atleta_id = aposta.atleta_id\r\n" + //
                    "    JOIN competicao ON competicao.id = aposta.competicao_id\r\n" + //
                    "    WHERE \r\n" + //
                    "        aposta.jogador_id = ?\r\n" + //
                    "        AND NOW() - competicao.data_ocorrencia_evento < INTERVAL '1 Month'\r\n" + //
                    "        AND NOW() > competicao.data_ocorrencia_evento\r\n" + //
                    "    GROUP BY aposta.jogador_id\r\n" + //
                    ") AS aposta_stats ON jogador.id = aposta_stats.jogador_id;");
            sql.setInt(1, valor.getJogadorDTO().getId());
            ResultSet result = sql.executeQuery();
            while (result.next()) {
                if (result.getInt("status_aposta") == -1)
                    throw new Exception(
                            "Este usuário superou o limite de 30% de lucros em um mês ou já fez mais de 10 apostas");
            }
            // #region transaction
            sql = _conn.prepareStatement(
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
            // #endregion
            _conn.commit();
            return valor;
        } catch (Exception e) {
            _conn.rollback();
            throw e;
        }

    }

}
