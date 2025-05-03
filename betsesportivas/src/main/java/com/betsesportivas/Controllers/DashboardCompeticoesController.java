package com.betsesportivas.Controllers;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;
import java.util.ResourceBundle;

import com.betsesportivas.App;
import com.betsesportivas.DAO.AtletaDAO;
import com.betsesportivas.DAO.CategoriaDAO;
import com.betsesportivas.DAO.CompeticaoDAO;
import com.betsesportivas.DAO.CompetidorDAO;
import com.betsesportivas.DAO.IAtletaDAO;
import com.betsesportivas.DAO.IBaseDAO;
import com.betsesportivas.DAO.ICompeticaoDAO;
import com.betsesportivas.DAO.ICompetidorDAO;
import com.betsesportivas.DTO.AtletaDTO;
import com.betsesportivas.DTO.CategoriaDTO;
import com.betsesportivas.DTO.CompeticaoDTO;
import com.betsesportivas.DTO.CompetidorDTO;
import com.betsesportivas.Database.Db;
import com.betsesportivas.Domain.Atleta;
import com.betsesportivas.Domain.Categoria;
import com.betsesportivas.Domain.Competicao;
import com.betsesportivas.Domain.Competidor;
import com.betsesportivas.Helpers.DateConverterHelper;
import com.betsesportivas.Helpers.FieldsHelper;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.ListView;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.Pane;

public class DashboardCompeticoesController implements Initializable {

    private final Db Database = new Db();
    private final IBaseDAO<Categoria, CategoriaDTO> categoriaDAO = new CategoriaDAO();
    private final ICompeticaoDAO<Competicao, CompeticaoDTO> competicaoDAO;
    private final IAtletaDAO<Atleta, AtletaDTO> atletaDAO = new AtletaDAO();
    private final ICompetidorDAO<Competidor, CompetidorDTO> competidorDAO = new CompetidorDAO();

    public DashboardCompeticoesController() throws SQLException {
        categoriaDAO.Connect(Database.Connect());
        competicaoDAO = new CompeticaoDAO(categoriaDAO);
        competicaoDAO.Connect(Database.Connect());
        atletaDAO.Connect(Database.Connect());
        competidorDAO.Connect(Database.Connect());
    }

    // #region tblView
    @FXML
    private TableView<CompeticaoDTO> tblView_competicoes;
    @FXML
    private TableColumn<CompeticaoDTO, String> tblViewColumn_competicoes_nome;
    @FXML
    private TableColumn<CompeticaoDTO, String> tblViewColumn_competicoes_categoria;
    @FXML
    private TableColumn<CompeticaoDTO, LocalDate> tblViewColumn_competicoes_dataInicioApostas;
    @FXML
    private TableColumn<CompeticaoDTO, LocalDate> tblViewColumn_competicoes_dataCadastro;
    @FXML
    private TableColumn<CompeticaoDTO, LocalDate> tblViewColumn_competicoes_dataTerminoApostas;
    @FXML
    private TableColumn<CompeticaoDTO, Integer> tblViewColumn_competicoes_qtdApostas;
    @FXML
    private TableColumn<CompeticaoDTO, Double> tblViewColumn_competicoes_valorEmJogo;
    @FXML
    private TableColumn<CompeticaoDTO, String> tblViewColumn_competicoes_status;

    // #endregion

    @FXML
    private ObservableList<CompeticaoDTO> observableCompeticaoDTO;

    @FXML
    private List<CompetidorDTO> atletasDisponiveisCriacao;
    @FXML
    private ObservableList<CompetidorDTO> atletasDisponiveisCriacaoObservable;
    @FXML
    private List<CompetidorDTO> atletasParticipandoCriacao;
    @FXML
    private ObservableList<CompetidorDTO> atletasParticipandoCriacaoObservable;

    @FXML
    private List<CompetidorDTO> atletasDisponiveis;
    @FXML
    private ObservableList<CompetidorDTO> atletasDisponiveisObservable;
    @FXML
    private ObservableList<CompetidorDTO> atletasParticipandoObservable;

    // #region editCompeticao

    @FXML
    private ObservableList<CategoriaDTO> observable_CategoriaDTOs;

