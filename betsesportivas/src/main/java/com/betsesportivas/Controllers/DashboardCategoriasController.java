package com.betsesportivas.Controllers;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.List;
import java.util.ResourceBundle;

import com.betsesportivas.App;
import com.betsesportivas.DAO.CategoriaDAO;
import com.betsesportivas.DTO.CategoriaDTO;
import com.betsesportivas.Database.Db;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.Pane;

public class DashboardCategoriasController implements Initializable {

    private final Db Database = new Db();
    private final CategoriaDAO categoriaDAO = new CategoriaDAO();

    public DashboardCategoriasController() throws SQLException {
        categoriaDAO.Connect(Database.Connect());
    }

    @FXML
    private TableView<CategoriaDTO> tblView_categorias;
    @FXML
    private TableColumn<CategoriaDTO, String> tblViewColumn_categorias_nome;
    @FXML
    private TableColumn<CategoriaDTO, Integer> tblViewColumn_categorias_vezesUtilizadas;
    @FXML
    private ObservableList<CategoriaDTO> obvservableCategoriaDTO;

    @FXML
    private Pane pane_criar;
    @FXML
    private TextField textField_pane_criar_nome;
    @FXML
    private Button btn_pane_criar_criar;
    @FXML
    private Button btn_pane_criar_fechar;
    @FXML
    private CategoriaDTO onEditCategoriaDTO;

    @FXML
    private Pane pane_editar;
    @FXML
    private TextField textField_pane_editar_nome;
    @FXML
    private Button btn_pane_editar_salvar;
    @FXML
    private Button btn_pane_editar_fechar;
    @FXML
    private Button btn_pane_editar_excluir;

    @FXML
    private Button btn_criarCategoria;

    @FXML
    private MenuItem menu_categorias_dashboard;
    @FXML
    private MenuItem menu_competicoes_dashboard;
    @FXML
    private MenuItem menu_apostas_dashboard;
    @FXML
    private MenuItem menu_clientes_dashboard;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        try {
            initializeTableView();
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.getStackTrace();
        }
        setEvents();
    }

    @FXML
    private void initializeTableView() throws SQLException {
        tblViewColumn_categorias_nome.setCellValueFactory(new PropertyValueFactory<>("Nome"));
        tblViewColumn_categorias_vezesUtilizadas.setCellValueFactory(new PropertyValueFactory<>("VezesUtilizada"));

        populateTableViewData();
    }

    private void populateTableViewData() throws SQLException {

        List<CategoriaDTO> categoriaDTO = categoriaDAO.BuscarTodosOsDTO();
        obvservableCategoriaDTO = FXCollections.observableArrayList(categoriaDTO);
        tblView_categorias.setItems(obvservableCategoriaDTO);

    }

    @FXML
    private void openEditPane() {
        pane_editar.setVisible(true);
        textField_pane_editar_nome.setText(onEditCategoriaDTO.getNome());
    }

    @FXML
    private void closeEditPane() {
        tblView_categorias.getSelectionModel().select(null);
        pane_editar.setVisible(false);
        textField_pane_editar_nome.setText("");
        onEditCategoriaDTO = new CategoriaDTO();
    }

    @FXML
    private void saveEdition() throws SQLException {
        onEditCategoriaDTO.setNome(textField_pane_editar_nome.getText());
        categoriaDAO.EditarPorDTO(onEditCategoriaDTO);
        closeEditPane();
        populateTableViewData();
    }

    @FXML
    private void excludeEdition() throws SQLException {
        try {
            categoriaDAO.Excluir(onEditCategoriaDTO.getId());
        } catch (Exception e) {
            Alert alert = new Alert(Alert.AlertType.ERROR, e.getMessage());
            alert.show();
        }
        closeEditPane();
    }

    @FXML
    private void setEvents() {

        btn_criarCategoria.setOnAction((ActionEvent event) -> {
            pane_criar.setVisible(true);
        });

        btn_pane_criar_fechar.setOnAction((ActionEvent event) -> {
            pane_criar.setVisible(false);
        });

        btn_pane_editar_fechar.setOnAction((ActionEvent event) -> {
            closeEditPane();
        });
        btn_pane_editar_salvar.setOnAction((ActionEvent event) -> {
            try {
                saveEdition();
            } catch (SQLException ex) {
            }
        });
        btn_pane_editar_excluir.setOnAction((ActionEvent event) -> {
            try {
                excludeEdition();
            } catch (Exception e) {
            }
        });

        tblView_categorias.getSelectionModel().selectedItemProperty().addListener((obs, oldValue, newValue) -> {
            if (newValue != null) {
                onEditCategoriaDTO = newValue;
                openEditPane();
            }
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