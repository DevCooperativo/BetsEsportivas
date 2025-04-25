package com.betsesportivas.Domain;

import java.time.LocalDate;

import com.betsesportivas.Domain.Abstractions.BaseEntity;

public class Competicao extends BaseEntity {
    private String _nome;
    private LocalDate _data_cadastro;
    private LocalDate _data_abertura_apostas;
    private LocalDate _data_fechamento_apostas;
    private int _categoria_id;
    private double _valor_limite_vencedor;

    public Competicao(int id, int _categoria_id, LocalDate _data_abertura_apostas, LocalDate _data_fechamento_apostas, String _nome, double _valor_limite_vencedor) {
        super(id);
        this._categoria_id = _categoria_id;
        this._data_abertura_apostas = _data_abertura_apostas;
        this._data_cadastro = LocalDate.now();
        this._data_fechamento_apostas = _data_fechamento_apostas;
        this._nome = _nome;
        this._valor_limite_vencedor = _valor_limite_vencedor;
    }

    public Competicao(int id, int _categoria_id, LocalDate _data_abertura_apostas, LocalDate _data_cadastro, LocalDate _data_fechamento_apostas, String _nome, double _valor_limite_vencedor) {
        super(id);
        this._categoria_id = _categoria_id;
        this._data_abertura_apostas = _data_abertura_apostas;
        this._data_cadastro = _data_cadastro;
        this._data_fechamento_apostas = _data_fechamento_apostas;
        this._nome = _nome;
        this._valor_limite_vencedor = _valor_limite_vencedor;
    }

    public String getNome() {
        return _nome;
    }

    public void setNome(String nome) {
        _nome = nome;
    }

    public LocalDate GetDataCadastro() {
        return _data_cadastro;
    }

    public void SetDataCadastro(LocalDate data_cadastro) {
        _data_cadastro = data_cadastro;
    }

    public LocalDate GetDataAberturaApostas() {
        return _data_abertura_apostas;
    }

    public void SetDataAberturaApostas(LocalDate data_abertura_apostas) {
        _data_abertura_apostas = data_abertura_apostas;
    }

    public LocalDate GetDataFechamentoApostas() {
        return _data_fechamento_apostas;
    }

    public void SetDataFechamentoApostas(LocalDate data_fechamento_apostas) {
        _data_fechamento_apostas = data_fechamento_apostas;
    }

    public int GetCategoriaId() {
        return _categoria_id;
    }

    public void SetCategoriaId(int categoria_id) {
        _categoria_id = categoria_id;
    }

    public double getValor_limite_vencedor() {
        return _valor_limite_vencedor;
    }

    public void setValor_limite_vencedor(double _valor_limite_vencedor) {
        this._valor_limite_vencedor = _valor_limite_vencedor;
    }
}