    @FXML
    private CompeticaoDTO onEditCompeticaoDTO;
    @FXML
    private Pane pane_editar;
    @FXML
    private Button btn_editar_fechar;
    @FXML
    private TextField textField_editar_nome;
    @FXML
    private DatePicker datePicker_editar_inicioApostas;
    @FXML
    private TextField textField_editar_inicioApostas;
    @FXML
    private DatePicker datePicker_editar_dataOcorrencia;
    @FXML
    private TextField textField_editar_dataOcorrencia;
    @FXML
    private DatePicker datePicker_editar_terminoApostas;
    @FXML
    private TextField textField_editar_terminoApostas;
    @FXML
    private ComboBox<CategoriaDTO> comboBox_editar_categoria;
    @FXML
    private Button btn_editar_competidores;
    @FXML
    private Pane pane_editar_competidores;
    @FXML
    private ListView<CompetidorDTO> pane_editar_competidores_disponiveis;
    @FXML
    private ListView<CompetidorDTO> pane_editar_competidores_participando;
    @FXML
    private Button btn_editar_competidores_concluir;
    @FXML
    private Button btn_editar_salvar;
    @FXML
    private Button btn_editar_excluir;

    // #endregion

    // #region criarCompeticao
    @FXML
    private Pane pane_criar;
    @FXML
    private TextField textField_criar_nome;
    @FXML
    private DatePicker datePicker_criar_inicioApostas;
    @FXML
    private TextField textField_criar_inicioApostas;
    @FXML
    private DatePicker datePicker_criar_dataOcorrencia;
    @FXML
    private TextField textField_criar_dataOcorrencia;
    @FXML
    private DatePicker datePicker_criar_terminoApostas;
    @FXML
    private TextField textField_criar_terminoApostas;
    @FXML
    private ComboBox<CategoriaDTO> comboBox_criar_categoria;
    @FXML
    private Pane pane_criar_competidores;
    @FXML
    private ListView<CompetidorDTO> pane_criar_competidores_participando;
    @FXML
    private ListView<CompetidorDTO> pane_criar_competidores_disponiveis;
    @FXML
    private Button btn_criar_fechar;
    @FXML
    private CompeticaoDTO onCreateCompeticaoDTO;
    @FXML
    private Button btn_criarEvento;

    // #endregion

    @FXML
    private Button btn_refresh;

    // #region menus
    @FXML
    private MenuItem menu_competicoes_dashboard;
    @FXML
    private MenuItem menu_jogadores_dashboard;
    @FXML
    private MenuItem menu_apostas_dashboard;
    @FXML
    private MenuItem menu_atletas_dashboard;
    @FXML
    private MenuItem menu_atletas_relatorio;
    @FXML
    private MenuItem menu_categorias_dashboard;
    // #endregion

    // ================================================================================
    // TableView
    // ================================================================================
    private void populateTableViewData() throws SQLException {
        List<CompeticaoDTO> listCompeticaoDTO = competicaoDAO.BuscarTodosOsDTO();
        observableCompeticaoDTO = FXCollections.observableArrayList(listCompeticaoDTO);
        tblView_competicoes.setItems(observableCompeticaoDTO);
    }

    private void initializeTableView() throws SQLException {
        tblViewColumn_competicoes_nome.setCellValueFactory(new PropertyValueFactory<>("nome"));
        tblViewColumn_competicoes_categoria.setCellValueFactory(new PropertyValueFactory<>("categoria"));
        tblViewColumn_competicoes_categoria.setCellValueFactory(c -> c.getValue().nameProperty());
        tblViewColumn_competicoes_dataCadastro.setCellValueFactory(new PropertyValueFactory<>("data_cadastro"));
        tblViewColumn_competicoes_dataInicioApostas
                .setCellValueFactory(new PropertyValueFactory<>("data_abertura_apostas"));
        tblViewColumn_competicoes_dataTerminoApostas
                .setCellValueFactory(new PropertyValueFactory<>("data_fechamento_apostas"));
        tblViewColumn_competicoes_qtdApostas.setCellValueFactory(new PropertyValueFactory<>("quantidadeDeApostas"));
        tblViewColumn_competicoes_valorEmJogo.setCellValueFactory(new PropertyValueFactory<>("valorEmJogo"));
        tblViewColumn_competicoes_status.setCellValueFactory(new PropertyValueFactory<>("status"));

        populateTableViewData();
    }

