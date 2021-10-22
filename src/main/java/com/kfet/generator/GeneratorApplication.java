package com.kfet.generator;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.io.IOException;

public class GeneratorApplication extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(GeneratorApplication.class.getResource("Generator-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 275, 420);
        stage.setTitle("Generator");
        stage.setScene(scene);
        stage.show();
        stage.getIcons().add(new Image(GeneratorApplication.class.getResourceAsStream("icon.png")));
    }

    public static void main(String[] args) {
        launch();
    }
}