package com.betsesportivas.DTO;

public class CompetidorDTO {
    public int Atleta_id;
    public AtletaDTO AtletaDTO;
    public int Competicao_id;
    public int Numero;
    public int Posicao_inicial;
    public int Posicao_final;

    public CompetidorDTO(AtletaDTO AtletaDTO, int Competicao_id, int Numero, int Posicao_final, int Posicao_inicial) {
        this.AtletaDTO = AtletaDTO;
        this.Atleta_id = AtletaDTO.Id;
        this.Competicao_id = Competicao_id;
        this.Numero = Numero;
        this.Posicao_final = Posicao_final;
        this.Posicao_inicial = Posicao_inicial;
    }

    public CompetidorDTO(AtletaDTO AtletaDTO){
        this.AtletaDTO=AtletaDTO;
        this.Atleta_id = AtletaDTO.getId();
    }

    public int getAtleta_id() {
        return Atleta_id;
    }

    public void setAtleta_id(int Atleta_id) {
        this.Atleta_id = Atleta_id;
    }

    public AtletaDTO getAtletaDTO() {
        return AtletaDTO;
    }

    public void setAtletaDTO(AtletaDTO AtletaDTO) {
        this.AtletaDTO = AtletaDTO;
    }

    public int getCompeticao_id() {
        return Competicao_id;
    }

    public void setCompeticao_id(int Competicao_id) {
        this.Competicao_id = Competicao_id;
    }

    public int getNumero() {
        return Numero;
    }

    public void setNumero(int Numero) {
        this.Numero = Numero;
    }

    public int getPosicao_inicial() {
        return Posicao_inicial;
    }

    public void setPosicao_inicial(int Posicao_inicial) {
        this.Posicao_inicial = Posicao_inicial;
    }

    public int getPosicao_final() {
        return Posicao_final;
    }

    public void setPosicao_final(int Posicao_final) {
        this.Posicao_final = Posicao_final;
    }

    @Override
    public String toString(){
        return this.AtletaDTO.getNome() + " " + this.AtletaDTO.getSobrenome();
    }
}
