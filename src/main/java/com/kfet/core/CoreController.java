package com.kfet.core;

import javafx.fxml.FXML;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

public class CoreController {

    @FXML
    private Circle Cashier;

    @FXML
    protected void onClickClose(){
        notFree(Cashier);
    }

    @FXML
    protected void onClickDelete(){
        free(Cashier);
    }

    @FXML
    protected void notFree(Circle circle){
        circle.setFill(Color.rgb(196,55,55));
        circle.setStroke(Color.rgb(112,39,39));
    }

    @FXML
    protected void free(Circle circle){
        circle.setFill(Color.rgb(81,198,55));
        circle.setStroke(Color.rgb(39,114,53));
    }

}
