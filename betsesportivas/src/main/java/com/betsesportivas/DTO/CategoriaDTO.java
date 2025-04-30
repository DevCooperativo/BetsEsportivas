package com.betsesportivas.DTO;

public class CategoriaDTO implements IBaseDTO{
    private int Id;
    private String Nome;
    private String Cor;
    private boolean IsAtiva;
    public boolean isAtiva() {
        return IsAtiva;
    }

    public void setIsAtiva(boolean isAtiva) {
        IsAtiva = isAtiva;
    }

    public String getCor() {
        return Cor;
    }

    public void setCor(String cor) {
        Cor = cor;
    }

    private String Descricao;
    private int MaxParticipantes;
    public String getDescricao() {
        return Descricao;
    }

    public void setDescricao(String descricao) {
        Descricao = descricao;
    }

    public int getMaxParticipantes() {
        return MaxParticipantes;
    }

    public void setMaxParticipantes(int maxParticipantes) {
        MaxParticipantes = maxParticipantes;
    }

    private int VezesUtilizada;

    public CategoriaDTO(){}

    public CategoriaDTO(int id, String nome, String descricao, String cor, int vezesUtilizada, int maxParticipantes, boolean isAtiva){
        Id=id;
        Nome=nome;
        Descricao = descricao;
        VezesUtilizada=vezesUtilizada;
        Cor = cor;
        MaxParticipantes = maxParticipantes;
        IsAtiva = isAtiva;
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

    public int getVezesUtilizada() {
        return VezesUtilizada;
    }

    public void setVezesUtilizada(int VezesUtilizada) {
        this.VezesUtilizada = VezesUtilizada;
    }

    @Override
    public String toString(){
        return this.Nome;
    }
}
