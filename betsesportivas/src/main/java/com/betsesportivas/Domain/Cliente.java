package com.betsesportivas.Domain;

import com.betsesportivas.Domain.Abstractions.BaseEntity;

public class Cliente extends BaseEntity{
    private String _nome;
    private double _saldo;
    private String _email;

    public Cliente(Integer id, String nome, double saldo, String email) {
        super(id);
        _nome=nome;
        _saldo=saldo;
        _email=email;
    }

    public String GetNome(){
        return _nome;
    }

    public void SetNome(String nome){
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
}
