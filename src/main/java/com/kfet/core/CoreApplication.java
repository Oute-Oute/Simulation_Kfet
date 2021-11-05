package com.kfet.core;

import com.kfet.generator.GeneratorApplication;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.io.IOException;


public class CoreApplication extends Application {

    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(CoreApplication.class.getResource("Core-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        stage.setTitle("Simulation");
        stage.getIcons().add(new Image(GeneratorApplication.class.getResourceAsStream("icon.png")));
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }


}