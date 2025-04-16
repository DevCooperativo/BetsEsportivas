package com.betsesportivas.Controllers;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import com.betsesportivas.App;
import com.betsesportivas.Domain.Competicao;
import com.betsesportivas.Domain.Enums.CompeticaoStatusEnum;
import com.betsesportivas.Domain.Utils.Data;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Menu;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

public class DashboardController implements Initializable {

    @FXML
    private TableView tblView_competicoes;

    @FXML
    private TableColumn<Competicao, String> tblViewColumn_competicoes_categoria;

    @FXML
    private TableColumn<Competicao, Data> tblViewColumn_competicoes_inicioApostas;

    @FXML
    private TableColumn<Competicao, Data> tblViewColumn_competicoes_terminoApostas;

    @FXML
    private TableColumn<Competicao, Data> tblViewColumn_competicoes_dataOcorrencia;

    @FXML
    private TableColumn<Competicao, Integer> tblViewColumn_competicoes_qtdApostas;

    @FXML
    private TableColumn<Competicao, Double> tblViewColumn_competicoes_valorEmJogo;

    @FXML
    private TableColumn<Competicao, CompeticaoStatusEnum> tblViewColumn_competicoes_status;

    @FXML
    private Button btn_refresh;

    @FXML
    private Button btn_inserirResultado;

    @FXML
    private Button btn_finalizarEvento;

    @FXML
    private Button btn_inserirCompetidor;

    @FXML
    private Button btn_acessarEvento;

    @FXML
    private Button btn_inserirAposta;

    @FXML
    private Button btn_abrirFecharEvento;

    @FXML
    private Menu menu_dashboard;

    @FXML
    private void switchToSecondary() throws IOException {
        App.setRoot("secondary");
    }

    private void populateTable(){

    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        tblViewColumn_competicoes_categoria.setCellValueFactory(new PropertyValueFactory<>("categoria"));
        tblViewColumn_competicoes_inicioApostas.setCellValueFactory(new PropertyValueFactory<>("inicioApostas"));
        tblViewColumn_competicoes_terminoApostas.setCellValueFactory(new PropertyValueFactory<>("terminoApostas"));
        tblViewColumn_competicoes_dataOcorrencia.setCellValueFactory(new PropertyValueFactory<>("dataOcorrencia"));
        tblViewColumn_competicoes_qtdApostas.setCellValueFactory(new PropertyValueFactory<>("qtdApostas"));
        tblViewColumn_competicoes_valorEmJogo.setCellValueFactory(new PropertyValueFactory<>("valorEmJogo"));
        tblViewColumn_competicoes_status.setCellValueFactory(new PropertyValueFactory<>("status"));

        setEvents();
    }

    private void setEvents() {
        btn_refresh.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                populateTable();
            }
        });

        menu_dashboard.setOnAction(new EventHandler<ActionEvent>(){
            @Override
            public void handle(ActionEvent event) {
                System.out.println(".()");
            }
            
        });
    }

}
