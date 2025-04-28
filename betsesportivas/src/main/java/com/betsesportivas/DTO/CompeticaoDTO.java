package com.betsesportivas.DTO;

import java.time.LocalDateTime;
import java.util.List;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class CompeticaoDTO implements IBaseDTO {
    public int Id;
    public String Nome;
    public CategoriaDTO Categoria;
    public LocalDateTime Data_cadastro;
    public LocalDateTime Data_abertura_apostas;
    public LocalDateTime Data_fechamento_apostas;
    public LocalDateTime Data_ocorrencia_evento;
    public int QuantidadeDeApostas;
    public double ValorEmJogo;
    public String Status;
    public List<AtletaDTO> Competidores;

    public CompeticaoDTO() {
    }

    public CompeticaoDTO(int id, String nome, CategoriaDTO categoria, LocalDateTime data_cadastro,
            LocalDateTime data_abertura_apostas,
            LocalDateTime data_fechamento_apostas, LocalDateTime data_ocorrencia_evento, int quantidadeDeApostas,
            double valorEmJogo, String status,
            List<AtletaDTO> competidores) {
        Id = id;
        Nome = nome;
        Categoria = categoria;
        Data_cadastro = data_cadastro;
        Data_abertura_apostas = data_abertura_apostas;
        Data_fechamento_apostas = data_fechamento_apostas;
        Data_ocorrencia_evento = data_ocorrencia_evento;
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

    public LocalDateTime getData_cadastro() {
        return Data_cadastro;
    }

    public void setData_cadastro(LocalDateTime data_cadastro) {
        Data_cadastro = data_cadastro;
    }

    public LocalDateTime getData_abertura_apostas() {
        return Data_abertura_apostas;
    }

    public void setData_abertura_apostas(LocalDateTime data_abertura_apostas) {
        Data_abertura_apostas = data_abertura_apostas;
    }

    public LocalDateTime getData_fechamento_apostas() {
        return Data_fechamento_apostas;
    }

    public void setData_fechamento_apostas(LocalDateTime data_fechamento_apostas) {
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

    public LocalDateTime getData_ocorrencia_evento() {
        return Data_ocorrencia_evento;
    }

    public void setData_ocorrencia_evento(LocalDateTime Data_ocorrencia_evento) {
        this.Data_ocorrencia_evento = Data_ocorrencia_evento;
    }



}
