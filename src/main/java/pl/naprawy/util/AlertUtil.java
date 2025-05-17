package pl.naprawy.util;

import javafx.animation.PauseTransition;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.util.Duration;

import java.util.Optional;

public class AlertUtil {
    public static void errorAlert(String message){
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Wystąpił nieoczekiwany błąd");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    public static void informationAlert(String message){
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Informacja");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    public static Optional<ButtonType> confirmAlert(String message){
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Potwierdzenie");
        alert.setHeaderText("Czy na pewno chcesz wykonać te akcje?\n"+message);
        alert.setContentText("Tej operacji nie można cofnąć!.");

        return alert.showAndWait();
    }

    public static void showTimedAlert(Alert.AlertType type, String title, String header, String content, Runnable continuation) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setHeaderText(header);
        alert.setContentText(content);

        PauseTransition pause = new PauseTransition(Duration.seconds(5));
        pause.setOnFinished(event -> {
            alert.close();
            if (continuation != null) {
                continuation.run();
            }
        });
        alert.show();
        pause.play();
    }
}
