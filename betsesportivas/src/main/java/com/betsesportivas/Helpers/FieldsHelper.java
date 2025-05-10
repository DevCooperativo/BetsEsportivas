package com.betsesportivas.Helpers;

import java.util.List;

import com.betsesportivas.DTO.IBaseDTO;

import javafx.scene.control.ComboBox;
import javafx.scene.control.ListCell;
import javafx.scene.control.TextField;

public class FieldsHelper {
    public static void setHourFieldProperties(List<TextField> fields) {
        for (TextField obj : fields) {

            obj.textProperty().addListener((observable, oldValue, newValue) -> {
                if (newValue.length() > 5) {
                    obj.setText(newValue.substring(0, 5));
                    return;
                }
                if (newValue.length() == 3 && newValue.charAt(newValue.length() - 1) != '-') {
                    char ultimoDigito = newValue.charAt(newValue.length() - 1);
                    obj.setText(oldValue + "-" + ultimoDigito);
                }
            });
        }
    }

    public static <T extends IBaseDTO> void setComboBoxProperties(ComboBox<T> combobox) {
        combobox.setCellFactory(listView -> new ListCell<T>() {
            @Override
            protected void updateItem(T item, boolean empty) {
                super.updateItem(item, empty);
                if (empty || item == null) {
                    setText(null);
                } else {
                    setText(item.getNomeFormatado()); // ou o campo que você quiser exibir
                }
            }
        });

        combobox.setButtonCell(new ListCell<T>() {
            @Override
            protected void updateItem(T item, boolean empty) {
                super.updateItem(item, empty);
                if (empty || item == null) {
                    setText(null);
                } else {
                    setText(item.getNomeFormatado()); // igual ao acima
                }
            }
        });
    }

    public static char recuperarSexoPorString(String sexoString) throws Exception {
        switch (sexoString) {
            case "Masculino":
                return 'M';
            case "Feminino":
                return 'F';
            default:
                throw new Exception("Sexo inválido");
        }
    }
}
