package com.betsesportivas.Controllers;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.ResourceBundle;

import com.betsesportivas.App;
import com.betsesportivas.DAO.IJogadorDAO;
import com.betsesportivas.DAO.JogadorDAO;
import com.betsesportivas.DTO.JogadorDTO;
import com.betsesportivas.Database.Db;
import com.betsesportivas.Domain.Jogador;
import com.betsesportivas.Helpers.ErrorHelper;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.Pane;

public class DashboardJogadoresController implements Initializable {

    @FXML
    private Button btn_pane_criar_jogador;
    @FXML
    private Button btn_pane_editar_jogador;
    @FXML
    private TextField textFieldNomeJogador;
    @FXML
    private TextField textFieldEmailJogador;
    @FXML
    private TextField textFieldEditarNomeJogador;
    @FXML
    private TextField textFieldEditarEmailJogador;
    @FXML
    private TextField valorDeposito;
    @FXML
    private CheckBox checkBoxEditarAtivoJogador;
    @FXML
    private DatePicker datePickerDataNascimento;
    @FXML
    private TableView<JogadorDTO> tableViewJogador;
    @FXML
    private TableView<JogadorDTO> tableViewJogadorDeposito;
    @FXML
    private TableColumn<JogadorDTO, String> tableColumnNomeJogadorDeposito;
    @FXML
    private TableColumn<JogadorDTO, Double> tableColumnSaldoJogadorDeposito;
    @FXML
    private TableColumn<JogadorDTO, String> tableColumnNomeJogador;
    @FXML
    private TableColumn<JogadorDTO, String> tableColumnEmailJogador;
    @FXML
    private TableColumn<JogadorDTO, Double> tableColumnSaldoJogador;
    @FXML
    private Pane pane_criar;
    @FXML
    private Pane pane_editar;
    @FXML
    private Pane pane_deposito;
    @FXML
    private List<JogadorDTO> listJogadoresDTO;
    @FXML
    private ObservableList<JogadorDTO> observableListJogadoresDTO;
    @FXML
    private JogadorDTO onEditJogadorDTO;
    @FXML
    private JogadorDTO onDepositoJogadorDTO;
    @FXML
    private Label labelSaldoEditar;

    private final Db Database = new Db();
    private final IJogadorDAO<Jogador, JogadorDTO> jogadorDAO = new JogadorDAO();

    // #region menus
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

    public DashboardJogadoresController() throws SQLException {
        jogadorDAO.Connect(Database.Connect());
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        try {
            initializeTableView();
        } catch (SQLException e) {
            e.getStackTrace();
        }
        setEvents();
    }

    @FXML
    private void initializeTableView() throws SQLException {
        try {
            tableColumnNomeJogador.setCellValueFactory(new PropertyValueFactory<>("nome"));
            tableColumnEmailJogador.setCellValueFactory(new PropertyValueFactory<>("email"));
            tableColumnSaldoJogador.setCellValueFactory(new PropertyValueFactory<>("saldo"));
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }

        populateTableViewData();
    }

    private void populateTableViewData() throws SQLException {
        try {
            List<JogadorDTO> jogadoresDTO = jogadorDAO.BuscarTodosOsDTO();
            observableListJogadoresDTO = FXCollections.observableArrayList(jogadoresDTO);
            tableViewJogador.setItems(observableListJogadoresDTO);
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }

    }

    @FXML
    private void initializeTableViewDeposito() throws SQLException {
        try {
            tableColumnNomeJogadorDeposito.setCellValueFactory(new PropertyValueFactory<>("nome"));
            tableColumnSaldoJogadorDeposito.setCellValueFactory(new PropertyValueFactory<>("saldo"));
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }

        populateDepositoTableViewData();
    }

    private void populateDepositoTableViewData() throws SQLException {
        try {
            List<JogadorDTO> jogadoresDTO = jogadorDAO.BuscarTodosOsDTO();
            observableListJogadoresDTO = FXCollections.observableArrayList(jogadoresDTO);
            tableViewJogadorDeposito.setItems(observableListJogadoresDTO);
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }

    }

    @FXML
    private void handleFecharCriar() {
        textFieldEmailJogador.setText("");
        textFieldNomeJogador.setText("");
        pane_criar.setVisible(false);
    }

