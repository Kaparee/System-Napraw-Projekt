package pl.naprawy;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.util.Objects;

public class Main extends Application {

    @Override
    public void start(Stage stage) throws Exception {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/pl/naprawy/fxml/Login-scene.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        stage.setTitle("NAPRAW.IO");
        Image icon = new Image(Objects.requireNonNull(getClass().getResourceAsStream("/pl/naprawy/images/logo.jpg")));
        stage.getIcons().add(icon);
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
