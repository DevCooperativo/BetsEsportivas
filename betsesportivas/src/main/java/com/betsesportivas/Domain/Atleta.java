package com.betsesportivas.Domain;

import com.betsesportivas.Domain.Abstractions.BaseEntity;

public class Atleta extends BaseEntity {
    private String _nome;
    private int _vitorias = 0;
    private int _participacoes = 0;

    public Atleta(int id, String nome) {
        super(id);
        _nome = nome;
    }

    public Atleta(int id, String nome, int vitorias, int participacoes) {
        super(id);
        _nome = nome;
        _vitorias = vitorias;
        _participacoes = participacoes;
    }

    public String GetNome() {
        return _nome;
    }

    public void SetNome(String nome) {
        _nome = nome;
    }

    public int GetVitorias() {
        return _vitorias;
    }

    public void SetVitorias(int vitorias) {
        _vitorias = vitorias;
    }

    public int GetParticipacoes() {
        return _participacoes;
    }

    public void SetParticipacoes(int participacoes) {
        _participacoes = participacoes;
    }

}
