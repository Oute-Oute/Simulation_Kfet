package com.kfet.generator;

import generator.Customers;
import javafx.fxml.FXML;
import javafx.scene.control.Spinner;

public class GeneratorController {
    @FXML
    private Spinner nbCustomersSpin;

    @FXML
    protected void onGenerateClick() {

            System.out.println(Integer.parseInt(nbCustomersSpin.getValue().toString()));
            Customers.generate(Integer.parseInt(nbCustomersSpin.getValue().toString()));

    }
}