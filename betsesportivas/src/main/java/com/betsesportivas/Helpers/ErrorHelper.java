package com.betsesportivas.Helpers;

import javafx.scene.control.Alert;

public class ErrorHelper {
    public static void ThrowErrorOnAlert(Exception ex) {
        if (ex instanceof NullPointerException) {
            new Alert(Alert.AlertType.ERROR, "Um ou mais campos foram deixados em branco. Preencha-os").show();
        } else {
            new Alert(Alert.AlertType.ERROR, ex.getMessage()).show();
        }
    }
}
