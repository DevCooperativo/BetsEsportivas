package com.betsesportivas.Domain;

import java.time.LocalDate;

import com.betsesportivas.Domain.Abstractions.BaseEntity;

public class Competicao extends BaseEntity {
    private String _nome;
    private LocalDate _data_cadastro;
    private LocalDate _data_abertura_apostas;
    private LocalDate _data_fechamento_apostas;
    private Categoria _categoria;

    public Competicao(Integer id, String nome, LocalDate data_cadastro, LocalDate data_abertura_apostas,
            LocalDate data_fechamento_apostas, Categoria categoria) {
        super(id);
        _nome = nome;
        _data_cadastro = data_cadastro;
        _data_abertura_apostas = data_abertura_apostas;
        _data_fechamento_apostas = data_fechamento_apostas;
        _categoria = categoria;
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

    public Categoria GetCategoria() {
        return _categoria;
    }

    public void SetCategoria(Categoria categoria) {
        _categoria = categoria;
    }
}
