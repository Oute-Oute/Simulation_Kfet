module com.example.simulation.generator {
    requires javafx.controls;
    requires javafx.fxml;
    requires com.example.simulation.common;

    opens com.kfet to javafx.fxml;
    exports com.kfet;

    opens main to javafx.fxml;
    exports main;

}