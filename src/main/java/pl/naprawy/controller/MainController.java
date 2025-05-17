package pl.naprawy.controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import pl.naprawy.service.*;
import pl.naprawy.util.ServiceInjector;

import java.io.IOException;

public class MainController {

    @FXML private TextField usernameField;
    @FXML private PasswordField passwordField;
    @FXML private Button loginButton, newAccount;

    private final LoginService loginService = new LoginService();

    @FXML
    private void onLoginClicked() {
        String username = usernameField.getText();
        String password = passwordField.getText();

        int role = loginService.verifyLogin(username, password);
        switch (role) {
            case 1 -> openUserPanel(username);

            default -> System.out.println("Błędna nazwa użytkownika lub hasło.");
        }
    }

    @FXML
    private void createNewAccount(javafx.event.ActionEvent event) {
        System.out.println("Kliknięto przycisk: Złóż wniosek o założenie konta pracownika");
    }


    private void openUserPanel(String username) {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/pl/naprawy/fxml/Main-scene-user.fxml"));
        try {
            Parent root = fxmlLoader.load();
            UserController userController = fxmlLoader.getController();

            ServiceInjector.injectAllServices(userController);

            userController.setUsername(username);
            userController.setClientInfo(username);

            Stage stage = (Stage) loginButton.getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.show();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }



}
