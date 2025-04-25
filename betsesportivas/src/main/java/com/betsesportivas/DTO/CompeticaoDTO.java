package com.betsesportivas.DTO;

import java.time.LocalDate;
import java.util.List;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class CompeticaoDTO {
    public int Id;
    public String Nome;
    public CategoriaDTO Categoria;
    public LocalDate Data_cadastro;
    public LocalDate Data_abertura_apostas;
    public LocalDate Data_fechamento_apostas;
    public int QuantidadeDeApostas;
    public double ValorEmJogo;
    public String Status;
    public List<AtletaDTO> Competidores;

    public CompeticaoDTO() {
    }

    public CompeticaoDTO(int id, String nome, CategoriaDTO categoria, LocalDate data_cadastro,
            LocalDate data_abertura_apostas,
            LocalDate data_fechamento_apostas, int quantidadeDeApostas, double valorEmJogo, String status,
            List<AtletaDTO> competidores) {
        Id = id;
        Nome = nome;
        Categoria = categoria;
        Data_cadastro = data_cadastro;
        Data_abertura_apostas = data_abertura_apostas;
        Data_fechamento_apostas = data_fechamento_apostas;
        QuantidadeDeApostas = quantidadeDeApostas;
        ValorEmJogo = valorEmJogo;
        Status = status;
        Competidores = competidores;
    }

    public int getId() {
        return Id;
    }

    public void setId(int Id) {
        this.Id = Id;
    }

    public String getNome() {
        return Nome;
    }

    public void setNome(String nome) {
        Nome = nome;
    }

    public CategoriaDTO getCategoria() {
        return Categoria;
    }

    public void setCategoria(CategoriaDTO categoria) {
        Categoria = categoria;
    }

    public LocalDate getData_cadastro() {
        return Data_cadastro;
    }

    public void setData_cadastro(LocalDate data_cadastro) {
        Data_cadastro = data_cadastro;
    }

    public LocalDate getData_abertura_apostas() {
        return Data_abertura_apostas;
    }

    public void setData_abertura_apostas(LocalDate data_abertura_apostas) {
        Data_abertura_apostas = data_abertura_apostas;
    }

    public LocalDate getData_fechamento_apostas() {
        return Data_fechamento_apostas;
    }

    public void setData_fechamento_apostas(LocalDate data_fechamento_apostas) {
        Data_fechamento_apostas = data_fechamento_apostas;
    }

    public double getValorEmJogo() {
        return ValorEmJogo;
    }

    public void setValorEmJogo(double valorEmJogo) {
        ValorEmJogo = valorEmJogo;
    }

    public String getStatus() {
        return Status;
    }

    public void setStatus(String status) {
        Status = status;
    }

    public int getQuantidadeDeApostas() {
        return QuantidadeDeApostas;
    }

    public void setQuantidadeDeApostas(int QuantidadeDeApostas) {
        this.QuantidadeDeApostas = QuantidadeDeApostas;
    }

    public List<AtletaDTO> getCompetidores() {
        return Competidores;
    }

    public void setCompetidores(List<AtletaDTO> Competidores) {
        this.Competidores = Competidores;
    }

    public StringProperty nameProperty() {
        return new SimpleStringProperty(Nome);
    }

}
