package com.kfet.generator;

import generator.Customers;
import javafx.fxml.FXML;
import javafx.scene.control.Slider;
import javafx.scene.control.Spinner;

public class GeneratorController {
    @FXML
    private Spinner nbCustomersSpin;
    @FXML
    private Slider customerFrequencySlider;

    @FXML
    protected void onGenerateClick() {

            System.out.println(Integer.parseInt(nbCustomersSpin.getValue().toString()));
            Customers.generate(Integer.parseInt(nbCustomersSpin.getValue().toString()), 3600*(int) Double.parseDouble(String.valueOf(customerFrequencySlider.getValue())));

    }
}