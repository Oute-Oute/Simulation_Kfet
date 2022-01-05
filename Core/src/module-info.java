module com.example.simulation.core {
    requires javafx.controls;
    requires javafx.fxml;
    requires poi.ooxml;
    requires poi;
    requires poi.ooxml.schemas;
    requires com.example.simulation.common;
    requires com.example.simulation.generator;
    requires java.desktop;

    opens kfet to javafx.fxml;
    exports kfet;

    opens kfet.settings to javafx.fxml;
    exports kfet.settings;

    opens main.java to javafx.fxml;
    exports main.java;

}