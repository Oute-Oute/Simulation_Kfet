package kfet;

import classes.Customers;
import com.kfet.GeneratorApplication;
import javafx.animation.TranslateTransition;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Circle;
import javafx.scene.shape.StrokeType;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Duration;
import kfet.help.HelpApplication;
import kfet.settings.SettingsApplication;
import main.java.WaitingList;
import main.java.control.ControllerHR;
import main.java.control.Pair;
import main.java.control.Scheduler;
import main.java.report.ExportExcel;

import java.awt.*;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;

public final class CoreController {

    private static CoreController CoreControllerInstance=new CoreController();
    private Customers customers = null;
    private Thread scheduler;

    private LocalTime time;

    @FXML
    Text clock;

    @FXML
    private Circle Cashier_1, Cashier_2, Oven_1, Oven_2, Oven_3, Oven_4, Oven_5, Oven_6, Oven_7, Oven_8, MO_1, MO_2, MO_3, Coffee_1, Coffee_2, Chocolate, Kettle_1, Kettle_2;

    @FXML
    private final HashMap<Integer, Circle> colorCircle = new HashMap<>();

    @FXML
    private final HashMap<Integer, Pair<Double, Double>> coordinates = new HashMap<>();

    @FXML
    private ImageView Cashier1,Cashier2,CoffeeMaker1,CoffeeMaker2,Cooker1,Cooker2;


    @FXML
    private final TranslateTransition transition = new TranslateTransition();
    @FXML
    private final Pane pane=new Pane();

    public static CoreController getInstance(){
        if(CoreControllerInstance == null){
            CoreControllerInstance = new CoreController();
        }
        return CoreControllerInstance;
    }

    public Customers getCustomers() {
        return customers;
    }

    public void setCustomers(Customers customers) {
        this.customers = customers;
    }

    @FXML
    public void initialize() {
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


        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("hh:mm");
        time = LocalTime.of(12,30,0);
        Reclock(0);

    }
    @FXML
    public void actualizeSprites(){
        System.out.println("test");
        if(ControllerHR.getInstance().getNbCashier()==2){
            System.out.println("Cash");
            colorCircle.put(21, Cashier_2);
            Cashier_2.setVisible(true);
            Cashier2.setVisible(true);
            CoffeeMaker2.setVisible(false);
            Cooker2.setVisible(false);

        }
        if(ControllerHR.getInstance().getNbCooks()==2){
            System.out.println("Cook");
            Cooker2.setVisible(true);
            Cashier_2.setVisible(false);
            Cashier2.setVisible(false);
            CoffeeMaker2.setVisible(false);


        }
        if(ControllerHR.getInstance().getNbKfetiers()==2){
            System.out.println("Kfetier");
            CoffeeMaker2.setVisible(true);
            Cashier_2.setVisible(false);
            Cashier2.setVisible(false);
            Cooker2.setVisible(false);

        }
    }

    @FXML
    public void notFree(int id) {
        Circle circle = colorCircle.get(id);

        if (circle != null) {
            circle.setFill(Color.rgb(196, 55, 55));
            circle.setStroke(Color.rgb(112, 39, 39));
        } else {
            //System.out.println("not Free: Ohno, circle " + id + " is null");
        }
    }

    @FXML
    public void free(int id) {
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
        transition(Cooker2, 15);
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
    protected void startSimulation() throws IOException, InterruptedException {
        if (Scheduler.getInstance().getnbEvent() == 0) {
            openSettings();
        } else {
       scheduler = new Thread(){
        public void run() {

                    CoreControllerInstance.initialize();
                try {
                    Scheduler.start();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

    }};
        scheduler.start();
        }


    }
    @FXML
    protected void stopSimulation(){
        Scheduler.getInstance().setCurrentTime(7200);
    }
    @FXML
    protected void playSimulation(){
        Scheduler.getInstance().setStatus(0);

    }
    @FXML
    protected void pauseSimulation() throws InterruptedException {
        Scheduler.getInstance().setStatus(1);


    }


    public void End(int status){

        Platform.runLater(new Runnable(){
            @Override
            public void run() {

                if (status==0) {
                    Alert alert;
                    alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Fin de service");
                    alert.setHeaderText("Le service est terminé");
                    alert.setContentText("Vous avez servi tout les clients! Offrez un Tropico à vos kfetiers pendant que vous allez regarder les statistiques du service");
                    alert.showAndWait();
                }

                else if (status==1) {
                    Alert alert;
                    alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Fin de service");
                    alert.setHeaderText("Le service est terminé mais il reste des clients a servir");
                    alert.setContentText("Vous pouvez réessayer avec moins de clients ou en changeant certains paramètres (n'oubliez pas que vous simulez la K'Fet et pas le McDonalds de l'Heure Tranquille, pensez au bien-être de vos Kfetiers!)");
                    alert.showAndWait();
                }

                ExportExcel.CreateFile(CoreController.getInstance().getCustomers());
            }
        });


    }
    public void Reclock(int second){
        LocalTime newTime=time.plusSeconds(second);
        clock.setText(newTime.toString());
    }
}
