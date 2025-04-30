package com.betsesportivas.Domain;

public class Competidor {
    private final int _atleta_id;
    private final Atleta _atleta;
    private final int _competicao_id;
    private int _numero;
    private int _posicao_inicial;
    private int _posicao_final;

    public Competidor(Atleta _atleta, int _competicao_id, int _numero, int _posicao_final, int _posicao_inicial) {
        this._atleta = _atleta;
        this._atleta_id = _atleta.GetId();
        this._competicao_id = _competicao_id;
        this._numero = _numero;
        this._posicao_final = _posicao_final;
        this._posicao_inicial = _posicao_inicial;
    }

    
    public int GetAtletaId() {
        return _atleta_id;
    }

    public int GetCompeticaoId() {
        return _competicao_id;
    }

    public int getNumero() {
        return _numero;
    }

    public void setNumero(int _numero) {
        this._numero = _numero;
    }

    public int getPosicao_inicial() {
        return _posicao_inicial;
    }

    public void setPosicao_inicial(int _posicao_inicial) {
        this._posicao_inicial = _posicao_inicial;
    }

    public int getPosicao_final() {
        return _posicao_final;
    }

    public void setPosicao_final(int _posicao_final) {
        this._posicao_final = _posicao_final;
    }

    public Atleta getAtleta() {
        return _atleta;
    }

}
