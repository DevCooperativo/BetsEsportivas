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
    public double Valor_maximo_aposta;
    public double Valor_minimo_aposta;
    public String Status;
    private Character Estado;
    public List<CompetidorDTO> Competidores;

    public CompeticaoDTO() {
    }

    public CompeticaoDTO(int id, String nome, LocalDateTime data_abertura_apostas,
            LocalDateTime data_fechamento_apostas, LocalDateTime data_ocorrencia_evento, double valor_maximo_aposta,
            double valor_minimo_aposta) {
        Id = id;
        Nome = nome;
        Data_abertura_apostas = data_abertura_apostas;
        Data_fechamento_apostas = data_fechamento_apostas;
        Data_ocorrencia_evento = data_ocorrencia_evento;
        Valor_maximo_aposta = valor_maximo_aposta;
        Valor_minimo_aposta = valor_minimo_aposta;
    }

    public CompeticaoDTO(int id, String nome, CategoriaDTO categoria, LocalDateTime data_cadastro,
            LocalDateTime data_abertura_apostas,
            LocalDateTime data_fechamento_apostas, LocalDateTime data_ocorrencia_evento,
            double valorEmJogo, String status,
            List<CompetidorDTO> competidores, double valor_maximo_aposta, double valor_minimo_aposta) {
        Id = id;
        Nome = nome;
        Categoria = categoria;
        Data_cadastro = data_cadastro;
        Data_abertura_apostas = data_abertura_apostas;
        Data_fechamento_apostas = data_fechamento_apostas;
        Data_ocorrencia_evento = data_ocorrencia_evento;
        ValorEmJogo = valorEmJogo;
        Status = status;
        Competidores = competidores;
        Valor_maximo_aposta = valor_maximo_aposta;
        Valor_minimo_aposta = valor_minimo_aposta;
    }

    public CompeticaoDTO(String nome, CategoriaDTO categoria, LocalDateTime data_cadastro,
            LocalDateTime data_abertura_apostas,
            LocalDateTime data_fechamento_apostas, LocalDateTime data_ocorrencia_evento, double valor_maximo_aposta,
            double valor_minimo_aposta,
            List<CompetidorDTO> competidores) {
        Nome = nome;
        Categoria = categoria;
        Data_cadastro = data_cadastro;
        Data_abertura_apostas = data_abertura_apostas;
        Data_fechamento_apostas = data_fechamento_apostas;
        Data_ocorrencia_evento = data_ocorrencia_evento;
        Valor_maximo_aposta = valor_maximo_aposta;
        Valor_minimo_aposta = valor_minimo_aposta;
        Competidores = competidores;
    }

    public CompeticaoDTO(int id, String nome, CategoriaDTO categoria, LocalDateTime data_cadastro,
            LocalDateTime data_abertura_apostas,
            LocalDateTime data_fechamento_apostas, LocalDateTime data_ocorrencia_evento, double valor_maximo_aposta,
            double valor_minimo_aposta,
            List<CompetidorDTO> competidores) {
        Id = id;
        Nome = nome;
        Categoria = categoria;
        Data_cadastro = data_cadastro;
        Data_abertura_apostas = data_abertura_apostas;
        Data_fechamento_apostas = data_fechamento_apostas;
        Data_ocorrencia_evento = data_ocorrencia_evento;
        Valor_maximo_aposta = valor_maximo_aposta;
        Valor_minimo_aposta = valor_minimo_aposta;
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

    public List<CompetidorDTO> getCompetidores() {
        return Competidores;
    }

    public void setCompetidores(List<CompetidorDTO> Competidores) {
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

    public double getValor_minimo_aposta() {
        return Valor_minimo_aposta;
    }

    public void setValor_minimo_aposta(double Valor_minimo_aposta) {
        this.Valor_minimo_aposta = Valor_minimo_aposta;
    }

    public double getValor_maximo_aposta() {
        return Valor_maximo_aposta;
    }

    public void setValor_maximo_aposta(double Valor_maximo_aposta) {
        this.Valor_maximo_aposta = Valor_maximo_aposta;
    }

    @Override
    public String toString() {
        return Nome;
    }

    @Override
    public String getNomeFormatado() {
        return Nome;
    }

    public Character getEstado() {
        return Estado;
    }

    public void setEstado(Character Estado) {
        this.Estado = Estado;
    }

}
