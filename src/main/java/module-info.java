module com.example.simulationkfet {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.example.simulationkfet to javafx.fxml;
    exports com.example.simulationkfet;
}