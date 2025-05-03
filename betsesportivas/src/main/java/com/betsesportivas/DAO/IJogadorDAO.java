package com.betsesportivas.DAO;

import java.sql.SQLException;

public interface IJogadorDAO<T, S> extends IBaseDAO<T, S> {
    double AdicionarSaldo(int jogadorId, double valor) throws SQLException;
}
