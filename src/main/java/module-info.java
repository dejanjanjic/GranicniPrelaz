module net.etfbl.pj2 {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.logging;


    opens net.etfbl.pj2 to javafx.fxml;
    exports net.etfbl.pj2;
}