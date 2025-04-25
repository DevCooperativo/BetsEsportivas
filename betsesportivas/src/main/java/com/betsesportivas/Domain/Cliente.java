package com.betsesportivas.Domain;

import java.time.LocalDate;

import com.betsesportivas.Domain.Abstractions.BaseEntity;

public class Cliente extends BaseEntity{
    private String _nome;
    private LocalDate _nascimento;
    private double _saldo;
    private String _email;
    private String _is_ativado;

    public Cliente(int id, String _nome, LocalDate _nascimento, double _saldo, String _email, String _is_ativado) {
        super(id);
        this._nome = _nome;
        this._nascimento = _nascimento;
        this._saldo = _saldo;
        this._email = _email;
        this._is_ativado = _is_ativado;
    }

    public String getNome(){
        return _nome;
    }

    public void setNome(String nome){
        _nome=nome;
    }

    public double GetSaldo(){
        return _saldo;
    }

    public void SetSaldo(double saldo){
        _saldo=saldo;
    }

    public String GetEmail(){
        return _email;
    }

    public void SetEmail(String email){
        _email=email;
    }

    public LocalDate getNascimento() {
        return _nascimento;
    }

    public void setNascimento(LocalDate _nascimento) {
        this._nascimento = _nascimento;
    }

    public String getIs_ativado() {
        return _is_ativado;
    }

    public void setIs_ativado(String _is_ativado) {
        this._is_ativado = _is_ativado;
    }
}
