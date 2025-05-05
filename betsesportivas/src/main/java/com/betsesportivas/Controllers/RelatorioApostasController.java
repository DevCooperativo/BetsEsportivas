package com.betsesportivas.Controllers;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import com.betsesportivas.App;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.MenuItem;

public class RelatorioApostasController implements Initializable {

    @FXML
    private MenuItem menu_competicoes_dashboard;
    @FXML
    private MenuItem menu_competicoes_relatorio;
    @FXML
    private MenuItem menu_apostas_dashboard;
    @FXML
    private MenuItem menu_apostas_relatorio;
    @FXML
    private MenuItem menu_atletas_dashboard;
    @FXML
    private MenuItem menu_atletas_relatorio;
    @FXML
    private MenuItem menu_categorias_dashboard;
    @FXML
    private MenuItem menu_categorias_relatorio;
    @FXML
    private MenuItem menu_jogadores_dashboard;
    @FXML
    private MenuItem menu_jogadores_relatorio;
    // #endregion

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        setEvents();
    }

    @FXML
    private void setEvents() {
        setMenuEvents();
    }

    // #region menuEvents
    @FXML
    private void setMenuEvents() {
        menu_categorias_dashboard.setOnAction((ActionEvent event) -> {
            try {
                App.setNewScene("DashboardCategorias");
            } catch (IOException e) {
                e.getStackTrace();
            }
        });
        menu_categorias_relatorio.setOnAction((ActionEvent event) ->{
            try{
                App.setNewScene("RelatorioCategorias");
            }catch(IOException ex){
                ex.getStackTrace();
            }
        });
        menu_competicoes_dashboard.setOnAction((ActionEvent event) -> {
            try {
                App.setNewScene("DashboardCompeticoes");
            } catch (IOException ex) {
                ex.getStackTrace();
            }
        });
        menu_competicoes_relatorio.setOnAction((ActionEvent event) ->{
            try{
                App.setNewScene("RelatorioCompeticoes");
            }catch(IOException ex){
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
        menu_apostas_relatorio.setOnAction((ActionEvent event) ->{
            try{
                App.setNewScene("RelatorioApostas");
            }catch(IOException ex){
                ex.getStackTrace();
            }
        });

        menu_atletas_dashboard.setOnAction((ActionEvent event) -> {
            try {
                App.setNewScene("DashboardAtletas");
            } catch (IOException ex) {
                ex.getStackTrace();
            }
        });

        menu_atletas_relatorio.setOnAction((ActionEvent event) -> {
            try {
                App.setNewScene("RelatorioAtletas");
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
        menu_jogadores_relatorio.setOnAction((ActionEvent event) ->{
            try{
                App.setNewScene("RelatorioJogadores");
            }catch(IOException ex){
                ex.getStackTrace();
            }
        });
    }
    // #endregion
}