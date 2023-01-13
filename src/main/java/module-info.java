module com.lee.banksystem {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires java.sql;

    opens com.lee.banksystem to javafx.fxml;
    exports com.lee.banksystem;
    exports com.lee.banksystem.controller;
    opens com.lee.banksystem.controller to javafx.fxml;
}