package com.betsesportivas.DTO;

import java.time.LocalDate;

import com.betsesportivas.Domain.Jogador;

public class JogadorDTO implements IBaseDTO {
    private int id;
    private String nome;
    private LocalDate nascimento;
    private Double saldo;
    private String email;
    private boolean isAtivado;

    public JogadorDTO(int id, String nome, Double saldo) {
        this.id = id;
        this.nome = nome;
        this.saldo = saldo;
    }

    public JogadorDTO(Jogador jogador) {
        id = jogador.getId();
        nome = jogador.getNome();
        nascimento = jogador.getNascimento();
        saldo = jogador.GetSaldo();
        email = jogador.GetEmail();
        isAtivado = jogador.isAtivado();
    }

    public JogadorDTO(int id, String nome, LocalDate nascimento, Double saldo, String email, boolean isAtivado) {
        this.id = id;
        this.nome = nome;
        this.nascimento = nascimento;
        this.saldo = saldo;
        this.email = email;
        this.isAtivado = isAtivado;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public LocalDate getNascimento() {
        return nascimento;
    }

    public void setNascimento(LocalDate nascimento) {
        this.nascimento = nascimento;
    }

    public Double getSaldo() {
        return saldo;
    }

    public void setSaldo(double saldo) {
        this.saldo = saldo;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public boolean isAtivado() {
        return isAtivado;
    }

    public void setAtivado(boolean isAtivado) {
        this.isAtivado = isAtivado;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    @Override
    public String toString(){
        return nome;
    }

    @Override
    public String getNomeFormatado() {
        return nome;
    }

}
