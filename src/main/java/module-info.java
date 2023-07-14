module com.example.grafkom {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.desktop;


    opens com.example.grafkom to javafx.fxml;
    exports com.example.grafkom;
}