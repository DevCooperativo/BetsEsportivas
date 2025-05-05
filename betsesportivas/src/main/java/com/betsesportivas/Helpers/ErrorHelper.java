package com.betsesportivas.Helpers;

import javafx.scene.control.Alert;

public class ErrorHelper {
    public static void ThrowErrorOnAlert(Exception ex){
        new Alert(Alert.AlertType.ERROR, ex.getMessage()).show();
    }
}
