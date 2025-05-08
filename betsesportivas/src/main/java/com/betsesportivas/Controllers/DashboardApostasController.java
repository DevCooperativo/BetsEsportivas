package com.betsesportivas.Controllers;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

import com.betsesportivas.Abstractions.ComboboxItem;
import com.betsesportivas.App;
import com.betsesportivas.DAO.ApostaDAO;
import com.betsesportivas.DAO.CompeticaoDAO;
import com.betsesportivas.DAO.CompetidorDAO;
import com.betsesportivas.DAO.JogadorDAO;
import com.betsesportivas.DTO.ApostaDTO;
import com.betsesportivas.Database.Db;
import com.betsesportivas.Helpers.ErrorHelper;
import com.betsesportivas.Helpers.FieldsHelper;
import com.betsesportivas.Helpers.ParserHelper;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.MenuItem;
import javafx.scene.control.Slider;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;

public class DashboardApostasController implements Initializable {

    private Db database = new Db();
    private ApostaDAO apostaDAO = new ApostaDAO();
    private CompetidorDAO competidorDAO = new CompetidorDAO();
    private CompeticaoDAO competicaoDAO = new CompeticaoDAO();
    private JogadorDAO jogadorDAO = new JogadorDAO();

    public DashboardApostasController() throws SQLException {
        apostaDAO.Connect(database.Connect());
        competidorDAO.Connect(database.Connect());
        competicaoDAO.Connect(database.Connect());
        jogadorDAO.Connect(database.Connect());
    }

    @FXML
    private ObservableList<ApostaDTO> observableAposta;
    @FXML
    private ObservableList<ComboboxItem> observableJogador;
    @FXML
    private ObservableList<ComboboxItem> observableCompetidor;
    @FXML
    private ObservableList<ComboboxItem> observableCompeticao;

    @FXML
    private TableView<ApostaDTO> tblViewApostas;
    @FXML
    private TableColumn<ApostaDTO, String> tblViewColumnApostasJogador;
    @FXML
    private TableColumn<ApostaDTO, Double> tblViewColumnApostasValor;
    @FXML
    private TableColumn<ApostaDTO, String> tblViewColumnApostasCompeticao;
    @FXML
    private TableColumn<ApostaDTO, String> tblViewColumnApostasCompetidor;

    @FXML
    private Button btnCriarAposta;

    @FXML
    private Pane paneCriar;
    @FXML
    private ComboBox<ComboboxItem> comboBoxJogador;
    @FXML
    private ComboBox<ComboboxItem> comboBoxCompeticao;
    @FXML
    private TextField textFieldValor;
    @FXML
    private ComboBox<ComboboxItem> comboBoxCompetidor;
    @FXML
    private Slider sliderOdd;
    @FXML
    private Text textOdd;
    @FXML
    private Button btnSalvar;
    @FXML
    private Button btnFechar;

    @FXML
    private ApostaDTO onEditApostaDTO;
    @FXML
    private Pane paneEditar;
    @FXML
    private ComboBox<ComboboxItem> comboBoxEditarJogador;
    @FXML
    private ComboBox<ComboboxItem> comboBoxEditarCompeticao;
    @FXML
    private TextField textFieldEditarValor;
    @FXML
    private ComboBox<ComboboxItem> comboBoxEditarCompetidor;
    @FXML
    private Slider sliderEditarOdd;
    @FXML
    private Text textEditarOdd;
    @FXML
    private Button btnEditarSalvar;
    @FXML
    private Button btnEditarExcluir;
    @FXML
    private Button btnEditarFechar;

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

    private void setTableViewFields() throws SQLException {
        tblViewColumnApostasCompeticao.setCellValueFactory(new PropertyValueFactory<>("NomeCompeticao"));
        tblViewColumnApostasCompetidor.setCellValueFactory(new PropertyValueFactory<>("NomeCompetidor"));
        tblViewColumnApostasValor.setCellValueFactory(new PropertyValueFactory<>("valor"));
        tblViewColumnApostasJogador.setCellValueFactory(new PropertyValueFactory<>("NomeJogador"));
        populateTableView();
    }

    private void setComboBoxes() {
        FieldsHelper.<ComboboxItem>setComboBoxProperties(comboBoxCompeticao);
        FieldsHelper.<ComboboxItem>setComboBoxProperties(comboBoxCompetidor);
        FieldsHelper.<ComboboxItem>setComboBoxProperties(comboBoxJogador);
        FieldsHelper.<ComboboxItem>setComboBoxProperties(comboBoxEditarCompeticao);
        FieldsHelper.<ComboboxItem>setComboBoxProperties(comboBoxEditarCompetidor);
        FieldsHelper.<ComboboxItem>setComboBoxProperties(comboBoxEditarJogador);
    }

