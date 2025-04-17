package com.betsesportivas.Domain;

import java.time.LocalDate;

import com.betsesportivas.Domain.Abstractions.BaseEntity;

public class Competicao extends BaseEntity {
    private String _nome;
    private LocalDate _dataCadastro;
    private LocalDate _dataAberturaApostas;
    private LocalDate _dataFechamentoApostas;
    private Categoria _categoria;

    public Competicao(Integer id, String nome, LocalDate dataCadastro, LocalDate dataAberturaApostas,
            LocalDate dataFechamentoApostas, Categoria categoria) {
        super(id);
        _nome = nome;
        _dataCadastro = dataCadastro;
        _dataAberturaApostas = dataAberturaApostas;
        _dataFechamentoApostas = dataFechamentoApostas;
        _categoria = categoria;
    }

    public String GetNome() {
        return _nome;
    }

    public void SetNome(String nome) {
        _nome = nome;
    }

    public LocalDate GetDataCadastro() {
        return _dataCadastro;
    }

    public void SetDataCadastro(LocalDate dataCadastro) {
        _dataCadastro = dataCadastro;
    }

    public LocalDate GetDataAberturaApostas() {
        return _dataAberturaApostas;
    }

    public void SetDataAberturaApostas(LocalDate dataAberturaApostas) {
        _dataAberturaApostas = dataAberturaApostas;
    }

    public LocalDate GetDataFechamentoApostas() {
        return _dataFechamentoApostas;
    }

    public void SetDataFechamentoApostas(LocalDate dataFechamentoApostas) {
        _dataFechamentoApostas = dataFechamentoApostas;
    }

    public Categoria GetCategoria() {
        return _categoria;
    }

    public void SetCategoria(Categoria categoria) {
        _categoria = categoria;
    }
}
