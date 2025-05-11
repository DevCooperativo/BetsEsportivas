package com.betsesportivas.DAO;

import java.sql.SQLException;
import java.util.Map;

public interface ICategoriaDAO<T, S> extends IBaseDAO<T, S> {
    Map<String, Integer> RecuperarQuantidadeCompeticoesPorCategoria() throws SQLException;

}