    // #region initialize
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        try {
            initializeTableView();
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        FieldsHelper.setHourFieldProperties(Arrays.asList(textField_editar_inicioApostas,
                textField_editar_terminoApostas, textField_editar_dataOcorrencia, textField_criar_inicioApostas,
                textField_criar_terminoApostas, textField_criar_dataOcorrencia));
        FieldsHelper.<CategoriaDTO>setComboBoxProperties(comboBox_editar_categoria);
        FieldsHelper.<CategoriaDTO>setComboBoxProperties(comboBox_criar_categoria);
        setEvents();
    }

    // #endregion

    // #region create
    @FXML
    private void openCreatePane() throws SQLException {
        pane_criar.setVisible(true);
        List<CategoriaDTO> catDTO = categoriaDAO.BuscarTodosOsDTO();
        observable_CategoriaDTOs = FXCollections.observableArrayList(catDTO);
        comboBox_criar_categoria.setItems(observable_CategoriaDTOs);
        popularCompetidoresCriacaoCompeticao();
    }

    @FXML
    private void closeCreatePane() {
        tblView_competicoes.getSelectionModel().select(null);
        pane_criar.setVisible(false);
        clearCreation();
        onCreateCompeticaoDTO = new CompeticaoDTO();
    }

    @FXML
    private void saveCreation() throws SQLException {
        onCreateCompeticaoDTO.setNome(textField_editar_nome.getText());
        // competicaoDAO.Criar(onCreateCompeticaoDTO);
        clearCreation();
        closeCreatePane();
        populateTableViewData();
    }

    private void clearCreation() {
        textField_criar_nome.setText("");
        textField_criar_inicioApostas.setText("");
        datePicker_criar_inicioApostas.setValue(null);
        textField_criar_dataOcorrencia.setText("");
        datePicker_criar_dataOcorrencia.setValue(null);
        textField_criar_terminoApostas.setText("");
        datePicker_criar_terminoApostas.setValue(null);
        comboBox_criar_categoria.setValue(null);
        pane_criar_competidores_participando.setItems(null);
        pane_criar_competidores_disponiveis.setItems(null);
    }

    @FXML
    private void abrirSelecaoCompetidoresCriar() {
        pane_criar_competidores.setVisible(true);
    }

    @FXML
    private void salvarAdicaoCompetidoresCriacao() {
        pane_criar_competidores.setVisible(false);
    }

    private void popularCompetidoresCriacaoCompeticao() throws SQLException {
        atletasDisponiveisCriacao = competidorDAO.BuscarCompetidoresDisponiveisDTO(onEditCompeticaoDTO.getId());
        atletasDisponiveisCriacaoObservable = FXCollections.observableArrayList(atletasDisponiveisCriacao);
        atletasParticipandoCriacao = new LinkedList<>();
        atletasParticipandoCriacaoObservable = FXCollections.observableArrayList(atletasParticipandoCriacao);
        pane_criar_competidores_disponiveis.setItems(atletasDisponiveisCriacaoObservable);
        pane_criar_competidores_participando.setItems(atletasParticipandoCriacaoObservable);
    }

    @FXML
    private void adicionarCompetidorCompeticaoCriacao() {
        pane_criar_competidores_disponiveis.getItems();
        CompetidorDTO atletaSelecionado = pane_criar_competidores_disponiveis.getSelectionModel().getSelectedItem();
        if (atletaSelecionado == null)
            return;
        atletasDisponiveisCriacaoObservable.remove(atletaSelecionado);
        atletasParticipandoCriacaoObservable.add(atletaSelecionado);
        ordenarListasCompetidoresCriacao();
    }

    @FXML
    private void removerCompetidorCompeticaoCriacao() {
        pane_criar_competidores_participando.getItems();
        CompetidorDTO atletaSelecionado = pane_criar_competidores_participando.getSelectionModel().getSelectedItem();
        if (atletaSelecionado == null)
            return;
        atletasParticipandoCriacaoObservable.remove(atletaSelecionado);
        atletasDisponiveisCriacaoObservable.add(atletaSelecionado);
        ordenarListasCompetidoresCriacao();
    }

    private void ordenarListasCompetidoresCriacao() {
        atletasParticipandoCriacaoObservable.sort(Comparator.comparing(a -> a.AtletaDTO.getNome()));
        atletasDisponiveisCriacaoObservable.sort(Comparator.comparing(a -> a.AtletaDTO.getNome()));
    }

