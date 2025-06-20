module com.betsesportivas {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires javafx.base;
    requires javafx.graphics;
    requires java.desktop;
    requires jasperreports;

    opens com.betsesportivas to javafx.fxml;
    opens com.betsesportivas.DAO;
    opens com.betsesportivas.Controllers;
    opens com.betsesportivas.DTO;
    opens com.betsesportivas.Runnable;
    opens sockets.thread;
    exports com.betsesportivas;
}
