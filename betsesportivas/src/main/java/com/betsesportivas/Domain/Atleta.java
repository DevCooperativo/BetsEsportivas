package com.betsesportivas.Domain;

import com.betsesportivas.Domain.Abstractions.BaseEntity;

public class Atleta extends BaseEntity {
    private String _nome;
    private Integer _vitorias = 0;
    private Integer _participacoes = 0;

    public Atleta(Integer id, String nome) {
        super(id);
        _nome=nome;
    }

    public String GetNome() {
        return _nome;
    }

    public void SetNome(String nome) {
        _nome = nome;
    }

    public Integer GetVitorias() {
        return _vitorias;
    }

    public void SetVitorias(Integer vitorias) {
        _vitorias = vitorias;
    }

    public Integer GetParticipacoes() {
        return _participacoes;
    }

    public void SetParticipacoes(Integer participacoes) {
        _participacoes = participacoes;
    }


}
