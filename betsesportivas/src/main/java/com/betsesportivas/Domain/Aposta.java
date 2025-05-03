package com.betsesportivas.Domain;

import com.betsesportivas.Domain.Abstractions.BaseEntity;

public class Aposta extends BaseEntity {
    private int _jogador_id;
    private double _valor;
    private int _atleta_id;
    private int _competicao_id;

    public Aposta(int id, int jogador_id, double valor, int atleta_id, int competicao_id) {
        super(id);
        _jogador_id = jogador_id;
        _valor = valor;
        _atleta_id = atleta_id;
        _competicao_id = competicao_id;
    }

    public int GetJogadorId() {
        return _jogador_id;
    }

    public void SetJogadorId(int jogador_id) {
        _jogador_id = jogador_id;
    }

    public double GetValor() {
        return _valor;
    }

    public void SetValor(double valor) {
        _valor = valor;
    }

    public int GetAtletaId() {
        return _atleta_id;
    }

    public void SetAtletaId(int atleta_id) {
        _atleta_id = atleta_id;
    }

    public int GetCompeticaoId() {
        return _competicao_id;
    }

    public void SetCompeticaoId(int competicao_id) {
        _competicao_id = competicao_id;
    }

}