    @FXML
    private void salvarCriacaoCompeticao() {
        String nomeCompeticao = textField_criar_nome.getText();
        CategoriaDTO categoriaSelecionada = comboBox_criar_categoria.getSelectionModel().getSelectedItem();

        String dataAberturaApostasString = datePicker_criar_inicioApostas.getValue().toString() + " "
                + textField_criar_inicioApostas.getText();
        LocalDateTime dataAberturaApostas = DateConverterHelper.ConvertStringToLocalDateTime(dataAberturaApostasString);

        String dataOcorrenciaApostasString = datePicker_criar_dataOcorrencia.getValue().toString() + " "
                + textField_criar_dataOcorrencia.getText();
        LocalDateTime dataOcorrenciaApostas = DateConverterHelper
                .ConvertStringToLocalDateTime(dataOcorrenciaApostasString);

        String dataTerminoApostasString = datePicker_criar_terminoApostas.getValue().toString() + " "
                + textField_criar_terminoApostas.getText();
        LocalDateTime dataTeminoApostas = DateConverterHelper.ConvertStringToLocalDateTime(dataTerminoApostasString);

        List<CompetidorDTO> atletasSelecionados = atletasParticipandoCriacaoObservable;

        CompeticaoDTO competicao = new CompeticaoDTO(0, nomeCompeticao, categoriaSelecionada, LocalDateTime.now(),
                dataAberturaApostas, dataTeminoApostas, dataOcorrenciaApostas, 0, atletasSelecionados);

        // falta implemetnar o método de criar no dao
        // competicaoDAO.CriarPorDTO();

        pane_criar.setVisible(false);
    }

    // #endregion

    // #region edit
    @FXML
    private void openEditPane() throws SQLException {
        pane_editar.setVisible(true);
        textField_editar_nome.setText(onEditCompeticaoDTO.getNome());
        textField_editar_inicioApostas.setText(
                DateConverterHelper.ConvertTimestampsToFormated(onEditCompeticaoDTO.getData_abertura_apostas()));
        datePicker_editar_inicioApostas.setValue(
                DateConverterHelper.ConvertTimestampsToLocalDate(onEditCompeticaoDTO.getData_abertura_apostas()));
        textField_editar_dataOcorrencia.setText(
                DateConverterHelper.ConvertTimestampsToFormated(onEditCompeticaoDTO.getData_ocorrencia_evento()));
        datePicker_editar_dataOcorrencia.setValue(
                DateConverterHelper.ConvertTimestampsToLocalDate(onEditCompeticaoDTO.getData_ocorrencia_evento()));
        textField_editar_terminoApostas.setText(
                DateConverterHelper.ConvertTimestampsToFormated(onEditCompeticaoDTO.getData_fechamento_apostas()));
        datePicker_editar_terminoApostas.setValue(
                DateConverterHelper.ConvertTimestampsToLocalDate(onEditCompeticaoDTO.getData_fechamento_apostas()));
        datePicker_editar_inicioApostas.setValue(
                DateConverterHelper.ConvertTimestampsToLocalDate(onEditCompeticaoDTO.getData_abertura_apostas()));

        List<CategoriaDTO> catDTO = categoriaDAO.BuscarTodosOsDTO();
        observable_CategoriaDTOs = FXCollections.observableArrayList(catDTO);
        comboBox_editar_categoria.setItems(observable_CategoriaDTOs);
        comboBox_editar_categoria.setValue(onEditCompeticaoDTO.getCategoria());

    }

    @FXML
    private void closeEditPane() {
        tblView_competicoes.getSelectionModel().select(null);
        pane_editar.setVisible(false);
        pane_editar_competidores.setVisible(false);
        clearEdition();
        onEditCompeticaoDTO = new CompeticaoDTO();
    }

    @FXML
    private void openEditCompetidoresPane() throws SQLException {
        atletasDisponiveis = competidorDAO.BuscarCompetidoresDisponiveisDTO(onEditCompeticaoDTO.Id);
        atletasDisponiveisObservable = FXCollections.observableArrayList(atletasDisponiveis);
        pane_editar_competidores_disponiveis.setItems(atletasDisponiveisObservable);

        atletasParticipandoObservable = FXCollections.observableArrayList(onEditCompeticaoDTO.Competidores);
        pane_editar_competidores_participando.setItems(atletasParticipandoObservable);

        pane_editar_competidores.setVisible(true);
    }

