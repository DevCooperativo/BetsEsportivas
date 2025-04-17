package com.betsesportivas.Domain;

public class Competidor {
    private final int _atleta_id;
    private final int _competicao_id;

    public Competidor(int atletaId, int competicaoId) {
        _atleta_id = atletaId;
        _competicao_id = competicaoId;
    }

    public int GetAtletaId() {
        return _atleta_id;
    }

    public int GetCompeticaoId() {
        return _competicao_id;
    }

}
