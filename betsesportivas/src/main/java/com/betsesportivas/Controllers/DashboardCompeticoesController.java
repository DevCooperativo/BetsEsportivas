package com.betsesportivas.Controllers;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;
import java.util.ResourceBundle;

import com.betsesportivas.App;
import com.betsesportivas.DAO.AtletaDAO;
import com.betsesportivas.DAO.CategoriaDAO;
import com.betsesportivas.DAO.CompeticaoDAO;
import com.betsesportivas.DAO.IAtletaDAO;
import com.betsesportivas.DAO.IBaseDAO;
import com.betsesportivas.DAO.ICompeticaoDAO;
import com.betsesportivas.DTO.AtletaDTO;
import com.betsesportivas.DTO.CategoriaDTO;
import com.betsesportivas.DTO.CompeticaoDTO;
import com.betsesportivas.Database.Db;
import com.betsesportivas.Domain.Atleta;
import com.betsesportivas.Domain.Categoria;
import com.betsesportivas.Domain.Competicao;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.ListCell;
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

    public DashboardCompeticoesController() throws SQLException {
        categoriaDAO.Connect(Database.Connect());
        competicaoDAO = new CompeticaoDAO(categoriaDAO);
        competicaoDAO.Connect(Database.Connect());
        atletaDAO.Connect(Database.Connect());
    }

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
    @FXML
    private ObservableList<CompeticaoDTO> observableCompeticaoDTO;

    @FXML
    private List<AtletaDTO> atletasDisponiveis;
    @FXML
    private ObservableList<AtletaDTO> atletasDisponiveisObservable;
    @FXML
    private ObservableList<AtletaDTO> atletasParticipandoObservable;

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
    private ComboBox<CategoriaDTO> comboBox_editar_categoria;
    @FXML
    private Button btn_editar_competidores;
    @FXML
    private Pane pane_editar_competidores;
    @FXML
    private ListView<AtletaDTO> pane_editar_competidores_disponiveis;
    @FXML
    private ListView<AtletaDTO> pane_editar_competidores_participando;
    @FXML
    private Button pane_editar_competidores_cancelar;
    @FXML
    private Button btn_editar_competidores_salvar;

    @FXML
    private Pane pane_criar;
    @FXML
    private Button btn_criar_fechar;
    @FXML
    private CompeticaoDTO onCreateCompeticaoDTO;

    @FXML
    private Button btn_criarEvento;
    @FXML
    private Button btn_refresh;

    @FXML
    private MenuItem menu_competicoes_dashboard;

    @FXML
    private MenuItem menu_clientes_dashboard;

    @FXML
    private MenuItem menu_apostas_dashboard;

    @FXML
    private MenuItem menu_categorias_dashboard;

    
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
        tblViewColumn_competicoes_categoria.setCellValueFactory(c->c.getValue().nameProperty());
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

    // ================================================================================
    // Initialize
    // ================================================================================
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        try {
            initializeTableView();
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        setEvents();
    }

    // ================================================================================
    // Create
    // ================================================================================
    @FXML
    private void openCreatePane() {
        pane_criar.setVisible(true);
        textField_editar_nome.setText(onCreateCompeticaoDTO.getNome());
    }

    @FXML
    private void closeCreatePane() {
        tblView_competicoes.getSelectionModel().select(null);
        pane_criar.setVisible(false);
        textField_editar_nome.setText("");
        onCreateCompeticaoDTO = new CompeticaoDTO();
    }

    @FXML
    private void saveCreation() throws SQLException {
    onCreateCompeticaoDTO.setNome(textField_editar_nome.getText());
    // competicaoDAO.Criar(onCreateCompeticaoDTO);
    closeCreatePane();
    populateTableViewData();
    }
    @FXML
    private void excludeCreation() throws SQLException{
    try {
    competicaoDAO.Excluir(onCreateCompeticaoDTO.getId());
    } catch (Exception e) {
    Alert alert = new Alert(Alert.AlertType.ERROR, e.getMessage());
    alert.show();
    }
    closeCreatePane();
    }

    // ================================================================================
    // Edit
    // ================================================================================
    @FXML
    private void openEditPane() {
        pane_editar.setVisible(true);
        textField_editar_nome.setText(onEditCompeticaoDTO.getNome());
        datePicker_editar_inicioApostas.setValue(onEditCompeticaoDTO.getData_abertura_apostas());
        comboBox_editar_categoria.setItems(FXCollections.observableArrayList(onEditCompeticaoDTO.Categoria));
        comboBox_editar_categoria.setCellFactory(c-> new ListCell<CategoriaDTO>(){
            protected void updateItem(CategoriaDTO item, boolean empty) {
                super.updateItem(item, empty);
                setText(empty || item == null ? null : item.getNome());
            }
        });
        comboBox_editar_categoria.setButtonCell(new ListCell<CategoriaDTO>() {
            @Override
            protected void updateItem(CategoriaDTO item, boolean empty) {
                super.updateItem(item, empty);
                setText(empty || item == null ? null : item.getNome());
            }
        });
    }

    @FXML
    private void closeEditPane() {
        tblView_competicoes.getSelectionModel().select(null);
        pane_editar.setVisible(false);
        textField_editar_nome.setText("");
        onEditCompeticaoDTO = new CompeticaoDTO();
    }

    @FXML
    private void openEditCompetidoresPane() throws SQLException {
        atletasDisponiveis = atletaDAO.BuscarAtletasDisponiveisDTO(onEditCompeticaoDTO.Id);
        atletasDisponiveisObservable = FXCollections.observableArrayList(atletasDisponiveis);
        pane_editar_competidores_disponiveis.setItems(atletasDisponiveisObservable);

        atletasParticipandoObservable = FXCollections.observableArrayList(onEditCompeticaoDTO.Competidores);
        pane_editar_competidores_participando.setItems(atletasParticipandoObservable);

        pane_editar_competidores.setVisible(true);
    }

    @FXML
    private void closeEditCompetidoresPane() {
        pane_editar_competidores.setVisible(false);
    }

    @FXML
    private void saveEdition() throws SQLException {
        onEditCompeticaoDTO.setNome(textField_editar_nome.getText());
        competicaoDAO.EditarPorDTO(onEditCompeticaoDTO);
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

    // ================================================================================
    // Events
    // ================================================================================
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
            onCreateCompeticaoDTO = new CompeticaoDTO();
            openCreatePane();
        });

        btn_criar_fechar.setOnAction((ActionEvent event) -> {
            closeCreatePane();
        });

        tblView_competicoes.getSelectionModel().selectedItemProperty().addListener((obs, oldValue, newValue) -> {
            if (newValue != null) {
                onEditCompeticaoDTO = newValue;
                openEditPane();
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
        pane_editar_competidores_cancelar.setOnAction((ActionEvent event) -> {
            closeEditCompetidoresPane();
        });
        btn_editar_fechar.setOnAction((ActionEvent event) -> {
            closeEditPane();
        });
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

        menu_clientes_dashboard.setOnAction((ActionEvent event) -> {
            try {
                App.setNewScene("DashboardClientes");
            } catch (IOException ex) {
                ex.getStackTrace();
            }
        });
    }
}
