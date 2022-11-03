module com.grupo8.simuladorplanificacion {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.grupo8.simuladorplanificacion to javafx.fxml;
    exports com.grupo8.simuladorplanificacion;
}