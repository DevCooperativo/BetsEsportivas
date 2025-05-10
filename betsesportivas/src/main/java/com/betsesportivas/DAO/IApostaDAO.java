package com.betsesportivas.DAO;

import java.sql.SQLException;
import java.util.Map;

public interface IApostaDAO<T, S> extends IBaseDAO<T, S> {
    Map<String, Integer> RecuperarQuantidadeApostasPorCompeticao() throws SQLException;
}
