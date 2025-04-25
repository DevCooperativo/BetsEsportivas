package com.betsesportivas.DAO;

import java.sql.SQLException;
import java.util.List;

public interface IAtletaDAO<T,S> extends IBaseDAO<T,S> {
    List<S> BuscarAtletasDisponiveisDTO(int idCompeticao) throws SQLException ;
}
