package com.betsesportivas.DAO;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import com.betsesportivas.DTO.RelatorioApostasDTO;

public interface IApostaDAO<T, S> extends IBaseDAO<T, S> {
    Map<String, Integer> RecuperarQuantidadeApostasPorCompeticao() throws SQLException;

    List<RelatorioApostasDTO> RecuperarRelatorioApostas() throws SQLException;
}
