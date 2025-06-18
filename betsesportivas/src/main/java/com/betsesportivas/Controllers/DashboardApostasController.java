package com.betsesportivas.Controllers;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.Map;
import java.util.ResourceBundle;

import com.betsesportivas.App;
import com.betsesportivas.DAO.ApostaDAO;
import com.betsesportivas.DAO.CompeticaoDAO;
import com.betsesportivas.DAO.CompetidorDAO;
import com.betsesportivas.DAO.IApostaDAO;
import com.betsesportivas.DAO.JogadorDAO;
import com.betsesportivas.DTO.ApostaDTO;
import com.betsesportivas.DTO.CompeticaoDTO;
import com.betsesportivas.DTO.CompetidorDTO;
import com.betsesportivas.DTO.JogadorDTO;
import com.betsesportivas.Database.Db;
import com.betsesportivas.Domain.Aposta;
import com.betsesportivas.Helpers.ErrorHelper;
import com.betsesportivas.Helpers.FieldsHelper;
import com.betsesportivas.Helpers.ParserHelper;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
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
    private IApostaDAO<Aposta, ApostaDTO> apostaDAO = new ApostaDAO();
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
    private ObservableList<JogadorDTO> observableJogador;
    @FXML
    private ObservableList<CompetidorDTO> observableCompetidor;
    @FXML
    private ObservableList<CompeticaoDTO> observableCompeticao;

    @FXML
    private TableView<ApostaDTO> tblViewApostas;
    @FXML
    private TableColumn<ApostaDTO, JogadorDTO> tblViewColumnApostasJogador;
    @FXML
    private TableColumn<ApostaDTO, Double> tblViewColumnApostasValor;
    @FXML
    private TableColumn<ApostaDTO, CompeticaoDTO> tblViewColumnApostasCompeticao;
    @FXML
    private TableColumn<ApostaDTO, CompetidorDTO> tblViewColumnApostasCompetidor;

    @FXML
    private Button btnCriarAposta;

    @FXML
    private Pane paneCriar;
    @FXML
    private Text textEditarSaldo;
    @FXML
    private ComboBox<JogadorDTO> comboBoxJogador;
    @FXML
    private ComboBox<CompeticaoDTO> comboBoxCompeticao;
    @FXML
    private TextField textFieldValor;
    @FXML
    private Text textValorMinimo;
    @FXML
    private Text textValorMaximo;
    @FXML
    private ComboBox<CompetidorDTO> comboBoxCompetidor;
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
    private Text textSaldo;
    @FXML
    private ComboBox<JogadorDTO> comboBoxEditarJogador;
    @FXML
    private ComboBox<CompeticaoDTO> comboBoxEditarCompeticao;
    @FXML
    private TextField textFieldEditarValor;
    @FXML
    private Text textEditarValorMinimo;
    @FXML
    private Text textEditarValorMaximo;
    @FXML
    private ComboBox<CompetidorDTO> comboBoxEditarCompetidor;
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
    private BarChart<String, Integer> chartApostasPorCompeticao;
    @FXML
    private CategoryAxis categoryAxis;
    @FXML
    private NumberAxis numberAxis;

    private void setTableViewFields() throws SQLException {
        tblViewColumnApostasCompeticao.setCellValueFactory(new PropertyValueFactory<>("CompeticaoDTO"));
        tblViewColumnApostasCompetidor.setCellValueFactory(new PropertyValueFactory<>("CompetidorDTO"));
        tblViewColumnApostasValor.setCellValueFactory(new PropertyValueFactory<>("valor"));
        tblViewColumnApostasJogador.setCellValueFactory(new PropertyValueFactory<>("JogadorDTO"));
        populateTableView();
    }

    private void setComboBoxes() {
        FieldsHelper.<CompeticaoDTO>setComboBoxProperties(comboBoxCompeticao);
        FieldsHelper.<CompetidorDTO>setComboBoxProperties(comboBoxCompetidor);
        FieldsHelper.<JogadorDTO>setComboBoxProperties(comboBoxJogador);
        FieldsHelper.<CompeticaoDTO>setComboBoxProperties(comboBoxEditarCompeticao);
        FieldsHelper.<CompetidorDTO>setComboBoxProperties(comboBoxEditarCompetidor);
        FieldsHelper.<JogadorDTO>setComboBoxProperties(comboBoxEditarJogador);
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
            textSaldo.setText("");
            textEditarSaldo.setText("");
            setFields();
            setEvents();
            clearCreate();
            populateGrafico();
        } catch (SQLException e) {
            ErrorHelper.ThrowErrorOnAlert(e);
        }
    }

    public void populateGrafico() throws SQLException {
        Map<String, Integer> dados = apostaDAO.RecuperarQuantidadeApostasPorCompeticao();

        XYChart.Series<String, Integer> series = new XYChart.Series<>();

        chartApostasPorCompeticao.setLegendVisible(false);

        for (Map.Entry<String, Integer> dadosItem : dados.entrySet()) {
            String nomeCategoria = dadosItem.getKey();
            Integer quantidade = dadosItem.getValue();
            XYChart.Data<String, Integer> data = new XYChart.Data<>(nomeCategoria, quantidade);
            series.getData().add(data);
        }

        chartApostasPorCompeticao.getData().clear();
        chartApostasPorCompeticao.getData().add(series);
    }

    private void clearCreate() {
        comboBoxJogador.setValue(null);
        comboBoxCompeticao.setValue(null);
        comboBoxCompetidor.setValue(null);
        sliderOdd.setValue(2);
    }

    private void populateComboboxes() throws SQLException {
        observableJogador = FXCollections.observableArrayList(jogadorDAO.BuscarTodosOsDTO());
        comboBoxJogador.setItems(observableJogador);
        observableCompeticao = FXCollections.observableArrayList(competicaoDAO.BuscarTodosOsDTO());
        comboBoxCompeticao.setItems(observableCompeticao);
        if (comboBoxCompeticao.getValue() != null) {
            observableCompetidor = FXCollections.observableArrayList(
                    competidorDAO
                            .BuscarCompetidoresParticipandoSemAposta(comboBoxCompeticao.getValue().getId(),
                                    comboBoxJogador.getValue().getId()));
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
            JogadorDTO jogador = comboBoxJogador.getValue();
            if (jogador == null)
                throw new Exception("Escolha um jogador");

            CompeticaoDTO competicao = comboBoxCompeticao.getValue();
            if (competicao == null)
                throw new Exception("Escolha uma competição");
            if (LocalDateTime.now().compareTo(competicao.getData_fechamento_apostas()) >= 0)
                throw new Exception(String.format(
                        "A aposta não pode ser realizada pois o período de apostas para esta competição foi encerrado às %s",
                        competicao.getData_fechamento_apostas()));

            CompetidorDTO competidor = comboBoxCompetidor.getValue();
            if (competidor == null)
                throw new Exception("Escolha um competidor");

            double valor = Double.parseDouble(textFieldValor.getText());
            if (valor > jogador.getSaldo())
                throw new Exception("O jogador não possui saldo suficiente");

            if (valor < 0
                    || (valor > competicao.getValor_maximo_aposta() || valor < competicao.getValor_minimo_aposta()))
                throw new Exception("Insira um valor dentro dos limites da competição");

            double odd = Double.parseDouble(ParserHelper.parseField(sliderOdd.getValue(), "Odd"));
            if (odd == 0)
                throw new Exception("Insira uma Odd válida");
            apostaDAO.CriarPorDTO(
                    new ApostaDTO(jogador, valor, competidor.getAtleta_id(), competicao.getId(), odd));
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
        comboBoxEditarCompeticao.setDisable(false);
        comboBoxEditarCompetidor.setDisable(false);
        textFieldEditarValor.setDisable(false);
        sliderEditarOdd.setDisable(false);

        observableJogador = FXCollections.observableArrayList((jogadorDAO.BuscarTodosOsDTO()));
        comboBoxEditarJogador.setItems(observableJogador);
        comboBoxEditarJogador.setValue(onEditApostaDTO.getJogadorDTO());

        observableCompeticao = FXCollections.observableArrayList(competicaoDAO.BuscarTodosOsDTO());
        comboBoxEditarCompeticao.setItems(observableCompeticao);
        comboBoxEditarCompeticao
                .setValue(onEditApostaDTO.getCompeticaoDTO());

        observableCompetidor = FXCollections
                .observableArrayList(competidorDAO.BuscarCompetidoresParticipandoSemAposta(
                        comboBoxEditarCompeticao.getValue().getId(), comboBoxEditarJogador.getValue().getId()));
        comboBoxEditarCompetidor.setItems(observableCompetidor);
        comboBoxEditarCompetidor
                .setValue(onEditApostaDTO.getCompetidorDTO());

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
            JogadorDTO jogador = comboBoxEditarJogador.getValue();
            if (jogador == null) {
                throw new Exception("Escolha um jogador");
            }
            CompeticaoDTO competicao = comboBoxEditarCompeticao.getValue();
            if (competicao == null) {
                throw new Exception("Escolha uma competição");
            }
            if (LocalDateTime.now().compareTo(competicao.getData_fechamento_apostas()) >= 0)
                throw new Exception(String.format(
                        "A aposta não pode ser realizada pois o período de apostas para esta competição foi encerrado às %s",
                        competicao.getData_fechamento_apostas()));

            CompetidorDTO competidor = comboBoxEditarCompetidor.getValue();
            if (competidor == null) {
                throw new Exception("Escolha um competidor");
            }

            double valor = Double.parseDouble(textFieldEditarValor.getText());
            if (onEditApostaDTO.getJogadorDTO().getSaldo() + onEditApostaDTO.getValor() - valor < 0) {
                throw new Exception("O jogador não possui saldo suficiente");
            }
            if (valor < 0
                    || (valor > competicao.getValor_maximo_aposta() || valor < competicao.getValor_minimo_aposta())) {
                throw new Exception("Insira um valor dentro dos limites da competição");
            }

            double odd = sliderEditarOdd.getValue();
            apostaDAO.EditarPorDTO(
                    new ApostaDTO(onEditApostaDTO.getId(), jogador.getId(), valor, competidor.getAtleta_id(),
                            competicao.getId(), odd));
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
                    observableCompeticao = FXCollections.observableArrayList(competicaoDAO.BuscarDTOsEmAberto());
                    comboBoxCompeticao.setItems(observableCompeticao);
                    comboBoxCompeticao.setDisable(false);
                    textSaldo.setText(newValue.getSaldo().toString());
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
                                                comboBoxCompeticao.getValue().getId(),
                                                comboBoxJogador.getValue().getId()));
                        comboBoxCompetidor.setItems(observableCompetidor);
                        textValorMinimo.setText(ParserHelper.doubleToString(newValue.getValor_minimo_aposta()));
                        textValorMaximo.setText(ParserHelper.doubleToString(newValue.getValor_maximo_aposta()));
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
                            observableCompeticao = FXCollections
                                    .observableArrayList(competicaoDAO.BuscarDTOsEmAberto());
                            comboBoxEditarCompeticao.setItems(observableCompeticao);
                            comboBoxEditarCompetidor.setDisable(false);
                            textEditarSaldo.setText(newValue.getSaldo().toString());
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
                            observableCompetidor = FXCollections.observableArrayList(
                                    competidorDAO
                                            .BuscarCompetidoresParticipandoSemAposta(
                                                    comboBoxEditarCompeticao.getValue().getId(),
                                                    comboBoxEditarJogador.getValue().getId()));
                            comboBoxEditarCompetidor.setItems(observableCompetidor);
                            comboBoxEditarCompetidor.setDisable(false);
                            textEditarValorMinimo.setText(ParserHelper.parseString(newValue.getValor_minimo_aposta()));
                            textEditarValorMaximo.setText(ParserHelper.parseString(newValue.getValor_maximo_aposta()));
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

}