    private void setOddTexts() {
        textOdd.setText(ParserHelper.doubleToString(sliderOdd.getValue()));
        textEditarOdd.setText(ParserHelper.doubleToString(sliderEditarOdd.getValue()));
        sliderOdd.valueProperty().addListener((observable, oldValue, newValue) -> {
            textOdd.setText(ParserHelper.doubleToString(newValue.doubleValue()));
        });
        sliderEditarOdd.valueProperty().addListener((observable, oldValue, newValue) -> {
            textEditarOdd.setText(ParserHelper.doubleToString(newValue.doubleValue()));
        });
    }

    private void setFields() throws SQLException {
        setTableViewFields();
        setComboBoxes();
        setOddTexts();
    }

    private void populateTableView() throws SQLException {
        observableAposta = FXCollections.observableArrayList(apostaDAO.BuscarTodosOsDTO());
        tblViewApostas.setItems(observableAposta);
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        try {
            setFields();
            setEvents();
            clearCreate();
        } catch (SQLException e) {
            ErrorHelper.ThrowErrorOnAlert(e);
        }
    }

    private void clearCreate() {
        comboBoxJogador.setValue(null);
        comboBoxCompeticao.setValue(null);
        comboBoxCompetidor.setValue(null);
        sliderOdd.setValue(2);
    }

    private void populateComboboxes() throws SQLException {
        observableJogador = FXCollections.observableArrayList(jogadorDAO.BuscarTodosOsDTO().stream()
                .map(x -> new ComboboxItem(x.getNome(), x.getId())).collect(Collectors.toList()));
        comboBoxJogador.setItems(observableJogador);
        observableCompeticao = FXCollections.observableArrayList(competicaoDAO.BuscarTodosOsDTO().stream()
                .map(x -> new ComboboxItem(x.getNome(), x.getId())).collect(Collectors.toList()));
        comboBoxCompeticao.setItems(observableCompeticao);
        if (comboBoxCompeticao.getValue() != null) {
            observableCompetidor = FXCollections.observableArrayList(
                    competidorDAO
                            .BuscarCompetidoresParticipandoSemAposta(comboBoxCompeticao.getValue().getValue(),
                                    comboBoxJogador.getValue().getValue())
                            .stream()
                            .map(x -> new ComboboxItem(x.getNome(), x.getAtleta_id())).collect(Collectors.toList()));
            comboBoxCompetidor.setItems(observableCompetidor);
        }
    }

    private void openCreate() throws SQLException {
        populateComboboxes();
        paneCriar.setVisible(true);
    }

    private void closeCreate() {
        clearCreate();
        paneCriar.setVisible(false);
    }

    private void saveCreate() throws SQLException {
        try {
            ComboboxItem jogador = comboBoxJogador.getValue();
            ComboboxItem competicao = comboBoxCompeticao.getValue();
            ComboboxItem competidor = comboBoxCompetidor.getValue();
            double valor = Double.parseDouble(textFieldValor.getText());
            double odd = sliderOdd.getValue();
            apostaDAO.CriarPorDTO(
                    new ApostaDTO(jogador.getValue(), valor, competidor.getValue(), competicao.getValue(), odd));
            populateTableView();
            closeCreate();
        } catch (Exception e) {
            ErrorHelper.ThrowErrorOnAlert(e);
        }
    }

    private void clearEdit() {
        comboBoxEditarJogador.setValue(null);
        comboBoxEditarCompeticao.setValue(null);
        comboBoxEditarCompetidor.setValue(null);
        sliderEditarOdd.setValue(2);
    }

