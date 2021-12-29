package kfet.help;

import com.kfet.GeneratorApplication;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Objects;

public class HelpApplication {

    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/resources/Help-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        stage.setTitle("Help");
        stage.getIcons().add(new Image(Objects.requireNonNull(GeneratorApplication.class.getResourceAsStream("/icon.png"))));
        stage.setScene(scene);
        stage.setResizable(false);
        stage.show();
    }

    //public static void main(String[] args) {
    //    launch();
    //}
}

