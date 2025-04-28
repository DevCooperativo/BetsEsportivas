package com.betsesportivas.DTO;

public class AtletaDTO implements IBaseDTO {
    public int Id;
    public String Nome;
    public String Sobrenome;
    public int Vitorias;
    public int Participacoes;

    public AtletaDTO(int Id, String Nome, String Sobrenome, int Participacoes, int Vitorias) {
        this.Id = Id;
        this.Nome = Nome;
        this.Sobrenome = Sobrenome;
        this.Participacoes = Participacoes;
        this.Vitorias = Vitorias;
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
