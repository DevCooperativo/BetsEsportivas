package com.betsesportivas.Abstractions;

import com.betsesportivas.DTO.IBaseDTO;

public class ComboboxItem implements IBaseDTO {
    private String Nome;
    private int Value;

    public ComboboxItem(String nome, int value) {
        Nome = nome;
        Value = value;
    }

    public String getNome() {
        return Nome;
    }

    public void setNome(String nome) {
        Nome = nome;
    }

    public int getValue() {
        return Value;
    }

    public void setValue(int value) {
        Value = value;
    }

    @Override
    public String toString() {
        return this.Nome;
    }

}
