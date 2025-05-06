package com.betsesportivas.Helpers;

public class ParserHelper {
    public static int tryParseInt(String value){
        try {
            return Integer.parseInt(value);
        } catch (Exception e) {
            return 0;
        }
    }
}
