package com.betsesportivas.DAO;

import java.sql.SQLException;
import java.util.List;

public interface ICompeticaoDAO<T,S> extends IBaseDAO<T,S> {
    List<S> BuscarDTOsEmAberto() throws SQLException;
    List<S> BuscarDTOsComCompetidores() throws SQLException;
    void FinalizarCompeticao(S competicao) throws SQLException;
}