    private void openEdit() throws SQLException {
        comboBoxEditarJogador.setDisable(false);
        comboBoxEditarCompeticao.setDisable(false);
        comboBoxEditarCompetidor.setDisable(false);
        textFieldEditarValor.setDisable(false);
        sliderEditarOdd.setDisable(false);

        observableJogador = FXCollections.observableArrayList((jogadorDAO.BuscarTodosOsDTO().stream()
                .map(x -> new ComboboxItem(x.getNome(), x.getId())).collect(Collectors.toList())));
        comboBoxEditarJogador.setItems(observableJogador);
        comboBoxEditarJogador
                .setValue(new ComboboxItem(onEditApostaDTO.getNomeJogador(), onEditApostaDTO.getIdJogador()));

        observableCompeticao = FXCollections.observableArrayList(competicaoDAO.BuscarTodosOsDTO().stream()
                .map(x -> new ComboboxItem(x.getNome(), x.getId())).collect(Collectors.toList()));
        comboBoxEditarCompeticao.setItems(observableCompeticao);
        comboBoxEditarCompeticao
                .setValue(new ComboboxItem(onEditApostaDTO.getNomeCompeticao(), onEditApostaDTO.getIdCompeticao()));

        observableCompetidor = FXCollections
                .observableArrayList(competidorDAO.BuscarCompetidoresParticipandoSemAposta(
                        comboBoxEditarCompeticao.getValue().getValue(), comboBoxEditarJogador.getValue().getValue())
                        .stream()
                        .map(x -> new ComboboxItem(
                                String.format("%s %s", x.AtletaDTO.getNome(), x.AtletaDTO.getSobrenome()),
                                x.getAtleta_id()))
                        .collect(Collectors.toList()));
        comboBoxEditarCompetidor.setItems(observableCompetidor);
        comboBoxEditarCompetidor
                .setValue(new ComboboxItem(onEditApostaDTO.getNomeCompetidor(), onEditApostaDTO.getIdCompetidor()));

        textFieldEditarValor.setText(ParserHelper.doubleToString(onEditApostaDTO.getValor()));
        sliderEditarOdd.setValue(onEditApostaDTO.getOdd());

        paneEditar.setVisible(true);
    }

    private void closeEdit() {
        clearEdit();
        paneEditar.setVisible(false);
    }

    private void saveEdit() {
        try {
            ComboboxItem jogador = comboBoxEditarJogador.getValue();
            ComboboxItem competicao = comboBoxEditarCompeticao.getValue();
            ComboboxItem competidor = comboBoxEditarCompetidor.getValue();
            double valor = Double.parseDouble(textFieldEditarValor.getText());
            double odd = sliderEditarOdd.getValue();
            apostaDAO.EditarPorDTO(
                    new ApostaDTO(onEditApostaDTO.getId(), jogador.getValue(), valor, competidor.getValue(),
                            competicao.getValue(), odd));
            populateTableView();
            closeEdit();
        } catch (Exception e) {
            ErrorHelper.ThrowErrorOnAlert(e);
        }
    }

    private void excludeEdit() {
        try {
            apostaDAO.Excluir(onEditApostaDTO.getId());
            populateTableView();
            closeEdit();
        } catch (Exception e) {
            ErrorHelper.ThrowErrorOnAlert(e);
        }
    }

