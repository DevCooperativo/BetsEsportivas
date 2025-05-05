package com.betsesportivas.DTO;

import java.time.LocalDate;

public class AtletaDTO implements IBaseDTO {
    private int Id;
    private String Nome;
    private String Sobrenome;
    private char Sexo;
    private LocalDate Nascimento;
    private int Vitorias;
    private int Participacoes;

    public AtletaDTO(int Id, String Nome, String Sobrenome, char Sexo, LocalDate Nascimento, int Participacoes, int Vitorias) {
        this.Id = Id;
        this.Nome = Nome;
        this.Sobrenome = Sobrenome;
        this.Participacoes = Participacoes;
        this.Vitorias = Vitorias;
    }

    public AtletaDTO(int Id, String Nome, String Sobrenome, char Sexo, LocalDate Nascimento) {
        this.Id = Id;
        this.Nome = Nome;
        this.Sobrenome = Sobrenome;
        this.Sexo = Sexo;
        this.Nascimento = Nascimento;
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

    public void setNome(String Nome) {
        this.Nome = Nome;
    }

    public String getSobrenome() {
        return Sobrenome;
    }

    public void setSobrenome(String sobrenome) {
        Sobrenome = sobrenome;
    }

    public char getSexo() {
        return Sexo;
    }

    public void setSexo(char sexo) {
        Sexo = sexo;
    }

    public LocalDate getNascimento() {
        return Nascimento;
    }

    public void setNascimento(LocalDate nascimento) {
        Nascimento = nascimento;
    }

    public int getVitorias() {
        return Vitorias;
    }

    public void setVitorias(int Vitorias) {
        this.Vitorias = Vitorias;
    }

    public int getParticipacoes() {
        return Participacoes;
    }

    public void setParticipacoes(int Participacoes) {
        this.Participacoes = Participacoes;
    }

    @Override
    public String toString() {
        return this.Nome + " " + this.Sobrenome;
    }
}
