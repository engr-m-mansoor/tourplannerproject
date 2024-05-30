module com.example.tourplanner {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires java.sql;
    requires org.apache.logging.log4j;
    requires java.desktop;
    requires org.json;
    requires pdfbox;
    requires opencsv;

    opens presentation to javafx.fxml;
    exports presentation;
    exports presentation.controller;
    opens presentation.controller to javafx.fxml;
    exports presentation.model;
    opens presentation.model to javafx.fxml;
}