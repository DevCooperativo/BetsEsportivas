package com.betsesportivas.DTO;

import java.time.LocalDateTime;

public class AtletaDTO implements IBaseDTO {
    private int Id;
    private String Nome;
    private String Sobrenome;
    private Character Sexo;
    private LocalDateTime Nascimento;
    private int Vitorias;
    private int Participacoes;

    public AtletaDTO(String nome, String sobrenome, Character sexo, LocalDateTime nascimento, int vitorias,
            int participacoes) {
        Nome = nome;
        Sobrenome = sobrenome;
        Sexo = sexo;
        Nascimento = nascimento;
        Vitorias = vitorias;
        Participacoes = participacoes;
    }

    
    public AtletaDTO(int id, String nome, String sobrenome, Character sexo, LocalDateTime nascimento, int vitorias,
            int participacoes) {
        Id = id;
        Nome = nome;
        Sobrenome = sobrenome;
        Sexo = sexo;
        Nascimento = nascimento;
        Vitorias = vitorias;
        Participacoes = participacoes;
    }


    public AtletaDTO(int Id, String Nome, String Sobrenome, Character Sexo, LocalDateTime Nascimento) {
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

    public LocalDateTime getNascimento() {
        return Nascimento;
    }

    public void setNascimento(LocalDateTime nascimento) {
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
