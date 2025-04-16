package com.betsesportivas.Domain.Utils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

public class Data {
    private Integer _dia;
    private Integer _mes;
    
    private Integer _ano;

    private String _mesString;

    private List<String> _meses = new LinkedList<>(Arrays.asList("Janeiro", "Fevereiro", "Março", "Abril", "Maio", "Junho", "Julho", "Agosto", "Setembro", "Outubro", "Novembro", "Dezembro"));

    public Integer GetDia() {
        return _dia;
    }

    public void SetDia(Integer dia) throws Exception {
        if (dia < 1 || dia > 31)
            throw new Exception("Data inválida");
        if (_mes != null) {
            if (_mes == 2 && (dia < 1 || dia > 29))
                throw new Exception("Data inválida");
            _dia = dia;
        }
    }

    public Integer GetMes(){
        return _mes;
    }
    
    public void SetMes(Integer mes) throws Exception{
        if(mes < 1 || mes > 12)
        throw new Exception("Mês inválido");
    }

    public String GetMesString(){
        return _meses.get(_mes);
    }
}
