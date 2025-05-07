package com.betsesportivas.DAO;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public interface IBaseDAO<T, S> {
    void Connect(Connection conn);

    List<T> BuscarTodos() throws SQLException;

    T BuscarPorId(Integer id) throws SQLException;

    T Criar(T valor) throws SQLException;

    T Editar(T valor) throws SQLException;

    void Excluir(int id) throws SQLException;

    S CriarPorDTO(S valor) throws Exception;

    S BuscarDTOPorId(int id) throws SQLException;

    List<S> BuscarTodosOsDTO() throws SQLException;

    S EditarPorDTO(S valor) throws SQLException;
}
