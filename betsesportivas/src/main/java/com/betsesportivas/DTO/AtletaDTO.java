package com.betsesportivas.DTO;

public class AtletaDTO {
    public int Id;
    public String Nome;
    public int Vitorias;
    public int Participacoes;

    public AtletaDTO(int Id, String Nome, int Participacoes, int Vitorias) {
        this.Id = Id;
        this.Nome = Nome;
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
}
