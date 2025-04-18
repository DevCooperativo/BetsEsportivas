package com.betsesportivas.DAO;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public interface IBaseDAO<T>  {
    void Connect(Connection conn);
    List<T> BuscarTodos() throws SQLException ;
    T BuscarPorId(Integer id)  throws SQLException;
    T Criar(T valor)  throws SQLException;
    T Editar(T valor)  throws SQLException;
    void Excluir(Integer id)  throws SQLException;
}
