package kfet;

import com.kfet.GeneratorApplication;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Objects;


public class CoreApplication extends Application {

    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/resources/Core-view.fxml"));
        CoreController controller=new CoreController();
        fxmlLoader.setController(controller.getInstance());
        Scene scene = new Scene(fxmlLoader.load());
        stage.setTitle("Simulation");
        stage.getIcons().add(new Image(Objects.requireNonNull(GeneratorApplication.class.getResourceAsStream("/icon.png"))));
        stage.setScene(scene);
        stage.setResizable(false);
        stage.show();
    }

    public static void main(String[] args) {
        Application.launch();
    }


}