package kfet;

import com.kfet.GeneratorApplication;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import java.io.IOException;
import java.util.Objects;


/**
 * The type Core application.
 */
public class CoreApplication extends Application {

    @Override
    public void start(Stage stage) throws IOException {

        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/Core-view.fxml"));
        CoreController controller = new CoreController();
        fxmlLoader.setController(CoreController.getInstance());
        Scene scene = new Scene(fxmlLoader.load());
        Font.loadFont(getClass().getResourceAsStream("/fonts/DS-DIGIT.ttf"), 48);
        String css = getClass().getResource("/marcopolo.css").toExternalForm();
        scene.getStylesheets().add(css);
        stage.setTitle("Simulation");
        stage.getIcons().add(new Image(Objects.requireNonNull(GeneratorApplication.class.getResourceAsStream("/icon.png"))));
        stage.setScene(scene);
        stage.setResizable(false);
        stage.show();
    }

    /**
     * The entry point of application.
     *
     * @param args the input arguments
     */
    public static void main(String[] args) {
        Application.launch(CoreApplication.class, args);
    }
}