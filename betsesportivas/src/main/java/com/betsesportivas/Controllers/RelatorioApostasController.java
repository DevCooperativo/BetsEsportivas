package com.betsesportivas.Controllers;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.List;
import java.util.ResourceBundle;

import com.betsesportivas.App;
import com.betsesportivas.DAO.ApostaDAO;
import com.betsesportivas.DAO.IApostaDAO;
import com.betsesportivas.DTO.ApostaDTO;
import com.betsesportivas.DTO.RelatorioApostasDTO;
import com.betsesportivas.Database.Db;
import com.betsesportivas.Domain.Aposta;
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

public class RelatorioApostasController implements Initializable {

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
    @FXML
    private MenuItem menu_grupos_dashboard;
    // #endregion

    @FXML
    private TableColumn<RelatorioApostasDTO, String> tableColumnCompeticao;
    @FXML
    private TableColumn<RelatorioApostasDTO, Integer> tableColumnVencedores;
    @FXML
    private TableView<RelatorioApostasDTO> tableViewVencedoresCompeticao;

    @FXML
    private ObservableList<RelatorioApostasDTO> observableRelatorio;

    private Db database = new Db();
    private IApostaDAO<Aposta, ApostaDTO> apostaDAO = new ApostaDAO();

    public RelatorioApostasController() throws SQLException {
        apostaDAO.Connect(database.Connect());
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

    private void setTableViewFields() throws SQLException {
        tableColumnCompeticao.setCellValueFactory(new PropertyValueFactory<>("nomeCompeticao"));
        tableColumnVencedores.setCellValueFactory(new PropertyValueFactory<>("quantidadeVencedores"));
        populateTableViewData();
    }

    private void populateTableViewData() throws SQLException {
        List<RelatorioApostasDTO> relatorioResult = apostaDAO.RecuperarRelatorioApostas();
        observableRelatorio = FXCollections.observableArrayList(relatorioResult);
        tableViewVencedoresCompeticao.setItems(observableRelatorio);
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
    // #endregion

    @FXML
    public void handleExportarPDF() throws JRException, SQLException {
        URL url = getClass().getResource("/com/betsesportivas/Relatorios/RelatorioApostas.jasper");
        JasperReport report = (JasperReport) JRLoader.loadObject(url);
        JasperPrint print = JasperFillManager.fillReport(report, null, database.Connect());
        JasperViewer viewer = new JasperViewer(print, false);
        viewer.setVisible(true);
    }

}