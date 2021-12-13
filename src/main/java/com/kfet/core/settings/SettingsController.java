package com.kfet.core.settings;

import com.kfet.generator.GeneratorApplication;
import core.control.Pair;
import javafx.animation.FillTransition;
import javafx.animation.TranslateTransition;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Slider;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class SettingsController {

    @FXML
    Slider CookSelector,coffeeSelector,cashierSelector =new Slider();
    @FXML
    TextField dataSelector;
@FXML
    protected void done(){
    if((CookSelector.getValue()+coffeeSelector.getValue()+cashierSelector.getValue())!=4){
        Alert alert = new Alert(Alert.AlertType.ERROR);

        alert.setTitle("Error");
        alert.setHeaderText("Number of Kfetier must be 4");
        alert.setContentText("Select differents value to have 4 Kfetier.");
        alert.showAndWait();
    }
    else if(dataSelector.getText().isEmpty()){
        Alert alert = new Alert(Alert.AlertType.ERROR);

        alert.setTitle("Error");
        alert.setHeaderText("No data file selected");
        alert.setContentText("You must select a data file");
        alert.showAndWait();
    }
}
@FXML
    protected void open(){
    FileChooser fileChooser =new FileChooser();
    fileChooser.setTitle("Choose a data file");
    fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("data",".dat"));
        fileChooser.showOpenDialog(new Stage());

}
}
