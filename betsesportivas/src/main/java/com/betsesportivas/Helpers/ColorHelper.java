package com.betsesportivas.Helpers;

import javafx.scene.paint.Color;

public class ColorHelper {
    public static String toHexString(Color value) {
        return "#" + (format(value.getRed()) + format(value.getGreen()) + format(value.getBlue()))
                .toUpperCase();
    }

    private static String format(double val) {
        String in = Integer.toHexString((int) Math.round(val * 255));
        return in.length() == 1 ? "0" + in : in;
    }

    public static Color fromHexString(String hex) {
        if (hex == null || !hex.matches("#?[0-9a-fA-F]{6}([0-9a-fA-F]{2})?")) {
            throw new IllegalArgumentException("Hex inválido. Esperado: #RRGGBB ou #RRGGBBAA");
        }

        hex = hex.replace("#", "");

        int r = Integer.parseInt(hex.substring(0, 2), 16);
        int g = Integer.parseInt(hex.substring(2, 4), 16);
        int b = Integer.parseInt(hex.substring(4, 6), 16);
        int a = hex.length() == 8 ? Integer.parseInt(hex.substring(6, 8), 16) : 255;

        var color = new Color(r / 255.0, g / 255.0, b / 255.0, a / 255.0);
        return color;
    }
}
