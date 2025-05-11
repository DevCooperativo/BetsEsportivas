package com.betsesportivas.DAO;

import java.sql.SQLException;
import java.util.List;

import com.betsesportivas.DTO.RelatorioJogadoresDTO;

public interface IJogadorDAO<T, S> extends IBaseDAO<T, S> {
    double AdicionarSaldo(int jogadorId, double valor) throws SQLException;

    List<RelatorioJogadoresDTO> buscarRelatorioLucroPorCliente() throws SQLException;
}
