module com.example.simulationkfet {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.kfet.generator to javafx.fxml;
    exports com.kfet.generator;
}