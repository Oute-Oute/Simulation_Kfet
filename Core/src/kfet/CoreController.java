package kfet;

import com.kfet.GeneratorApplication;
import kfet.settings.SettingsApplication;

import javafx.animation.TranslateTransition;
import javafx.fxml.FXML;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;
import javafx.util.Duration;

import main.java.control.Pair;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class CoreController {

    @FXML
    private Circle Cashier_1, Cashier_2, Oven_1, Oven_2, Oven_3, Oven_4, Oven_5, Oven_6, Oven_7, Oven_8, MO_1, MO_2, MO_3, Coffee_1, Coffee_2, Chocolate, Kettle_1, Kettle_2;

    @FXML
    private Map<Integer, Circle> Circles = new HashMap<>();
    private Map<Integer, Pair<Double, Double>> Coordinates= new HashMap<>();

    @FXML
    private ImageView Cooker2 = new ImageView();

    @FXML
    private TranslateTransition transition = new TranslateTransition();


    @FXML
    private void initialize() {
        Circles.put(0, Oven_1);
        Circles.put(1, Oven_2);
        Circles.put(2, Oven_3);
        Circles.put(3, Oven_4);
        Circles.put(4, Oven_5);
        Circles.put(5, Oven_6);
        Circles.put(6, Oven_7);
        Circles.put(7, Oven_8);
        Circles.put(8, MO_1);
        Circles.put(9, MO_2);
        Circles.put(10, MO_3);
        Circles.put(11, Coffee_1);
        Circles.put(12, Coffee_2);
        Circles.put(13, Kettle_1);
        Circles.put(14, Kettle_2);
        Circles.put(15,Chocolate);
        /*Circles.put(getCashier().get(0).getId(), Cashier_1);
        if (getCashier().size() == 2){
            Circles.put(getCashier().get(1).getId(), Cashier_2);
        }*/
        Coordinates.put(0,new Pair<>(475.0,520.0));
        Coordinates.put(1,new Pair<>(475.0,455.0));
        Coordinates.put(2,new Pair<>(475.0,400.0));
        Coordinates.put(3,new Pair<>(475.0,400.0));
        Coordinates.put(4,new Pair<>(500.0,400.0));
        Coordinates.put(5,new Pair<>(565.0,400.0));
        Coordinates.put(6,new Pair<>(630.0,400.0));
        Coordinates.put(7,new Pair<>(695.0,400.0));
        Coordinates.put(8,new Pair<>(775.0,170.0));
        Coordinates.put(9,new Pair<>(775.0,110.0));
        Coordinates.put(10,new Pair<>(775.0,90.0));
        Coordinates.put(11,new Pair<>(600.0,280.0));
        Coordinates.put(12,new Pair<>(600.0,280.0));
        Coordinates.put(13,new Pair<>(775.0,90.0));
        Coordinates.put(14,new Pair<>(725.0,90.0));
        Coordinates.put(15,new Pair<>(600.0,280.0));

    //TODO verifier que tout les emplacements ont leur coords
    }

    @FXML
    protected void notFree(int id){
        Circles.get(id).setFill(Color.rgb(196,55,55));
        Circles.get(id).setStroke(Color.rgb(112,39,39));

    }

    @FXML
    protected void free(int id){
        Circles.get(id).setFill(Color.rgb(81,198,55));
        Circles.get(id).setStroke(Color.rgb(39,114,53));

    }

    @FXML
    protected  void transition(){
        transition(Cooker2,15);


    }

    @FXML
    protected  void transition(ImageView sprite, int device){
        Pair<Double,Double> Coords=Coordinates.get(device);
        transition.setDuration(Duration.seconds(1.5));
        transition.setToX(Coords.getL()-sprite.getLayoutX());
        transition.setToY(Coords.getR()-sprite.getLayoutY());
        transition.setNode(sprite);
        transition.playFromStart();
        transition.setOnFinished(event -> notFree(device));

    }

    @FXML
    protected void openGenerator() throws IOException {
        new GeneratorApplication().start(new Stage());
    }
    @FXML
    protected void openSettings() throws IOException {
        new SettingsApplication().start(new Stage());
    }
}
