package com.betsesportivas.Controllers;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.ResourceBundle;

import com.betsesportivas.App;
import com.betsesportivas.DAO.AtletaDAO;
import com.betsesportivas.DAO.IAtletaDAO;
import com.betsesportivas.DTO.AtletaDTO;
import com.betsesportivas.Database.Db;
import com.betsesportivas.Domain.Atleta;
import com.betsesportivas.Helpers.DateConverterHelper;
import com.betsesportivas.Helpers.ErrorHelper;
import com.betsesportivas.Helpers.FieldsHelper;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.Pane;

public class DashboardAtletasController implements Initializable {
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
    @FXML
    private TextField textFieldNomeAtleta;
    @FXML
    private TextField textFieldSobrenomeAtleta;
    @FXML
    private TextField textFieldEditarNomeAtleta;
    @FXML
    private TextField textFieldEditarSobrenomeAtleta;
    @FXML
    private DatePicker datePickerDataNascimento;
    @FXML
    private ComboBox<String> comboBoxSexoAtleta;
    @FXML
    private List<AtletaDTO> listAtletasDTO;
    @FXML
    private ObservableList<AtletaDTO> observableListAtletasDTO;
    @FXML
    private AtletaDTO onEditAtletaDTO;
    @FXML
    private Pane pane_criar;
    @FXML
    private Pane pane_editar;
    @FXML
    private TableView<AtletaDTO> tableViewAtleta;
    @FXML
    private TableColumn<AtletaDTO, String> tableColumnNomeAtleta;
    @FXML
    private TableColumn<AtletaDTO, Integer> tableColumnQuantidadeVitorias;
    @FXML
    private TableColumn<AtletaDTO, Integer> tableColumnQuantidadeParticipacoes;
    // #endregion

    private final Db Database = new Db();
    private final IAtletaDAO<Atleta, AtletaDTO> atletaDAO = new AtletaDAO();

    public DashboardAtletasController() throws SQLException {
        try {
            atletaDAO.Connect(Database.Connect());
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        try {
            initializeTableView();
            carregarOpcoesSexo();
        } catch (SQLException e) {
            e.getStackTrace();
        }
        setEvents();
    }

    private void carregarOpcoesSexo() {
        comboBoxSexoAtleta.setItems(FXCollections.observableArrayList("Masculino", "Feminino"));
    }

    private void setEvents() {
        setMenuEvents();
    }

    @FXML
    private void abrirPanelCriarAtleta() {
        textFieldNomeAtleta.setText("");
        textFieldSobrenomeAtleta.setText("");
        comboBoxSexoAtleta.setValue("Masculino");
        datePickerDataNascimento.setValue(LocalDate.now());

        pane_criar.setVisible(true);
    }

    @FXML
    private void abrirEdicaoAtleta() throws SQLException {
        onEditAtletaDTO = tableViewAtleta.getSelectionModel().selectedItemProperty().getValue();
        if (onEditAtletaDTO != null) {
            textFieldEditarNomeAtleta.setText(onEditAtletaDTO.getNome());
            textFieldEditarSobrenomeAtleta.setText(onEditAtletaDTO.getSobrenome());
            pane_editar.setVisible(true);
        } else {
            pane_editar.setVisible(false);
        }
    }

    @FXML
    private void criarAtletaHandler() throws SQLException {
        try {

            String nomeAtleta = textFieldNomeAtleta.getText();
            String sobreNomeAtleta = textFieldSobrenomeAtleta.getText();
            LocalDateTime dataNascimento = DateConverterHelper.ConvertStringToLocalDateTime(datePickerDataNascimento.getValue().toString() + "00-00-00");
            String sexoRecuperado = comboBoxSexoAtleta.getSelectionModel().getSelectedItem();
            char sexo = FieldsHelper.recuperarSexoPorString(sexoRecuperado);

            if (LocalDateTime.now().isBefore(dataNascimento) || LocalDateTime.now().isEqual(dataNascimento)) {
                throw new Exception("A data de hoje é maior ou igual à data de nascimento informada");
            }

            AtletaDTO atletaDTO = new AtletaDTO(0, nomeAtleta, sobreNomeAtleta, sexo, dataNascimento);

            atletaDAO.CriarPorDTO(atletaDTO);

            // jogadorDA
            populateTableViewData();
            pane_criar.setVisible(false);
        } catch (Exception ex) {
            ErrorHelper.ThrowErrorOnAlert(ex);
        }
    }

    @FXML
    private void editarAtletaHandler() throws SQLException {
        String nomeAtleta = textFieldEditarNomeAtleta.getText();
        String sobreNomeAtleta = textFieldEditarSobrenomeAtleta.getText();

        AtletaDTO atletaDTO = new AtletaDTO(onEditAtletaDTO.getId(), nomeAtleta, sobreNomeAtleta,
                onEditAtletaDTO.getSexo(), onEditAtletaDTO.getNascimento());

        atletaDAO.EditarPorDTO(atletaDTO);

        // atletaDA
        populateTableViewData();
        pane_editar.setVisible(false);
    }

    @FXML
    private void handleFecharCriar() {
        pane_criar.setVisible(false);
    }

    @FXML
    private void handleFecharEditar() {
        pane_editar.setVisible(false);
    }

    @FXML
    private void excluirAtletaHandler() throws SQLException {
        try {
            atletaDAO.Excluir(onEditAtletaDTO.getId());
        } catch (Exception e) {
            Alert alert = new Alert(Alert.AlertType.ERROR, e.getMessage());
            alert.show();
        }
        populateTableViewData();
        pane_editar.setVisible(false);
    }

    @FXML
    private void initializeTableView() throws SQLException {
        try {
            tableColumnNomeAtleta.setCellValueFactory(new PropertyValueFactory<>("Nome"));
            tableColumnQuantidadeVitorias.setCellValueFactory(new PropertyValueFactory<>("Vitorias"));
            tableColumnQuantidadeParticipacoes.setCellValueFactory(new PropertyValueFactory<>("Participacoes"));
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }

        populateTableViewData();
    }

    private void populateTableViewData() throws SQLException {
        try {
            List<AtletaDTO> atletasDTO = atletaDAO.BuscarTodosOsDTO();
            observableListAtletasDTO = FXCollections.observableArrayList(atletasDTO);
            tableViewAtleta.setItems(observableListAtletasDTO);
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }

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
}
