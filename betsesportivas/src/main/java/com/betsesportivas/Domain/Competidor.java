package com.betsesportivas.Domain;

public class Competidor {
    private final Integer _atletaId;
    private final Integer _competicaoId;

    public Competidor(Integer atletaId, Integer competicaoId) {
        _atletaId = atletaId;
        _competicaoId = competicaoId;
    }

    public Integer GetAtletaId() {
        return _atletaId;
    }

    public Integer GetCompeticaoId() {
        return _competicaoId;
    }

}