    private void setComboboxesEvents() {

        comboBoxJogador.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                try {
                    observableCompeticao = FXCollections.observableArrayList(competicaoDAO.BuscarTodosOsDTO().stream()
                            .map(x -> new ComboboxItem(x.getNome(), x.getId())).collect(Collectors.toList()));
                    comboBoxCompeticao.setItems(observableCompeticao);
                    comboBoxCompeticao.setDisable(false);
                } catch (SQLException e) {
                    ErrorHelper.ThrowErrorOnAlert(e);
                } finally {
                    comboBoxCompetidor.setDisable(true);
                }
            } else {
                comboBoxJogador.setValue(null);
                comboBoxCompeticao.setValue(null);
                comboBoxCompeticao.setDisable(true);
                comboBoxCompetidor.setValue(null);
                comboBoxCompetidor.setDisable(true);
            }
        });

        comboBoxCompeticao.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                try {
                    if (comboBoxCompeticao.getValue() != null) {
                        observableCompetidor = FXCollections.observableArrayList(
                                competidorDAO
                                        .BuscarCompetidoresParticipandoSemAposta(
                                                comboBoxCompeticao.getValue().getValue(),
                                                comboBoxJogador.getValue().getValue())
                                        .stream()
                                        .map(x -> new ComboboxItem(String.format("%s %s", x.AtletaDTO.getNome(),
                                                x.AtletaDTO.getSobrenome()), x.getAtleta_id()))
                                        .collect(Collectors.toList()));
                        comboBoxCompetidor.setItems(observableCompetidor);
                    }
                    comboBoxCompetidor.setDisable(false);
                } catch (SQLException e) {
                    ErrorHelper.ThrowErrorOnAlert(e);
                }
            } else {
                comboBoxCompeticao.setValue(null);
                comboBoxCompeticao.setDisable(true);
                comboBoxCompetidor.setValue(null);
                comboBoxCompetidor.setDisable(true);
            }
        });
        comboBoxCompetidor.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                textFieldValor.setDisable(false);
                sliderOdd.setDisable(false);
            } else {
                comboBoxEditarCompetidor.setValue(null);
                comboBoxEditarCompetidor.setDisable(true);
            }
        });

        comboBoxEditarJogador.getSelectionModel().selectedItemProperty()
                .addListener((observable, oldValue, newValue) -> {
                    if (newValue != null) {
                        try {
                            observableCompeticao = FXCollections.observableArrayList(competicaoDAO.BuscarTodosOsDTO()
                                    .stream()
                                    .map(x -> new ComboboxItem(x.getNome(), x.getId())).collect(Collectors.toList()));
                            comboBoxEditarCompeticao.setItems(observableCompeticao);
                            comboBoxEditarCompetidor.setDisable(false);
                        } catch (SQLException e) {
                            ErrorHelper.ThrowErrorOnAlert(e);
                        } finally {
                            comboBoxEditarCompetidor.setDisable(true);
                        }
                    } else {
                        comboBoxEditarJogador.valueProperty().set(null);
                        comboBoxEditarCompeticao.valueProperty().set(null);
                        comboBoxEditarCompeticao.setDisable(true);
                        comboBoxEditarCompetidor.valueProperty().set(null);
                        comboBoxEditarCompetidor.setDisable(true);
                    }
                });

        comboBoxEditarCompeticao.getSelectionModel().selectedItemProperty()
                .addListener((observable, oldValue, newValue) -> {
                    if (newValue != null) {
                        try {
                            if (comboBoxEditarCompeticao.getValue() != null) {
                                observableCompetidor = FXCollections.observableArrayList(
                                        competidorDAO
                                                .BuscarCompetidoresParticipandoSemAposta(
                                                        comboBoxEditarCompeticao.getValue().getValue(),
                                                        comboBoxEditarJogador.getValue().getValue())
                                                .stream()
                                                .map(x -> new ComboboxItem(String.format("%s %s", x.AtletaDTO.getNome(),
                                                        x.AtletaDTO.getSobrenome()), x.getAtleta_id()))
                                                .collect(Collectors.toList()));
                                comboBoxEditarCompetidor.setItems(observableCompetidor);
                            }
                            comboBoxEditarCompetidor.setDisable(false);
                        } catch (SQLException e) {
                            ErrorHelper.ThrowErrorOnAlert(e);
                        }
                    } else {
                        comboBoxEditarCompeticao.valueProperty().set(null);
                        comboBoxEditarCompeticao.setDisable(true);
                        comboBoxEditarCompetidor.valueProperty().set(null);
                        comboBoxEditarCompetidor.setDisable(true);
                    }
                });
        comboBoxEditarCompetidor.getSelectionModel().selectedItemProperty()
                .addListener((observable, oldValue, newValue) -> {
                    if (newValue != null) {
                        textFieldValor.setDisable(false);
                        sliderOdd.setDisable(false);
                    } else {
                        comboBoxEditarCompetidor.valueProperty().set(null);
                        comboBoxEditarCompetidor.setDisable(true);
                    }
                });

    }

    private void setEvents() {
        setMenuEvents();
        btnCriarAposta.setOnAction((ActionEvent event) -> {
            try {
                openCreate();
            } catch (SQLException e) {
                ErrorHelper.ThrowErrorOnAlert(e);
            }
        });
        btnFechar.setOnAction((ActionEvent event) -> {
            closeCreate();
        });
        btnSalvar.setOnAction((ActionEvent event) -> {
            try {
                saveCreate();
            } catch (SQLException e) {
                ErrorHelper.ThrowErrorOnAlert(e);
            }
        });
        btnEditarFechar.setOnAction((ActionEvent event) -> {
            closeEdit();
        });
        btnEditarSalvar.setOnAction((ActionEvent event) -> {
            try {
                saveEdit();
            } catch (Exception e) {
                ErrorHelper.ThrowErrorOnAlert(e);
            }
        });
        btnEditarExcluir.setOnAction((ActionEvent event) -> {
            try {
                excludeEdit();
            } catch (Exception e) {
                ErrorHelper.ThrowErrorOnAlert(e);
            }
        });
        tblViewApostas.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                onEditApostaDTO = newValue;
                try {
                    openEdit();
                } catch (SQLException e) {
                    ErrorHelper.ThrowErrorOnAlert(e);
                }
            }
        });

        setComboboxesEvents();

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
