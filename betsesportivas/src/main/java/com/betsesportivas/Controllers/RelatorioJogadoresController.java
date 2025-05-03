package com.betsesportivas.Controllers;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import com.betsesportivas.App;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.MenuItem;

public class RelatorioJogadoresController implements Initializable {
    @FXML
    private MenuItem menu_competicoes_dashboard;

    @FXML
    private MenuItem menu_jogadores_dashboard;

    @FXML
    private MenuItem menu_apostas_dashboard;

    @FXML
    private MenuItem menu_categorias_dashboard;

    @FXML
    private void switchToPrimary() throws IOException {
        App.setRoot("primary");
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        setEvents();
    }

    @FXML
    private void setEvents() {
        setMenuEvents();
    }

    @FXML
    private void setMenuEvents() {
        menu_categorias_dashboard.setOnAction((ActionEvent event) -> {
            try {
                App.setNewScene("DashboardCategorias");
            } catch (IOException e) {
                e.getStackTrace();
            }
        });
        menu_competicoes_dashboard.setOnAction((ActionEvent event) -> {
            System.out.println(".()");
            try {
                App.setNewScene("DashboardCompeticoes");
            } catch (IOException ex) {
                ex.getStackTrace();
            }
        });

        menu_apostas_dashboard.setOnAction((ActionEvent event) -> {
            try {
                App.setNewScene("DashboardApostas");
            } catch (IOException ex) {
                ex.getStackTrace();
            }
        });

        menu_jogadores_dashboard.setOnAction((ActionEvent event) -> {
            try {
                App.setNewScene("DashboardJogadores");
            } catch (IOException ex) {
                ex.getStackTrace();
            }
        });
    }
}