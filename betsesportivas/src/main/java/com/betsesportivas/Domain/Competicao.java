package com.betsesportivas.Domain;

import java.time.LocalDate;

import com.betsesportivas.Domain.Abstractions.BaseEntity;

public class Competicao extends BaseEntity {
    private String _nome;
    private LocalDate _data_cadastro;
    private LocalDate _data_abertura_apostas;
    private LocalDate _data_fechamento_apostas;
    private int _categoria_id;

    public Competicao(Integer id, String nome, LocalDate data_cadastro, LocalDate data_abertura_apostas,
            LocalDate data_fechamento_apostas, int categoria_id) {
        super(id);
        _nome = nome;
        _data_cadastro = data_cadastro;
        _data_abertura_apostas = data_abertura_apostas;
        _data_fechamento_apostas = data_fechamento_apostas;
        _categoria_id = categoria_id;
    }

    public String GetNome() {
        return _nome;
    }

    public void SetNome(String nome) {
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
}
