module org.example.systemnaprawprojekt {
    requires javafx.controls;
    requires javafx.fxml;


    opens org.example.systemnaprawprojekt to javafx.fxml;
    exports org.example.systemnaprawprojekt;
}