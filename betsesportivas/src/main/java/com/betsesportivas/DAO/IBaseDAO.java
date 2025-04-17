package com.betsesportivas.DAO;

import java.util.List;

public interface IBaseDAO<T>  {
    List<T> BuscarTodos();
    T BuscarPorId(Integer id);
    T Criar(T valor);
    T Editar(T valor);
    void Escluir(Integer id);
}
