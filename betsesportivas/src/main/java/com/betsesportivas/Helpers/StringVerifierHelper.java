package com.betsesportivas.Helpers;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

public class StringVerifierHelper {
    public static boolean isDigit(String value) {
        List<Character> numbers = new LinkedList<Character>(
                Arrays.asList('0', '1', '2', '3', '4', '5', '6', '7', '8', '9'));
        for(char i : value.toCharArray()){
            if(!numbers.contains(i))return false;
        }
        return true;
    }
}
