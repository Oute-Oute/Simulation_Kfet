package com.kfet.generator;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.Spinner;

public class GeneratorController {
    @FXML
    private Label welcomeText;
    @FXML
    private Spinner nbCustomersSpin;

    @FXML
    protected void onGenerateClick() {
        //welcomeText.setText("Welcome to JavaFX Application!");
        //Customers.generate(Integer.parseInt(nbCustomersSpin.getValue().toString()));
        System.out.println(Integer.parseInt(nbCustomersSpin.getValue().toString()));
    }
}