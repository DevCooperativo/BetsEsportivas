package com.betsesportivas.Domain;

import java.time.LocalDate;

import com.betsesportivas.Domain.Abstractions.BaseEntity;

public class Jogador extends BaseEntity {
    private String _nome;
    private LocalDate _nascimento;
    private double _saldo;
    private String _email;
    private boolean _isAtivado;

    public Jogador(int id, String _nome, LocalDate _nascimento, double _saldo, String _email, boolean _isAtivado) {
        super(id);
        this._nome = _nome;
        this._nascimento = _nascimento;
        this._saldo = _saldo;
        this._email = _email;
        this._isAtivado = _isAtivado;
    }

    public String getNome() {
        return _nome;
    }

    public void setNome(String nome) {
        _nome = nome;
    }

    public double GetSaldo() {
        return _saldo;
    }

    public void SetSaldo(double saldo) {
        _saldo = saldo;
    }

    public String GetEmail() {
        return _email;
    }

    public void SetEmail(String email) {
        _email = email;
    }

    public LocalDate getNascimento() {
        return _nascimento;
    }

    public void setNascimento(LocalDate _nascimento) {
        this._nascimento = _nascimento;
    }

    public boolean isAtivado() {
        return _isAtivado;
    }

    public void setAtivado(boolean isAtivado) {
        this._isAtivado = isAtivado;
    }
}
