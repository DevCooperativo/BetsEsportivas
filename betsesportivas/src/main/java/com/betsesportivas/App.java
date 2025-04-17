package com.betsesportivas;

import java.io.IOException;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 * JavaFX App
 */
public class App extends Application {

    private static Scene scene;
    private static Stage _stage;

    @Override
    public void start(Stage stage) throws IOException {
        _stage = stage;
        scene = new Scene(loadFXML("DashboardCompeticoes"), 800, 600);
        _stage.setScene(scene);
        _stage.show();
    }

    public static void setNewScene(String fxml) throws IOException {
        scene.setRoot(loadFXML(fxml));
        _stage.setScene(scene);
        _stage.show();
    }

    public static void setRoot(String fxml) throws IOException {
        scene.setRoot(loadFXML(fxml));
    }

    private static Parent loadFXML(String fxml) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource(fxml + ".fxml"));
        return fxmlLoader.load();
    }

    public static void main(String[] args) {
        launch();
    }

}