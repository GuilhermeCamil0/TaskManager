package com.example.taskmanager;

import com.example.taskmanager.db.DatabaseHelper;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class HelloApplication extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        // Cria o arquivo tarefas.db na raiz do projeto se ele não existir
        DatabaseHelper.inicializarBaseDados();

        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("hello-view.fxml"));
        // Ajustamos o tamanho da janela para 650x450 para acomodar a tabela
        Scene scene = new Scene(fxmlLoader.load(), 650, 450);

        // Carrega o arquivo CSS para aplicar as cores de status
        String css = this.getClass().getResource("styles.css").toExternalForm();
        scene.getStylesheets().add(css);

        stage.setTitle("Gestor de Tarefas - CRUD");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}