module sio.gestionmagazine {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    opens  sio.gestionmagazine.Model;

    opens sio.gestionmagazine to javafx.fxml;
    exports sio.gestionmagazine;
}