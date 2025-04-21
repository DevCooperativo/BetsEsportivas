package com.betsesportivas.Controllers;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import com.betsesportivas.App;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.MenuItem;
import javafx.scene.layout.Pane;

public class DashboardCategoriasController implements Initializable {

    @FXML
    private Pane pane_criar;

    @FXML
    private Button btn_criarCategoria;

    @FXML
    private Button btn_pane_criar_fechar;

    @FXML
    private MenuItem menu_categorias_dashboard;
    @FXML
    private MenuItem menu_competicoes_dashboard;
    @FXML
    private MenuItem menu_apostas_dashboard;
    @FXML
    private MenuItem menu_clientes_dashboard;


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        setEvents();
    }

    @FXML
    private void setEvents() {

        menu_categorias_dashboard.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                try {
                    App.setNewScene("DashboardCategorias");
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

        });
        menu_competicoes_dashboard.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                System.out.println(".()");
                try {
                    App.setNewScene("DashboardCompeticoes");
                } catch (IOException ex) {
                    ex.getStackTrace();
                }
            }
        });

        menu_apostas_dashboard.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                try {
                    App.setNewScene("DashboardApostas");
                } catch (IOException ex) {
                    ex.getStackTrace();
                }
            }

        });

        menu_clientes_dashboard.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                try {
                    App.setNewScene("DashboardClientes");
                } catch (IOException ex) {
                    ex.getStackTrace();
                }
            }
        });

    }
}