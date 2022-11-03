package com.grupo8.simuladorplanificacion;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class Principal extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(Principal.class.getResource("principal-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        stage.setTitle("Simulador de algoritmos de planificación del disco duro");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}