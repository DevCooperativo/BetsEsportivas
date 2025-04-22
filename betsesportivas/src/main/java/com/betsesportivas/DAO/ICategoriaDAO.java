package com.betsesportivas.DAO;

import java.sql.SQLException;
import java.util.List;

public interface ICategoriaDAO<T, S> extends IBaseDAO<T, S> {
    S BuscarDTOPorId(int id) throws SQLException;
    List<S> BuscarTodosOsDTO() throws SQLException;
    S EditarPorDTO(S valor) throws SQLException;
}
