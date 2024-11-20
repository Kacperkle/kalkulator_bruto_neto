module com.example.klkulator_bruto_neto {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.example.klkulator_bruto_neto to javafx.fxml;
    exports com.example.klkulator_bruto_neto;
}