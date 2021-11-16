package com.kfet.core;

import core.ControllerHR;
import javafx.fxml.FXML;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

import java.util.HashMap;
import java.util.Map;

public class CoreController extends ControllerHR {

    @FXML
    private Circle Cashier_1, Cashier_2, Oven_1, Oven_2, Oven_3, Oven_4, Oven_5, Oven_6, Oven_7, Oven_8, MO_1, MO_2, MO_3, Coffee_1, Coffee_2, Chocolate, Kettle_1, Kettle_2;

    @FXML
    private Map<Integer, Circle> Circles = new HashMap<>();

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
        //Circles.put(getCashier().get(0).getId(), Cashier_1);
        /*if (getCashier().size() == 2){
            Circles.put(getCashier().get(1).getId(), Cashier_2);
        }*/
    }

    @FXML
    protected void notFree(int id){
        Circles.get(id).setFill(Color.rgb(196,55,55));
        Circles.get(id).setStroke(Color.rgb(112,39,39));
        /* switch (name){
            case "Cashier":
                Cashier.setFill(Color.rgb(196,55,55));
                Cashier.setStroke(Color.rgb(112,39,39));
                break;

            case "Oven_1":
                Oven_1.setFill(Color.rgb(196,55,55));
                Oven_1.setStroke(Color.rgb(112,39,39));
                break;

            case "Oven_2":
                Oven_2.setFill(Color.rgb(196,55,55));
                Oven_2.setStroke(Color.rgb(112,39,39));
                break;

            case "Oven_3":
                Oven_3.setFill(Color.rgb(196,55,55));
                Oven_3.setStroke(Color.rgb(112,39,39));
                break;

            case "Oven_4":
                Oven_4.setFill(Color.rgb(196,55,55));
                Oven_4.setStroke(Color.rgb(112,39,39));
                break;

            case "Oven_5":
                Oven_5.setFill(Color.rgb(196,55,55));
                Oven_5.setStroke(Color.rgb(112,39,39));
                break;

            case "Oven_6":
                Oven_6.setFill(Color.rgb(196,55,55));
                Oven_6.setStroke(Color.rgb(112,39,39));
                break;

            case "Oven_7":
                Oven_7.setFill(Color.rgb(196,55,55));
                Oven_7.setStroke(Color.rgb(112,39,39));
                break;

            case "Oven_8":
                Oven_8.setFill(Color.rgb(196,55,55));
                Oven_8.setStroke(Color.rgb(112,39,39));
                break;

            case "MO_1":
                MO_1.setFill(Color.rgb(196,55,55));
                MO_1.setStroke(Color.rgb(112,39,39));
                break;

            case "MO_2":
                MO_2.setFill(Color.rgb(196,55,55));
                MO_2.setStroke(Color.rgb(112,39,39));
                break;

            case "MO_3":
                MO_3.setFill(Color.rgb(196,55,55));
                MO_3.setStroke(Color.rgb(112,39,39));
                break;

            case "Coffee_1":
                Coffee_1.setFill(Color.rgb(196,55,55));
                Coffee_1.setStroke(Color.rgb(112,39,39));
                break;

            case "Coffee_2":
                Coffee_2.setFill(Color.rgb(196,55,55));
                Coffee_2.setStroke(Color.rgb(112,39,39));
                break;

            case "Chocolate":
                Chocolate.setFill(Color.rgb(196,55,55));
                Chocolate.setStroke(Color.rgb(112,39,39));
                break;

            case "Kettle_1":
                Kettle_1.setFill(Color.rgb(196,55,55));
                Kettle_1.setStroke(Color.rgb(112,39,39));
                break;

            case "Kettle_2":
                Kettle_2.setFill(Color.rgb(196,55,55));
                Kettle_2.setStroke(Color.rgb(112,39,39));
                break;

        }*/
    }

    @FXML
    protected void free(int id){
        Circles.get(id).setFill(Color.rgb(81,198,55));
        Circles.get(id).setStroke(Color.rgb(39,114,53));
        /* switch (name){
            case "Cashier":
                Cashier.setFill(Color.rgb(81,198,55));
                Cashier.setStroke(Color.rgb(39,114,53));
                break;

            case "Oven_1":
                Oven_1.setFill(Color.rgb(81,198,55));
                Oven_1.setStroke(Color.rgb(39,114,53));
                break;

            case "Oven_2":
                Oven_2.setFill(Color.rgb(81,198,55));
                Oven_2.setStroke(Color.rgb(39,114,53));
                break;

            case "Oven_3":
                Oven_3.setFill(Color.rgb(81,198,55));
                Oven_3.setStroke(Color.rgb(39,114,53));
                break;

            case "Oven_4":
                Oven_4.setFill(Color.rgb(81,198,55));
                Oven_4.setStroke(Color.rgb(39,114,53));
                break;

            case "Oven_5":
                Oven_5.setFill(Color.rgb(81,198,55));
                Oven_5.setStroke(Color.rgb(39,114,53));
                break;

            case "Oven_6":
                Oven_6.setFill(Color.rgb(81,198,55));
                Oven_6.setStroke(Color.rgb(39,114,53));
                break;

            case "Oven_7":
                Oven_7.setFill(Color.rgb(81,198,55));
                Oven_7.setStroke(Color.rgb(39,114,53));
                break;

            case "Oven_8":
                Oven_8.setFill(Color.rgb(81,198,55));
                Oven_8.setStroke(Color.rgb(39,114,53));
                break;

            case "MO_1":
                MO_1.setFill(Color.rgb(81,198,55));
                MO_1.setStroke(Color.rgb(39,114,53));
                break;

            case "MO_2":
                MO_2.setFill(Color.rgb(81,198,55));
                MO_2.setStroke(Color.rgb(39,114,53));
                break;

            case "MO_3":
                MO_3.setFill(Color.rgb(81,198,55));
                MO_3.setStroke(Color.rgb(39,114,53));
                break;

            case "Coffee_1":
                Coffee_1.setFill(Color.rgb(81,198,55));
                Coffee_1.setStroke(Color.rgb(39,114,53));
                break;

            case "Coffee_2":
                Coffee_2.setFill(Color.rgb(81,198,55));
                Coffee_2.setStroke(Color.rgb(39,114,53));
                break;

            case "Chocolate":
                Chocolate.setFill(Color.rgb(81,198,55));
                Chocolate.setStroke(Color.rgb(39,114,53));
                break;

            case "Kettle_1":
                Kettle_1.setFill(Color.rgb(81,198,55));
                Kettle_1.setStroke(Color.rgb(39,114,53));
                break;

            case "Kettle_2":
                Kettle_2.setFill(Color.rgb(81,198,55));
                Kettle_2.setStroke(Color.rgb(39,114,53));
                break;
        }*/
    }

}
