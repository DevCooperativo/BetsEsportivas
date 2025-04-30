package com.betsesportivas.DAO;

import java.sql.SQLException;
import java.util.List;

public interface ICompetidorDAO<T, S> extends IBaseDAO<T, S> {

    void Excluir(int atleta_id, int competicao_id) throws SQLException;

    @Override
    S CriarPorDTO(S valor) throws SQLException;

    @Override
    S BuscarDTOPorId(int id) throws SQLException;

    @Override
    List<S> BuscarTodosOsDTO() throws SQLException;

    @Override
    S EditarPorDTO(S valor) throws SQLException;

    List<S> BuscarCompetidoresDisponiveisDTO(int idCompeticao) throws SQLException;
}
