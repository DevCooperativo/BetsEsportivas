package com.betsesportivas.Domain;

import com.betsesportivas.Domain.Abstractions.BaseEntity;

public class Categoria extends BaseEntity {
    private String _nome;

    public Categoria(int id, String nome) {
        super(id);
        _nome = nome;
    }

    public String GetNome() {
        return _nome;
    }

    public void SetNome(String nome) {
        _nome = nome;
    }
}