    @FXML
    private void handleFecharEditar() {
        textFieldEditarEmailJogador.setText("");
        textFieldEditarNomeJogador.setText("");
        pane_editar.setVisible(false);
    }

    @FXML
    private void handleFecharDeposito() {
        valorDeposito.setText("0.0");
        pane_deposito.setVisible(false);
    }

    @FXML
    private void depositarSaldoHandler() throws SQLException {
        try {
            Double valorDepositoDouble = Double.parseDouble(valorDeposito.getText());
            onDepositoJogadorDTO = tableViewJogadorDeposito.getSelectionModel().getSelectedItem();
            if (onDepositoJogadorDTO == null)
                throw new Exception("Selecione um jogador");
            if (valorDepositoDouble <= 0)
                throw new Exception("Insira um valor válido para depósito");

            jogadorDAO.AdicionarSaldo(onDepositoJogadorDTO.getId(), valorDepositoDouble);

            populateTableViewData();
            valorDeposito.setText("0.0");
            pane_deposito.setVisible(false);
        } catch (Exception e) {
            ErrorHelper.ThrowErrorOnAlert(e);
        }

    }

    @FXML
    private void abrirPanelDeposito() throws SQLException {
        initializeTableViewDeposito();
        pane_deposito.setVisible(true);
    }

    @FXML
    private void abrirPanelCriarJogador() {
        textFieldEmailJogador.setText("");
        textFieldNomeJogador.setText("");
        datePickerDataNascimento.setValue(LocalDate.now());

        pane_criar.setVisible(true);
    }

    @FXML
    private void criarJogadorHandler() throws SQLException {
        try {
            String emailJogador = textFieldEmailJogador.getText();
            String nomeJogador = textFieldNomeJogador.getText();
            LocalDate dataNascimento = datePickerDataNascimento.getValue();
            if (emailJogador.isEmpty())
                throw new Exception("Insira o email do jogador");
            if (nomeJogador.isEmpty())
                throw new Exception("Insira o nome do jogador");
            if ((LocalDateTime.now().getYear() - dataNascimento.getYear()) < 18)
                throw new Exception("O jogador deve ser maior de 18 anos para ser cadastrado no sistema");

            JogadorDTO jogadorDTO = new JogadorDTO(0, nomeJogador, dataNascimento, 0.0, emailJogador, true);

            jogadorDAO.CriarPorDTO(jogadorDTO);

            // jogadorDA
            populateTableViewData();
            pane_criar.setVisible(false);
        } catch (Exception ex) {
            ErrorHelper.ThrowErrorOnAlert(ex);
        }
    }

    @FXML
    private void abrirEdicaoJogador() throws SQLException {
        onEditJogadorDTO = tableViewJogador.getSelectionModel().selectedItemProperty().getValue();
        if (onEditJogadorDTO != null) {
            textFieldEditarNomeJogador.setText(onEditJogadorDTO.getNome());
            textFieldEditarEmailJogador.setText(onEditJogadorDTO.getEmail());
            checkBoxEditarAtivoJogador.setSelected(onEditJogadorDTO.isAtivado());
            labelSaldoEditar.setText(onEditJogadorDTO.getSaldo().toString());
            pane_editar.setVisible(true);
        } else {
            pane_editar.setVisible(false);
        }
    }

    @FXML
    private void editarJogadorHandler() throws SQLException {
        try {
            String emailJogador = textFieldEditarEmailJogador.getText();
            String nomeJogador = textFieldEditarNomeJogador.getText();
            if (emailJogador.isEmpty())
                throw new Exception("Insira o email do jogador");
            if (nomeJogador.isEmpty())
                throw new Exception("Insira o nome do jogador");
            JogadorDTO jogadorDTO = new JogadorDTO(onEditJogadorDTO.getId(), nomeJogador, null, 0.0, emailJogador,
                    true);

            jogadorDAO.EditarPorDTO(jogadorDTO);

            // jogadorDA
            populateTableViewData();
            pane_editar.setVisible(false);
        } catch (Exception e) {
            ErrorHelper.ThrowErrorOnAlert(e);
        }
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
        menu_categorias_relatorio.setOnAction((ActionEvent event) -> {
            try {
                App.setNewScene("RelatorioCategorias");
            } catch (IOException ex) {
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
        menu_competicoes_relatorio.setOnAction((ActionEvent event) -> {
            try {
                App.setNewScene("RelatorioCompeticoes");
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