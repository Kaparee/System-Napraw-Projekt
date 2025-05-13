package pl.naprawy.controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import pl.naprawy.util.ServiceInjector;

import java.io.IOException;

public class UserStatusController extends UserController {
    @FXML
    private Button reportButton;
    @Override
    @FXML
    public void setClientInfo(String username) {
        super.setClientInfo(username);
    }

    public void onReportButtonClicked(){
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/pl/naprawy/fxml/Main-scene-user.fxml"));
        try {
            Parent root = fxmlLoader.load();
            UserController userController = fxmlLoader.getController();

            ServiceInjector.injectAllServices(userController);

            userController.setUsername(username);
            userController.setClientInfo(username);

            Stage stage = (Stage) reportButton.getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.show();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
