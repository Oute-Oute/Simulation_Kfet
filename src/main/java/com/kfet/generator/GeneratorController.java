package com.kfet.generator;

import generator.Customers;
import generator.Serializer;
import javafx.fxml.FXML;
import javafx.scene.control.Slider;
import javafx.scene.control.Spinner;

public class GeneratorController {
    @FXML
    private Spinner nbCustomersSpin;
    @FXML
    private Spinner probaSpinner;
    @FXML
    private Slider cptSlider;
    @FXML
    private Slider fastSlider;
    @FXML
    private Slider slowSlider;
    @FXML
    private Slider cashSlider;

    @FXML
    protected void onGenerateClick() {
            int nbCustomers = Integer.parseInt(nbCustomersSpin.getValue().toString());
            double proba = ;
            int cpt =;
            int fast= ;
            int slow =;
            int cash = ;

            Customers customers = new Customers(nbCustomers,proba,fast,cash,slow,cpt);
            //System.out.println(Integer.parseInt(nbCustomersSpin.getValue().toString()));
            //Customers.generate(Integer.parseInt(nbCustomersSpin.getValue().toString()));
            Serializer serializer = new Serializer();
            serializer.serializeCustomers(customers);
            //serializer.unserialiseCustomers();

    }
}