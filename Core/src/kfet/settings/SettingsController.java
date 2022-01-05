package kfet.settings;

import classes.Customers;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Slider;
import javafx.scene.control.TextField;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import kfet.CoreController;
import main.java.control.ControllerDevices;
import main.java.control.ControllerHR;
import main.java.control.Scheduler;
import main.java.control.Unserializer;
import main.java.payment.NewCustomer;
import main.java.report.ExportExcel;

import java.io.File;
import java.io.StreamTokenizer;


public class SettingsController {

    @FXML
    Slider CookSelector, coffeeSelector, cashierSelector = new Slider();
    @FXML
    TextField dataSelector;
    @FXML
    Button doneButton;

    @FXML
    protected void done() {
        if ((CookSelector.getValue() + coffeeSelector.getValue() + cashierSelector.getValue()) != 4) {
            Alert alert = new Alert(Alert.AlertType.ERROR);

            alert.setTitle("Error");
            alert.setHeaderText("Number of Kfetier must be 4");
            alert.setContentText("Select differents value to have 4 Kfetier.");
            alert.showAndWait();
        } else if (dataSelector.getText().isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.ERROR);

            alert.setTitle("Error");
            alert.setHeaderText("No data file selected");
            alert.setContentText("You must select a data file");
            alert.showAndWait();
        }


        ControllerHR.setInstance((int) CookSelector.getValue(), (int) cashierSelector.getValue(), (int) coffeeSelector.getValue());
        Unserializer unserializer = new Unserializer();
        String File = dataSelector.getText();
        CoreController.getInstance().setCustomers(unserializer.unserialiseCustomers(File));
        CoreController.getInstance().actualizeSprites();
        System.out.println(File);
        String delimiter="[\\\\.]+";
        String[] tokenString = new String[0];
        tokenString = File.split(delimiter);
        ExportExcel.datFile=tokenString[5];


        for (int i = 0; i < CoreController.getInstance().getCustomers().getCustomers().size(); i++) {
            Scheduler.getInstance().addEvent(new NewCustomer(CoreController.getInstance().getCustomers().getCustomers().get(i), CoreController.getInstance().getCustomers().getCustomers().get(i).getArrivalTime()));
        }

        Alert alert = new Alert(Alert.AlertType.INFORMATION);

        alert.setTitle("Validation");
        alert.setHeaderText("You can now start the simulation!");
        alert.showAndWait();

        Stage stage = (Stage) doneButton.getScene().getWindow();
        stage.close();
    }

    @FXML
    protected void open() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Choose a data file");
        File recordsDir = new File(System.getProperty("user.home"), "SimulationKfet/Data");
        if (! recordsDir.exists()) {
            recordsDir.mkdirs();
        }
        fileChooser.setInitialDirectory(recordsDir);
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("DAT", "*.dat"),
                new FileChooser.ExtensionFilter("All Files", "*.*"));
        File file = fileChooser.showOpenDialog(new Stage());
        if (file != null) {
            dataSelector.setText(file.getPath());
        }
    }

}
