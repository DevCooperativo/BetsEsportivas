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
import com.betsesportivas.Helpers.ColorHelper;
import com.betsesportivas.Helpers.ErrorHelper;
import com.betsesportivas.Helpers.ParserHelper;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;

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
    private TextField textField_pane_criar_descricao;
    @FXML
    private TextField textField_pane_criar_max_participantes;
    @FXML
    private ColorPicker colorPicker_pane_criar_max_participantes;
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
    private TextField textField_pane_editar_descricao;
    @FXML
    private TextField textField_pane_editar_max_participantes;
    @FXML
    private ColorPicker colorPicker_pane_editar_max_participantes;
    @FXML
    private CheckBox checkBoxAtivoEditar;
    @FXML
    private Button btn_pane_editar_salvar;
    @FXML
    private Button btn_pane_editar_fechar;
    @FXML
    private Button btn_pane_editar_excluir;

    @FXML
    private Button btn_criarCategoria;

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
        tblView_categorias.setRowFactory(row -> new TableRow<CategoriaDTO>() {
            @Override
            protected void updateItem(CategoriaDTO item, boolean empty) {
                super.updateItem(item, empty);
                if (item == null || empty) {
                    setStyle(""); // Limpa o estilo se a linha estiver vazia
                } else {
                    // Exemplo de lógica de estilo
                    if (!item.getCor().isEmpty()) {
                        setStyle(String.format("-fx-background-color: %s44;", item.getCor()));
                    } else {
                        setStyle("-fx-background-color: white;");
                    }
                }
            }

        });
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
    private void closeCreatePane() {
        textField_pane_criar_nome.setText("");
        textField_pane_criar_descricao.setText("");
        textField_pane_criar_max_participantes.setText("");
        colorPicker_pane_criar_max_participantes.setValue(Color.WHITE);
        pane_criar.setVisible(false);
    }

    @FXML
    private void openEditPane() {
        textField_pane_editar_nome.setText(onEditCategoriaDTO.getNome());
        textField_pane_editar_descricao.setText(onEditCategoriaDTO.getDescricao());
        textField_pane_editar_max_participantes.setText(Integer.toString(onEditCategoriaDTO.getMaxParticipantes()));
        colorPicker_pane_editar_max_participantes.setValue(ColorHelper.fromHexString(onEditCategoriaDTO.getCor()));
        checkBoxAtivoEditar.setSelected(onEditCategoriaDTO.isAtiva());
        pane_editar.setVisible(true);
    }

    @FXML
    private void closeEditPane() {
        tblView_categorias.getSelectionModel().select(null);
        pane_editar.setVisible(false);
        textField_pane_editar_nome.setText("");
        onEditCategoriaDTO = new CategoriaDTO();
    }

    @FXML
    private void excludeEdition() throws SQLException {
        try {
            categoriaDAO.Excluir(onEditCategoriaDTO.getId());
            populateTableViewData();
        } catch (Exception e) {
            ErrorHelper.ThrowErrorOnAlert(e);
        }
        closeEditPane();
    }

    @FXML
    private void setEvents() {

        btn_criarCategoria.setOnAction((ActionEvent event) -> {
            pane_criar.setVisible(true);
        });

        btn_pane_criar_fechar.setOnAction((ActionEvent event) -> {
            closeCreatePane();
        });

        btn_pane_editar_fechar.setOnAction((ActionEvent event) -> {
            closeEditPane();
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
    private void criarCategoriaHandler() {
        try {
            String nome = textField_pane_criar_nome.getText();
            String descricao = textField_pane_criar_descricao.getText();
            int maxParticipantes = Integer.parseInt(textField_pane_criar_max_participantes.getText());
            String cor = ColorHelper.toHexString(colorPicker_pane_criar_max_participantes.getValue());
            CategoriaDTO categoriaDTO = new CategoriaDTO(0, nome, descricao, cor, 0, maxParticipantes, true);

            categoriaDAO.CriarPorDTO(categoriaDTO);
            populateTableViewData();
            pane_criar.setVisible(false);
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
    }

    @FXML
    private void editarCategoriaHandler() {
        try {
            String nome = textField_pane_editar_nome.getText();
            if (nome.isEmpty())
                throw new Exception("Insira um nome para a categoria");
            String descricao = textField_pane_editar_descricao.getText();
            if (descricao.isEmpty())
                throw new Exception("Insira uma descrição para a categoria");
            int maxParticipantes = ParserHelper.tryParseInt(textField_pane_editar_max_participantes.getText());
            if (maxParticipantes < 2)
                throw new Exception("Insira um número máximo de participantes maior ou igual a 2 para a categoria");

            String cor = ColorHelper.toHexString(colorPicker_pane_editar_max_participantes.getValue());
            boolean isAtiva = checkBoxAtivoEditar.isSelected();

            CategoriaDTO categoriaDTO = new CategoriaDTO(onEditCategoriaDTO.getId(), nome, descricao, cor,
                    onEditCategoriaDTO.getVezesUtilizada(), maxParticipantes, isAtiva);

            categoriaDAO.EditarPorDTO(categoriaDTO);
            populateTableViewData();
            pane_editar.setVisible(false);
        } catch (Exception ex) {
            if (ex instanceof NumberFormatException) {

            }
            ErrorHelper.ThrowErrorOnAlert(ex);
        }
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