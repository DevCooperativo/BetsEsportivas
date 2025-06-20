package com.betsesportivas.Controllers;

import java.io.IOException;
import java.net.Socket;
import java.net.URL;
import java.sql.SQLException;
import java.util.List;
import java.util.ResourceBundle;

import com.betsesportivas.App;
import com.betsesportivas.DTO.JogadorDTO;
import com.betsesportivas.Runnable.RunnableClient;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import sockets.thread.ContadorGrupo;

public class DashboardGruposController implements Initializable {

    private final int idGrupo = 2;
    private final String ipDestino = "127.0.0.1";
    private final int portaDestino = 12345;

    @FXML
    private MenuItem menu_apostas_dashboard;
    @FXML
    private MenuItem menu_apostas_relatorio;
    @FXML
    private MenuItem menu_atletas_dashboard;
    @FXML
    private MenuItem menu_competicoes_dashboard;
    @FXML
    private MenuItem menu_categorias_dashboard;

    @FXML
    private MenuItem menu_jogadores_dashboard;
    @FXML
    private MenuItem menu_jogadores_relatorio;
    @FXML
    private MenuItem menu_grupos_dashboard;

    // @FXML
    // private List<ContadorGrupo> listJogadoresDTO;
    @FXML
    private ObservableList<ContadorGrupo> observableContadorGrupos;

    @FXML
    private TableView<ContadorGrupo> tblViewGrupo;
    @FXML
    private TableColumn<ContadorGrupo, Integer> tblColGrupoPos;
    @FXML
    private TableColumn<ContadorGrupo, String> tblColGrupoGrupo;
    @FXML
    private TableColumn<ContadorGrupo, Integer> tblColGrupoUtilizacoes;

    @FXML
    private Label labelLog;

    @FXML
    private void initializeTableView() {
        tblColGrupoPos.setCellValueFactory(new PropertyValueFactory<>("idGrupo"));
        tblColGrupoGrupo.setCellValueFactory(new PropertyValueFactory<>("nomeGrupo"));
        tblColGrupoUtilizacoes.setCellValueFactory(new PropertyValueFactory<>("quantidadeUtilizacoes"));
        tblViewGrupo.setItems(observableContadorGrupos);
    }

    @Override
    public void initialize(URL arg0, ResourceBundle arg1) {
        try {
            Socket socket = new Socket(ipDestino, portaDestino);
            RunnableClient runnable = new RunnableClient(socket, idGrupo, observableContadorGrupos);
            Thread thread = new Thread(runnable);
            thread.start();
            
            // espera a thread terminar
            thread.join();
            // observableContadorGrupos = FXCollections.observableArrayList(runnable.getContadorGrupos());
            initializeTableView();

        } catch (IOException ex) {
            ex.printStackTrace();
        } catch (InterruptedException ex) {
            ex.printStackTrace();
        }
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
        menu_apostas_relatorio.setOnAction((ActionEvent event) -> {
            try {
                App.setNewScene("RelatorioApostas");
            } catch (IOException ex) {
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

        menu_jogadores_dashboard.setOnAction((ActionEvent event) -> {
            try {
                App.setNewScene("DashboardJogadores");
            } catch (IOException ex) {
                ex.getStackTrace();
            }
        });
        menu_jogadores_relatorio.setOnAction((ActionEvent event) -> {
            try {
                App.setNewScene("RelatorioJogadores");
            } catch (IOException ex) {
                ex.getStackTrace();
            }
        });
        menu_grupos_dashboard.setOnAction((ActionEvent event) -> {
            try {
                App.setNewScene("DashboardGrupos");
            } catch (IOException ex) {
                ex.getStackTrace();
            }
        });
    }
}
