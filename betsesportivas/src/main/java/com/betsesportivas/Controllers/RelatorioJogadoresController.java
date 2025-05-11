package com.betsesportivas.Controllers;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.List;
import java.util.ResourceBundle;

import com.betsesportivas.App;
import com.betsesportivas.DAO.ApostaDAO;
import com.betsesportivas.DAO.IApostaDAO;
import com.betsesportivas.DAO.IJogadorDAO;
import com.betsesportivas.DAO.JogadorDAO;
import com.betsesportivas.DTO.ApostaDTO;
import com.betsesportivas.DTO.JogadorDTO;
import com.betsesportivas.DTO.RelatorioApostasDTO;
import com.betsesportivas.DTO.RelatorioJogadoresDTO;
import com.betsesportivas.DTO.RelatorioJogadoresDTO;
import com.betsesportivas.Database.Db;
import com.betsesportivas.Domain.Aposta;
import com.betsesportivas.Domain.Jogador;
import com.betsesportivas.Helpers.ErrorHelper;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.util.JRLoader;
import net.sf.jasperreports.view.JasperViewer;

public class RelatorioJogadoresController implements Initializable {
    // #region menus
    @FXML
    private MenuItem menu_competicoes_dashboard;
 
    @FXML
    private MenuItem menu_apostas_dashboard;
    @FXML
    private MenuItem menu_apostas_relatorio;
    @FXML
    private MenuItem menu_atletas_dashboard;
 
    @FXML
    private MenuItem menu_categorias_dashboard;
 
    @FXML
    private MenuItem menu_jogadores_dashboard;
    @FXML
    private MenuItem menu_jogadores_relatorio;
    // #endregion

    @FXML
    private TableColumn<RelatorioJogadoresDTO, String> tableColumnJogador;
    @FXML
    private TableColumn<RelatorioJogadoresDTO, Double> tableColumnTotalApostado;
    @FXML
    private TableColumn<RelatorioJogadoresDTO, Double> tableColumnTotalGanho;
    @FXML
    private TableColumn<RelatorioJogadoresDTO, Double> tableColumnTotalLucro;
    @FXML
    private TableView<RelatorioJogadoresDTO> tableViewLucroJogadores;

    @FXML
    private ObservableList<RelatorioJogadoresDTO> observableRelatorio;

    private Db database = new Db();
    private IJogadorDAO<Jogador, JogadorDTO> jogadorDAO = new JogadorDAO();

    public RelatorioJogadoresController() throws SQLException {
        jogadorDAO.Connect(database.Connect());
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        try {
            setEvents();
            setTableViewFields();
        } catch (SQLException e) {
            ErrorHelper.ThrowErrorOnAlert(e);
        }
    }

    private void setEvents() {
        setMenuEvents();
    }

    private void setTableViewFields() throws SQLException {
        tableColumnJogador.setCellValueFactory(new PropertyValueFactory<>("nomeJogador"));
        tableColumnTotalApostado.setCellValueFactory(new PropertyValueFactory<>("totalApostado"));
        tableColumnTotalGanho.setCellValueFactory(new PropertyValueFactory<>("totalGanho"));
        tableColumnTotalLucro.setCellValueFactory(new PropertyValueFactory<>("lucro"));
        populateTableViewData();
    }

    private void populateTableViewData() throws SQLException {
        List<RelatorioJogadoresDTO> relatorioResult = jogadorDAO.buscarRelatorioLucroPorCliente();
        observableRelatorio = FXCollections.observableArrayList(relatorioResult);
        tableViewLucroJogadores.setItems(observableRelatorio);
    }

    @FXML
    public void handleExportarPDF() throws JRException, SQLException {
        URL url = getClass().getResource("/com/betsesportivas/Relatorios/RelatorioJogadores.jasper");
        JasperReport report = (JasperReport) JRLoader.loadObject(url);
        JasperPrint print = JasperFillManager.fillReport(report, null, database.Connect());
        JasperViewer viewer = new JasperViewer(print, false);
        viewer.setVisible(true);
    }
    // #endregion

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
    }
    // #endregion
}