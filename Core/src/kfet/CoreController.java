package kfet;

import classes.Customers;
import com.kfet.GeneratorApplication;
import javafx.animation.TranslateTransition;
import javafx.fxml.FXML;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;
import javafx.util.Duration;
import kfet.help.HelpApplication;
import kfet.settings.SettingsApplication;
import main.java.control.Pair;
import main.java.control.Scheduler;

import java.io.IOException;
import java.util.HashMap;

public class CoreController {
    public Customers customers = null;

    @FXML
    private Circle Cashier_1, Cashier_2, Oven_1, Oven_2, Oven_3, Oven_4, Oven_5, Oven_6, Oven_7, Oven_8, MO_1, MO_2, MO_3, Coffee_1, Coffee_2, Chocolate, Kettle_1, Kettle_2;

    @FXML
    private HashMap<Integer, Circle> colorCircle = new HashMap<>();

    @FXML
    private HashMap<Integer, Pair<Double, Double>> coordinates = new HashMap<>();

    @FXML
    private ImageView cooker2 = new ImageView();

    @FXML
    private TranslateTransition transition = new TranslateTransition();

    @FXML
    protected void initialize() {
        colorCircle.put(0, Oven_1);
        colorCircle.put(1, Oven_2);
        colorCircle.put(2, Oven_3);
        colorCircle.put(3, Oven_4);
        colorCircle.put(4, Oven_5);
        colorCircle.put(5, Oven_6);
        colorCircle.put(6, Oven_7);
        colorCircle.put(7, Oven_8);
        colorCircle.put(8, MO_1);
        colorCircle.put(9, MO_2);
        colorCircle.put(10, MO_3);
        colorCircle.put(11, Coffee_1);
        colorCircle.put(12, Coffee_2);
        colorCircle.put(13, Kettle_1);
        colorCircle.put(14, Kettle_2);
        colorCircle.put(15, Chocolate);
        colorCircle.put(20, Cashier_1);

        coordinates.put(0, new Pair<>(475.0, 520.0));
        coordinates.put(1, new Pair<>(475.0, 455.0));
        coordinates.put(2, new Pair<>(475.0, 400.0));
        coordinates.put(3, new Pair<>(475.0, 400.0));
        coordinates.put(4, new Pair<>(500.0, 400.0));
        coordinates.put(5, new Pair<>(565.0, 400.0));
        coordinates.put(6, new Pair<>(630.0, 400.0));
        coordinates.put(7, new Pair<>(695.0, 400.0));
        coordinates.put(8, new Pair<>(775.0, 170.0));
        coordinates.put(9, new Pair<>(775.0, 110.0));
        coordinates.put(10, new Pair<>(775.0, 90.0));
        coordinates.put(11, new Pair<>(600.0, 280.0));
        coordinates.put(12, new Pair<>(600.0, 280.0));
        coordinates.put(13, new Pair<>(775.0, 90.0));
        coordinates.put(14, new Pair<>(725.0, 90.0));
        coordinates.put(15, new Pair<>(600.0, 280.0));

        //TODO verifier que tout les emplacements ont leur coords
    }

    @FXML
    protected void notFree(int id) {
        Circle circle = colorCircle.get(id);

        if (circle != null) {
            circle.setFill(Color.rgb(196, 55, 55));
            circle.setStroke(Color.rgb(112, 39, 39));
        } else {
            //System.out.println("not Free: Ohno, circle " + id + " is null");
        }
    }

    @FXML
    protected void free(int id) {
        Circle circle = colorCircle.get(id);

        if (circle != null) {
            circle.setFill(Color.rgb(81, 198, 55));
            circle.setStroke(Color.rgb(39, 114, 53));
        } else {
            //System.out.println("free: Ohno, circle " + id + " is null");
        }
    }

    @FXML
    protected void transition(ImageView sprite, int device) {
        Pair<Double, Double> Coords = coordinates.get(device);
        transition.setDuration(Duration.seconds(1.5));
        transition.setToX(Coords.getL() - sprite.getLayoutX());
        transition.setToY(Coords.getR() - sprite.getLayoutY());
        transition.setNode(sprite);
        transition.playFromStart();
        transition.setOnFinished(event -> notFree(device));
    }

    @FXML
    protected void transition() {
        transition(cooker2, 15);
    }

    @FXML
    protected void openGenerator() throws IOException {
        new GeneratorApplication().start(new Stage());
    }

    @FXML
    protected void openSettings() throws IOException {
        new SettingsApplication().start(new Stage());
    }

    @FXML
    protected void openHelp() throws IOException {
        new HelpApplication().start(new Stage());
    }

    @FXML
    protected void startSimulation() throws IOException {

        if (Scheduler.getInstance().getnbEvent() == 0) {
            openSettings();
        } else {
            Scheduler.start();
        }
    }

}
