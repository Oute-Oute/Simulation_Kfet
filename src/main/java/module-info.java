module com.example.simulationkfet {
    requires javafx.controls;
    requires javafx.fxml;

    opens com.kfet.core to javafx.fxml;
    exports com.kfet.core;

    opens com.kfet.generator to javafx.fxml;
    exports com.kfet.generator;
}