package com.betsesportivas.Domain;

import com.betsesportivas.Domain.Abstractions.BaseEntity;

public class Categoria extends BaseEntity {
    private String _nome;
    private String _descricao;
    private String _cor;
    private String _is_ativada;
    private int _limite_participantes;

    public Categoria(int id, String _nome, String _descricao, String _cor, String _is_ativada,
            int _limite_participantes) {
        super(id);
        this._nome = _nome;
        this._descricao = _descricao;
        this._cor = _cor;
        this._is_ativada = _is_ativada;
        this._limite_participantes = _limite_participantes;
    }

    public Categoria(int id, String nome) {
        super(id);
        _nome = nome;
    }

    public String getNome() {
        return _nome;
    }

    public void setNome(String nome) {
        _nome = nome;
    }

    public String getDescricao() {
        return _descricao;
    }

    public void setDescricao(String _descricao) {
        this._descricao = _descricao;
    }

    public String getCor() {
        return _cor;
    }

    public void setCor(String _cor) {
        this._cor = _cor;
    }

    public String getIs_ativada() {
        return _is_ativada;
    }

    public void setIs_ativada(String _is_ativada) {
        this._is_ativada = _is_ativada;
    }

    public int getLimite_participantes() {
        return _limite_participantes;
    }

    public void setLimite_participantes(int _limite_participantes) {
        this._limite_participantes = _limite_participantes;
    }
}
