package com.betsesportivas.Controllers;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

import com.betsesportivas.App;
import com.betsesportivas.DAO.CategoriaDAO;
import com.betsesportivas.DAO.CompetidorDAO;
import com.betsesportivas.DAO.ICategoriaDAO;
import com.betsesportivas.DAO.ICompetidorDAO;
import com.betsesportivas.DTO.CategoriaDTO;
import com.betsesportivas.DTO.CompetidorDTO;
import com.betsesportivas.Database.Db;
import com.betsesportivas.Domain.Categoria;
import com.betsesportivas.Domain.Competidor;
import com.betsesportivas.Helpers.ColorHelper;
import com.betsesportivas.Helpers.ErrorHelper;
import com.betsesportivas.Helpers.ParserHelper;

import javafx.application.Platform;
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
    // private final CategoriaDAO categoriaDAO = new CategoriaDAO();
    private final ICategoriaDAO<Categoria, CategoriaDTO> categoriaDAO = new CategoriaDAO();

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

    @FXML
    private BarChart<String, Integer> chartCategorias;
    @FXML
    private CategoryAxis categoryAxis;
    @FXML
    private NumberAxis numberAxis;
    private ObservableList<String> observableListNomesCategorias = FXCollections.observableArrayList();

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        try {
            initializeTableView();
            populateGrafico();
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

    public void populateGrafico() throws SQLException {
        List<CategoriaDTO> categorias = categoriaDAO.BuscarTodosOsDTO();
        // Mapeia nome da categoria → cor
        Map<String, String> coresPorCategoria = categorias.stream()
                .collect(Collectors.toMap(CategoriaDTO::getNome, CategoriaDTO::getCor));
        Map<String, Integer> dados = categoriaDAO.RecuperarQuantidadeCompeticoesPorCategoria();

        XYChart.Series<String, Integer> series = new XYChart.Series<>();

        chartCategorias.setLegendVisible(false);

        for (Map.Entry<String, Integer> dadosItem : dados.entrySet()) {
            String nomeCategoria = dadosItem.getKey();
            Integer quantidade = dadosItem.getValue();
            XYChart.Data<String, Integer> data = new XYChart.Data<>(nomeCategoria, quantidade);
            series.getData().add(data);
        }

        chartCategorias.getData().clear();
        chartCategorias.getData().add(series);

        // Aguarda o gráfico renderizar para aplicar os estilos (via Node)
        Platform.runLater(() -> {
            for (XYChart.Data<String, Integer> data : series.getData()) {
                String nomeCategoria = data.getXValue();
                String cor = coresPorCategoria.get(nomeCategoria);

                if (cor != null && data.getNode() != null) {
                    // Aplica a cor usando CSS inline
                    data.getNode().setStyle("-fx-bar-fill: " + cor + ";");
                }
            }
        });
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