    @FXML
    private void addCompetidorEdit() {
        CompetidorDTO atleta = pane_editar_competidores_disponiveis.getSelectionModel().getSelectedItem();
        if (atleta != null) {
            atletasDisponiveisObservable.remove(atleta);
            atletasParticipandoObservable.add(atleta);
        }
    }

    @FXML
    private void removeCompetidorEdit() {
        CompetidorDTO atleta = pane_editar_competidores_participando.getSelectionModel().getSelectedItem();
        if (atleta != null) {
            atletasParticipandoObservable.remove(atleta);
            atletasDisponiveisObservable.add(atleta);
        }
    }

    @FXML
    private void closeEditCompetidoresPane() {
        pane_editar_competidores.setVisible(false);
    }

    @FXML
    private void saveEdition() throws SQLException {
        onEditCompeticaoDTO.setNome(textField_editar_nome.getText());
        onEditCompeticaoDTO.setData_abertura_apostas(DateConverterHelper.ConvertStringToLocalDateTime(
                (datePicker_editar_inicioApostas.getValue().toString() + " " + textField_editar_inicioApostas
                        .getText())));
        onEditCompeticaoDTO.setData_fechamento_apostas(
                DateConverterHelper.ConvertStringToLocalDateTime((datePicker_editar_terminoApostas.getValue().toString()
                        + " " + textField_editar_terminoApostas.getText())));
        onEditCompeticaoDTO.setData_ocorrencia_evento(
                DateConverterHelper.ConvertStringToLocalDateTime((datePicker_editar_dataOcorrencia.getValue().toString()
                        + " " + textField_editar_dataOcorrencia.getText())));
        onEditCompeticaoDTO.setCategoria(comboBox_editar_categoria.getValue());
        onEditCompeticaoDTO.setCompetidores(pane_editar_competidores_participando.getItems());
        competicaoDAO.EditarPorDTO(onEditCompeticaoDTO);
        clearEdition();
        closeEditPane();
        populateTableViewData();
    }

    @FXML
    private void excludeEdition() throws SQLException {
        try {
            competicaoDAO.Excluir(onEditCompeticaoDTO.getId());
        } catch (Exception e) {
            Alert alert = new Alert(Alert.AlertType.ERROR, e.getMessage());
            alert.show();
        }
        closeEditPane();
    }

    private void clearEdition() {
        textField_editar_nome.setText("");
        textField_editar_inicioApostas.setText("");
        datePicker_editar_inicioApostas.setValue(null);
        textField_editar_dataOcorrencia.setText("");
        datePicker_editar_dataOcorrencia.setValue(null);
        textField_editar_terminoApostas.setText("");
        datePicker_editar_terminoApostas.setValue(null);
        comboBox_editar_categoria.setValue(null);
        clearEditionCompetidor();
    }

    private void clearEditionCompetidor() {
        pane_editar_competidores_participando.setItems(null);
        pane_editar_competidores_disponiveis.setItems(null);
    }

    // #endregion

    // #region events
    @FXML
    private void setEvents() {
        btn_refresh.setOnAction((ActionEvent event) -> {
            try {
                populateTableViewData();
            } catch (SQLException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        });

        btn_criarEvento.setOnAction((ActionEvent event) -> {
            try {

                onCreateCompeticaoDTO = new CompeticaoDTO();
                openCreatePane();
            } catch (SQLException ex) {
                System.out.println(ex);
            }
        });

        btn_criar_fechar.setOnAction((ActionEvent event) -> {
            closeCreatePane();
        });

        tblView_competicoes.getSelectionModel().selectedItemProperty().addListener((obs, oldValue, newValue) -> {
            if (newValue != null) {
                onEditCompeticaoDTO = newValue;
                try {
                    openEditPane();
                } catch (SQLException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }
        });

        btn_editar_competidores.setOnAction((ActionEvent event) -> {
            try {
                openEditCompetidoresPane();
            } catch (SQLException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        });
        setMenuEvents();
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
            System.out.println(".()");
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
    }
    // #endregion
}
