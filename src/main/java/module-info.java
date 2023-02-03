module com.example.smsfinal {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.graphics;
    requires java.sql;


    opens com.example.smsfinal to javafx.fxml;
    exports com.example.smsfinal;
}