module org.example.lr5_2 {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.desktop;


    opens org.example.lr5_2 to javafx.fxml;
    exports org.example.lr5_2;
}