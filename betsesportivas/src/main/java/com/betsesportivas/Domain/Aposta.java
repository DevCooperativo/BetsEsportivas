package com.betsesportivas.Domain;

import com.betsesportivas.Domain.Abstractions.BaseEntity;

public class Aposta extends BaseEntity {
    private Cliente _cliente;
    private double _valor;
    private Competidor _competidor;

    public Aposta(Integer id, Cliente cliente, double valor, Competidor competidor) {
        super(id);
        _cliente=cliente;
        _valor=valor;
        _competidor=competidor;
    }

    public Cliente GetCliente(){
        return _cliente;
    }

    public void SetCliente(Cliente cliente){
        _cliente=cliente;
    }

    public double GetValor(){
        return _valor;
    }

    public void SetValor(double valor){
        _valor=valor;
    }

    public Competidor GetCompetidor(){
        return _competidor;
    }

    public void SetCompetidor(Competidor competidor){
        _competidor=competidor;
    }


}
