package com.betsesportivas.Helpers;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;

public class ParserHelper {
    public static int tryParseInt(String value){
        try {
            return Integer.parseInt(value);
        } catch (Exception e) {
            return 0;
        }
    }

    
    public static <T> String parseString(T value){
        return value.toString();
    }

    public static String doubleToString(double value){

        // Define o separador decimal como vírgula
        DecimalFormatSymbols symbols = new DecimalFormatSymbols();
        symbols.setDecimalSeparator('.');

        DecimalFormat df = new DecimalFormat("#0.00", symbols);

        // Formata o valor com separador decimal personalizado
        String valorFormatado = df.format(value);

        return (valorFormatado);
    }

}
