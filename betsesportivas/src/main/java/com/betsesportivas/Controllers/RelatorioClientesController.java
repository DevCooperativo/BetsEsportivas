package com.betsesportivas.Controllers;

import java.io.IOException;

import com.betsesportivas.App;

import javafx.fxml.FXML;

public class RelatorioClientesController {

    @FXML
    private void switchToPrimary() throws IOException {
        App.setRoot("primary");
    }
}