module com.betsesportivas {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires javafx.base;

    opens com.betsesportivas to javafx.fxml;
    opens com.betsesportivas.DAO;
    opens com.betsesportivas.Controllers;
    opens com.betsesportivas.DTO;
    exports com.betsesportivas;
}
