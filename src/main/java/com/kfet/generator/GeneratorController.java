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
    protected void onGenerateClick() {
            int nbCustomers = Integer.parseInt(nbCustomersSpin.getValue().toString());
            double proba = (Double.parseDouble(probaKfetierSpin.getValue().toString())/100);
            int cpt = (int)cptSlider.getValue();
            int fast= (int)fastSlider.getValue();
            int slow = (int)slowSlider.getValue();
            int cash = (int)cashSlider.getValue();
            int frequency =3600*(int) Double.parseDouble(String.valueOf(customerFrequencySlider.getValue()));

            Customers customers = new Customers(nbCustomers,proba,fast,cash,slow,cpt,frequency);
            //System.out.println(Integer.parseInt(nbCustomersSpin.getValue().toString()));
            //Customers.generate(Integer.parseInt(nbCustomersSpin.getValue().toString()));
            Serializer serializer = new Serializer();
            serializer.serializeCustomers(customers);
            //serializer.unserialiseCustomers();

    }
}