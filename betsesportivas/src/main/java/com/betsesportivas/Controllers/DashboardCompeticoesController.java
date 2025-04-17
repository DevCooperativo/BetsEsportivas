package com.betsesportivas.Controllers;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.util.ResourceBundle;

import com.betsesportivas.App;
import com.betsesportivas.Domain.Competicao;
import com.betsesportivas.Domain.Enums.CompeticaoStatusEnum;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.MenuItem;
import javafx.scene.control.SplitPane;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;

public class DashboardCompeticoesController implements Initializable {

    @FXML
    private AnchorPane anchorPane_dashboard;

    @FXML
    private SplitPane splitPane_dashboard;

    @FXML
    private TableView tblView_competicoes;

    @FXML
    private TableColumn<Competicao, String> tblViewColumn_competicoes_categoria;

    @FXML
    private TableColumn<Competicao, LocalDate> tblViewColumn_competicoes_inicioApostas;

    @FXML
    private TableColumn<Competicao, LocalDate> tblViewColumn_competicoes_terminoApostas;

    @FXML
    private TableColumn<Competicao, LocalDate> tblViewColumn_competicoes_dataOcorrencia;

    @FXML
    private TableColumn<Competicao, Integer> tblViewColumn_competicoes_qtdApostas;

    @FXML
    private TableColumn<Competicao, Double> tblViewColumn_competicoes_valorEmJogo;

    @FXML
    private TableColumn<Competicao, CompeticaoStatusEnum> tblViewColumn_competicoes_status;

    @FXML
    private Button btn_refresh;

    @FXML
    private Button btn_criarEvento;

    @FXML
    private Button btn_editarEvento;

    @FXML
    private Button btn_detalhesDoEvento;

    @FXML
    private Button btn_alterarStatusDoEvento;

    @FXML
    private MenuItem menu_competicoes_competicoes;

    @FXML
    private MenuItem menu_clientes_clientes;

    @FXML
    private MenuItem menu_apostas_apostas;

    @FXML
    private void switchToSecondary() throws IOException {
        App.setRoot("secondary");
    }

    private void populateTable() {

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

    @FXML
    private void setEvents() {
        btn_refresh.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                populateTable();
            }
        });
        // menu_competicoes_competicoes.setOnAction(new EventHandler<ActionEvent>() {
        // @Override
        // public void handle(ActionEvent event) {
        // System.out.println(".()");
        // try {
        // App.setNewScene("DashboardCompeticoes");
        // } catch (IOException ex) {
        // ex.getStackTrace();
        // }
        // }
        // });

        menu_apostas_apostas.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                try {
                    App.setNewScene("RelatorioApostas");
                } catch (IOException ex) {
                    ex.getStackTrace();
                }
            }

        });

    }

}
