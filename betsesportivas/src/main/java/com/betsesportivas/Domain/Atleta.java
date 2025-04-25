package com.betsesportivas.Domain;

import java.time.LocalDate;

import com.betsesportivas.Domain.Abstractions.BaseEntity;

public class Atleta extends BaseEntity {
    private String _nome;
    private String _sobrenome;
    
    private LocalDate _nascimento;
    private String _sexo;
    private int _vitorias = 0;
    private int _participacoes = 0;

    public Atleta(int id, String _nome, String _sobrenome, LocalDate _nascimento, String _sexo, int _vitorias,
            int _participacoes) {
        super(id);
        this._nome = _nome;
        this._sobrenome = _sobrenome;
        this._nascimento = _nascimento;
        this._sexo = _sexo;
        this._vitorias = _vitorias;
        this._participacoes = _participacoes;
    }

    public Atleta(int id, String _nome, String _sobrenome, LocalDate _nascimento, String _sexo) {
        super(id);
        this._nome = _nome;
        this._sobrenome = _sobrenome;
        this._nascimento = _nascimento;
        this._sexo = _sexo;
    }


    public String getNome() {
        return _nome;
    }

    public void setNome(String nome) {
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

    public String getSexo() {
        return _sexo;
    }

    public void setSexo(String _sexo) {
        this._sexo = _sexo;
    }

    public LocalDate getNascimento() {
        return _nascimento;
    }

    public void setNascimento(LocalDate _nascimento) {
        this._nascimento = _nascimento;
    }

    public String getSobrenome() {
        return _sobrenome;
    }

    public void setSobrenome(String _sobrenome) {
        this._sobrenome = _sobrenome;
    }

}
