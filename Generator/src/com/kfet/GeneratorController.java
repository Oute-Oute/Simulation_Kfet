package com.kfet;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Slider;
import javafx.scene.control.Spinner;
import main.Serializer;

import classes.Customers;

public class GeneratorController {
    @FXML
    private Spinner nbCustomersSpin;
    @FXML
    private Spinner probaKfetierSpin;
    @FXML
    private Slider cptSlider;
    @FXML
    private Slider fastSlider;
    @FXML
    private Slider slowSlider;
    @FXML
    private Slider cashSlider;
    @FXML
    private Slider customerFrequencySlider;
    @FXML
    private Slider slider_max_order;

    @FXML
    protected void onGenerateClick() {
        int nbCustomers = Integer.parseInt(nbCustomersSpin.getValue().toString());
        double proba = (Double.parseDouble(probaKfetierSpin.getValue().toString()) / 100);
        int cpt = (int) cptSlider.getValue();
        int fast = (int) fastSlider.getValue();
        int slow = (int) slowSlider.getValue();
        int cash = (int) cashSlider.getValue();
        int max_order = (int) slider_max_order.getValue();
        double frequency = 3600 * Double.parseDouble(String.valueOf(customerFrequencySlider.getValue()));

        Customers customers = new Customers(nbCustomers, proba, fast, cash, slow, cpt, frequency, max_order);
        Serializer serializer = new Serializer();
        serializer.serializeCustomers(customers);

        Alert alert = new Alert(Alert.AlertType.INFORMATION);

        alert.setTitle("Confirmation");
        alert.setHeaderText("Your file has been created.\nYou can find it at the path below:");
        alert.setContentText(serializer.getPath());
        alert.showAndWait();

    }
}