module com.betsesportivas {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;

    opens com.betsesportivas to javafx.fxml;
    opens com.betsesportivas.DAO;
    opens com.betsesportivas.Controllers;
    exports com.betsesportivas;
}
