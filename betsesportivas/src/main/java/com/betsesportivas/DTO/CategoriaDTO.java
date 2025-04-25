package com.betsesportivas.DTO;

public class CategoriaDTO {
    private int Id;
    private String Nome;
    private int VezesUtilizada;

    public CategoriaDTO(){}

    public CategoriaDTO(int id, String nome, int vezesUtilizada){
        Id=id;
        Nome=nome;
        VezesUtilizada=vezesUtilizada;
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
}
