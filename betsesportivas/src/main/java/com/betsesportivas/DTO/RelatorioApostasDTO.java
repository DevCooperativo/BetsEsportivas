package com.betsesportivas.DTO;

public class RelatorioApostasDTO {
    private String nomeCompeticao;
    private int quantidadeVencedores;

    public RelatorioApostasDTO(String nomeCompeticao, int quantidadeVencedores) {
        this.nomeCompeticao = nomeCompeticao;
        this.quantidadeVencedores = quantidadeVencedores;
    }

    public String getNomeCompeticao() {
        return nomeCompeticao;
    }

    public int getQuantidadeVencedores() {
        return quantidadeVencedores;
    }
}
