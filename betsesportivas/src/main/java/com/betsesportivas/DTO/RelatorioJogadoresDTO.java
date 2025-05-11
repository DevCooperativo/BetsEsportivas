package com.betsesportivas.DTO;

public class RelatorioJogadoresDTO {
    private int clienteId;
    private String nomeJogador;
    private double totalApostado;
    private double totalGanho;
    private double lucro;

    public RelatorioJogadoresDTO(int clienteId, String nomeJogador, double totalApostado, double totalGanho,
            double lucro) {
        this.clienteId = clienteId;
        this.nomeJogador = nomeJogador;
        this.totalApostado = totalApostado;
        this.totalGanho = totalGanho;
        this.lucro = lucro;
    }

    public int getJogadorId() {
        return clienteId;
    }

    public void setJogadorId(int clienteId) {
        this.clienteId = clienteId;
    }

    public String getNomeJogador() {
        return nomeJogador;
    }

    public void setNomeJogador(String nomeJogador) {
        this.nomeJogador = nomeJogador;
    }

    public double getTotalApostado() {
        return totalApostado;
    }

    public void setTotalApostado(double totalApostado) {
        this.totalApostado = totalApostado;
    }

    public double getTotalGanho() {
        return totalGanho;
    }

    public void setTotalGanho(double totalGanho) {
        this.totalGanho = totalGanho;
    }

    public double getLucro() {
        return lucro;
    }

    public void setLucro(double lucro) {
        this.lucro = lucro;
    }
    // Getters e setters omitidos para brevidade
}
