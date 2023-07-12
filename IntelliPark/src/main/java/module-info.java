module com.example.intellipark {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires javafx.graphics;
//    requires mysql.connector.j;
//    requires org.junit.jupiter.api;

    opens com.example.intellipark to javafx.fxml;
    exports com.example.intellipark;
}